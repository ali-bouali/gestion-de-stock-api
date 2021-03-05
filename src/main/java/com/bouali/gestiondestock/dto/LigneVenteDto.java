package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.LigneVente;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneVenteDto {

  private Integer id;

  private VentesDto vente;

  private ArticleDto article;

  private BigDecimal quantite;

  private BigDecimal prixUnitaire;

  private Integer idEntreprise;

  public static LigneVenteDto fromEntity(LigneVente ligneVente) {
    if (ligneVente == null) {
      return null;
    }

    return LigneVenteDto.builder()
        .id(ligneVente.getId())
        .vente(VentesDto.fromEntity(ligneVente.getVente()))
        .article(ArticleDto.fromEntity(ligneVente.getArticle()))
        .quantite(ligneVente.getQuantite())
        .prixUnitaire(ligneVente.getPrixUnitaire())
        .idEntreprise(ligneVente.getIdEntreprise())
        .build();
  }

  public static LigneVente toEntity(LigneVenteDto dto) {
    if (dto == null) {
      return null;
    }
    LigneVente ligneVente = new LigneVente();
    ligneVente.setId(dto.getId());
    ligneVente.setVente(VentesDto.toEntity(dto.getVente()));
    ligneVente.setArticle(ArticleDto.toEntity(dto.getArticle()));
    ligneVente.setQuantite(dto.getQuantite());
    ligneVente.setPrixUnitaire(dto.getPrixUnitaire());
    ligneVente.setIdEntreprise(dto.getIdEntreprise());
    return ligneVente;
  }

}
