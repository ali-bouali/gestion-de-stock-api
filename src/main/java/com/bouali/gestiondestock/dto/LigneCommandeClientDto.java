package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.LigneCommandeClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneCommandeClientDto {

  private Integer id;

  private ArticleDto article;

  @JsonIgnore
  private CommandeClientDto commandeClient;

  private BigDecimal quantite;

  private BigDecimal prixUnitaire;

  private Integer idEntreprise;

  public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient) {
    if (ligneCommandeClient == null) {
      return null;
    }
    return LigneCommandeClientDto.builder()
        .id(ligneCommandeClient.getId())
        .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
        .quantite(ligneCommandeClient.getQuantite())
        .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
        .idEntreprise(ligneCommandeClient.getIdEntreprise())
        .build();
  }

  public static LigneCommandeClient toEntity(LigneCommandeClientDto dto) {
    if (dto == null) {
      return null;
    }

    LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
    ligneCommandeClient.setId(dto.getId());
    ligneCommandeClient.setArticle(ArticleDto.toEntity(dto.getArticle()));
    ligneCommandeClient.setPrixUnitaire(dto.getPrixUnitaire());
    ligneCommandeClient.setQuantite(dto.getQuantite());
    ligneCommandeClient.setIdEntreprise(dto.getIdEntreprise());
    return ligneCommandeClient;
  }

}
