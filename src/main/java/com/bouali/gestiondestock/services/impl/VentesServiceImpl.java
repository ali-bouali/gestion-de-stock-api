package com.bouali.gestiondestock.services.impl;


import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.LigneVenteDto;
import com.bouali.gestiondestock.dto.MvtStkDto;
import com.bouali.gestiondestock.dto.VentesDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Article;
import com.bouali.gestiondestock.model.LigneVente;
import com.bouali.gestiondestock.model.SourceMvtStk;
import com.bouali.gestiondestock.model.TypeMvtStk;
import com.bouali.gestiondestock.model.Ventes;
import com.bouali.gestiondestock.repository.ArticleRepository;
import com.bouali.gestiondestock.repository.LigneVenteRepository;
import com.bouali.gestiondestock.repository.VentesRepository;
import com.bouali.gestiondestock.services.MvtStkService;
import com.bouali.gestiondestock.services.VentesService;
import com.bouali.gestiondestock.validator.VentesValidator;
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
public class VentesServiceImpl implements VentesService {

  private ArticleRepository articleRepository;
  private VentesRepository ventesRepository;
  private LigneVenteRepository ligneVenteRepository;
  private MvtStkService mvtStkService;

  @Autowired
  public VentesServiceImpl(ArticleRepository articleRepository, VentesRepository ventesRepository,
      LigneVenteRepository ligneVenteRepository, MvtStkService mvtStkService) {
    this.articleRepository = articleRepository;
    this.ventesRepository = ventesRepository;
    this.ligneVenteRepository = ligneVenteRepository;
    this.mvtStkService = mvtStkService;
  }

  @Override
  public VentesDto save(VentesDto dto) {
    List<String> errors = VentesValidator.validate(dto);
    if (!errors.isEmpty()) {
      log.error("Ventes n'est pas valide");
      throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
    }

    List<String> articleErrors = new ArrayList<>();

    dto.getLigneVentes().forEach(ligneVenteDto -> {
      Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
      if (article.isEmpty()) {
        articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId() + " n'a ete trouve dans la BDD");
      }
    });

    if (!articleErrors.isEmpty()) {
      log.error("One or more articles were not found in the DB, {}", errors);
      throw new InvalidEntityException("Un ou plusieurs articles n'ont pas ete trouve dans la BDD", ErrorCodes.VENTE_NOT_VALID, errors);
    }

    Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));

    dto.getLigneVentes().forEach(ligneVenteDto -> {
      LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
      ligneVente.setVente(savedVentes);
      ligneVenteRepository.save(ligneVente);
      updateMvtStk(ligneVente);
    });

    return VentesDto.fromEntity(savedVentes);
  }

  @Override
  public VentesDto findById(Integer id) {
    if (id == null) {
      log.error("Ventes ID is NULL");
      return null;
    }
    return ventesRepository.findById(id)
        .map(VentesDto::fromEntity)
        .orElseThrow(() -> new EntityNotFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
  }

  @Override
  public VentesDto findByCode(String code) {
    if (!StringUtils.hasLength(code)) {
      log.error("Vente CODE is NULL");
      return null;
    }
    return ventesRepository.findVentesByCode(code)
        .map(VentesDto::fromEntity)
        .orElseThrow(() -> new EntityNotFoundException(
            "Aucune vente client n'a ete trouve avec le CODE " + code, ErrorCodes.VENTE_NOT_VALID
        ));
  }

  @Override
  public List<VentesDto> findAll() {
    return ventesRepository.findAll().stream()
        .map(VentesDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Integer id) {
    if (id == null) {
      log.error("Vente ID is NULL");
      return;
    }
    List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
    if (!ligneVentes.isEmpty()) {
      throw new InvalidOperationException("Impossible de supprimer une vente ...",
          ErrorCodes.VENTE_ALREADY_IN_USE);
    }
    ventesRepository.deleteById(id);
  }

  private void updateMvtStk(LigneVente lig) {
    MvtStkDto mvtStkDto = MvtStkDto.builder()
        .article(ArticleDto.fromEntity(lig.getArticle()))
        .dateMvt(Instant.now())
        .typeMvt(TypeMvtStk.SORTIE)
        .sourceMvt(SourceMvtStk.VENTE)
        .quantite(lig.getQuantite())
        .idEntreprise(lig.getIdEntreprise())
        .build();
    mvtStkService.sortieStock(mvtStkDto);
  }
}
