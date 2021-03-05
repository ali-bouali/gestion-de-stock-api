package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Fournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FournisseurDto {

  private Integer id;

  private String nom;

  private String prenom;

  private AdresseDto adresse;

  private String photo;

  private String mail;

  private String numTel;

  private Integer idEntreprise;

  @JsonIgnore
  private List<CommandeFournisseurDto> commandeFournisseurs;

  public static FournisseurDto fromEntity(Fournisseur fournisseur) {
    if (fournisseur == null) {
      return null;
    }
    return FournisseurDto.builder()
        .id(fournisseur.getId())
        .nom(fournisseur.getNom())
        .prenom(fournisseur.getPrenom())
        .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
        .photo(fournisseur.getPhoto())
        .mail(fournisseur.getMail())
        .numTel(fournisseur.getNumTel())
        .idEntreprise(fournisseur.getIdEntreprise())
        .build();
  }

  public static Fournisseur toEntity(FournisseurDto dto) {
    if (dto == null) {
      return null;
    }
    Fournisseur fournisseur = new Fournisseur();
    fournisseur.setId(dto.getId());
    fournisseur.setNom(dto.getNom());
    fournisseur.setPrenom(dto.getPrenom());
    fournisseur.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
    fournisseur.setPhoto(dto.getPhoto());
    fournisseur.setMail(dto.getMail());
    fournisseur.setNumTel(dto.getNumTel());
    fournisseur.setIdEntreprise(dto.getIdEntreprise());

    return fournisseur;
  }
}
