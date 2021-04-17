package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import com.bouali.gestiondestock.dto.FournisseurDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.dto.MvtStkDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.CommandeFournisseur;
import com.bouali.gestiondestock.model.EtatCommande;
import com.bouali.gestiondestock.model.Fournisseur;
import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.bouali.gestiondestock.model.LigneCommandeFournisseur;
import com.bouali.gestiondestock.model.SourceMvtStk;
import com.bouali.gestiondestock.model.TypeMvtStk;
import com.bouali.gestiondestock.repository.ArticleRepository;
import com.bouali.gestiondestock.repository.CommandeFournisseurRepository;
import com.bouali.gestiondestock.repository.FournisseurRepository;
import com.bouali.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.bouali.gestiondestock.services.CommandeFournisseurService;
import com.bouali.gestiondestock.services.MvtStkService;
import com.bouali.gestiondestock.validator.ArticleValidator;
import com.bouali.gestiondestock.validator.CommandeFournisseurValidator;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

  private CommandeFournisseurRepository commandeFournisseurRepository;
  private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
  private FournisseurRepository fournisseurRepository;
  private ArticleRepository articleRepository;
  private MvtStkService mvtStkService;

  @Autowired
  public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
      FournisseurRepository fournisseurRepository, ArticleRepository articleRepository,
      LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, MvtStkService mvtStkService) {
    this.commandeFournisseurRepository = commandeFournisseurRepository;
    this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    this.fournisseurRepository = fournisseurRepository;
    this.articleRepository = articleRepository;
    this.mvtStkService = mvtStkService;
  }

  @Override
  public CommandeFournisseurDto save(CommandeFournisseurDto dto) {

    List<String> errors = CommandeFournisseurValidator.validate(dto);

    if (!errors.isEmpty()) {
      log.error("Commande fournisseur n'est pas valide");
      throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
    }

    if (dto.getId() != null && dto.isCommandeLivree()) {
      throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }

    Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
    if (fournisseur.isEmpty()) {
      log.warn("Fournisseur with ID {} was not found in the DB", dto.getFournisseur().getId());
      throw new EntityNotFoundException("Aucun fournisseur avec l'ID" + dto.getFournisseur().getId() + " n'a ete trouve dans la BDD",
          ErrorCodes.FOURNISSEUR_NOT_FOUND);
    }

    List<String> articleErrors = new ArrayList<>();

    if (dto.getLigneCommandeFournisseurs() != null) {
      dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
        if (ligCmdFrs.getArticle() != null) {
          Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticle().getId());
          if (article.isEmpty()) {
            articleErrors.add("L'article avec l'ID " + ligCmdFrs.getArticle().getId() + " n'existe pas");
          }
        } else {
          articleErrors.add("Impossible d'enregister une commande avec un aticle NULL");
        }
      });
    }

    if (!articleErrors.isEmpty()) {
      log.warn("");
      throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
    }
    dto.setDateCommande(Instant.now());
    CommandeFournisseur savedCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

    if (dto.getLigneCommandeFournisseurs() != null) {
      dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
        LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs);
        ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFrs);
        ligneCommandeFournisseur.setIdEntreprise(savedCmdFrs.getIdEntreprise());
        LigneCommandeFournisseur saveLigne = ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        effectuerEntree(saveLigne);
      });
    }

    return CommandeFournisseurDto.fromEntity(savedCmdFrs);
  }

  @Override
  public CommandeFournisseurDto findById(Integer id) {
    if (id == null) {
      log.error("Commande fournisseur ID is NULL");
      return null;
    }
    return commandeFournisseurRepository.findById(id)
        .map(CommandeFournisseurDto::fromEntity)
        .orElseThrow(() -> new EntityNotFoundException(
            "Aucune commande fournisseur n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
        ));
  }

  @Override
  public CommandeFournisseurDto findByCode(String code) {
    if (!StringUtils.hasLength(code)) {
      log.error("Commande fournisseur CODE is NULL");
      return null;
    }
    return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
        .map(CommandeFournisseurDto::fromEntity)
        .orElseThrow(() -> new EntityNotFoundException(
            "Aucune commande fournisseur n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
        ));
  }

  @Override
  public List<CommandeFournisseurDto> findAll() {
    return commandeFournisseurRepository.findAll().stream()
        .map(CommandeFournisseurDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
    return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
        .map(LigneCommandeFournisseurDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Integer id) {
    if (id == null) {
      log.error("Commande fournisseur ID is NULL");
      return;
    }
    List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
    if (!ligneCommandeFournisseurs.isEmpty()) {
      throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
          ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
    }
    commandeFournisseurRepository.deleteById(id);
  }

  @Override
  public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
    checkIdCommande(idCommande);
    if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
      log.error("L'etat de la commande fournisseur is NULL");
      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }
    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
    commandeFournisseur.setEtatCommande(etatCommande);

    CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
    if (commandeFournisseur.isCommandeLivree()) {
      updateMvtStk(idCommande);
    }
    return CommandeFournisseurDto.fromEntity(savedCommande);
  }

  @Override
  public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
    checkIdCommande(idCommande);
    checkIdLigneCommande(idLigneCommande);

    if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
      log.error("L'ID de la ligne commande is NULL");
      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }

    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
    Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

    LigneCommandeFournisseur ligneCommandeFounisseur = ligneCommandeFournisseurOptional.get();
    ligneCommandeFounisseur.setQuantite(quantite);
    ligneCommandeFournisseurRepository.save(ligneCommandeFounisseur);

    return commandeFournisseur;
  }

  @Override
  public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
    checkIdCommande(idCommande);
    if (idFournisseur == null) {
      log.error("L'ID du fournisseur is NULL");
      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }
    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
    Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
    if (fournisseurOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.FOURNISSEUR_NOT_FOUND);
    }
    commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

    return CommandeFournisseurDto.fromEntity(
        commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
    );
  }

  @Override
  public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
    checkIdCommande(idCommande);
    checkIdLigneCommande(idLigneCommande);
    checkIdArticle(idArticle, "nouvel");

    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

    Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

    Optional<Article> articleOptional = articleRepository.findById(idArticle);
    if (articleOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
    }

    List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
    if (!errors.isEmpty()) {
      throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
    }

    LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
    ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
    ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

    return commandeFournisseur;
  }

  @Override
  public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
    checkIdCommande(idCommande);
    checkIdLigneCommande(idLigneCommande);

    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
    // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
    findLigneCommandeFournisseur(idLigneCommande);
    ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

    return commandeFournisseur;
  }

  private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
    CommandeFournisseurDto commandeFournisseur = findById(idCommande);
    if (commandeFournisseur.isCommandeLivree()) {
      throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }
    return commandeFournisseur;
  }

  private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
    Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
    if (ligneCommandeFournisseurOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
    }
    return ligneCommandeFournisseurOptional;
  }

  private void checkIdCommande(Integer idCommande) {
    if (idCommande == null) {
      log.error("Commande fournisseur ID is NULL");
      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }
  }

  private void checkIdLigneCommande(Integer idLigneCommande) {
    if (idLigneCommande == null) {
      log.error("L'ID de la ligne commande is NULL");
      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }
  }

  private void checkIdArticle(Integer idArticle, String msg) {
    if (idArticle == null) {
      log.error("L'ID de " + msg + " is NULL");
      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
    }
  }

  private void updateMvtStk(Integer idCommande) {
    List<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
    ligneCommandeFournisseur.forEach(lig -> {
      effectuerEntree(lig);
    });
  }

  private void effectuerEntree(LigneCommandeFournisseur lig) {
    MvtStkDto mvtStkDto = MvtStkDto.builder()
        .article(ArticleDto.fromEntity(lig.getArticle()))
        .dateMvt(Instant.now())
        .typeMvt(TypeMvtStk.ENTREE)
        .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
        .quantite(lig.getQuantite())
        .idEntreprise(lig.getIdEntreprise())
        .build();
    mvtStkService.entreeStock(mvtStkDto);
  }
}
