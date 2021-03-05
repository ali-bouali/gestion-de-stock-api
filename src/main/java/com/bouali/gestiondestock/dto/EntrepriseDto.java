package com.bouali.gestiondestock.dto;

import com.bouali.gestiondestock.model.Entreprise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntrepriseDto {

  private Integer id;

  private String nom;

  private String description;

  private AdresseDto adresse;

  private String codeFiscal;

  private String photo;

  private String email;

  private String numTel;

  private String steWeb;


  @JsonIgnore
  private List<UtilisateurDto> utilisateurs;

  public static EntrepriseDto fromEntity(Entreprise entreprise) {
    if (entreprise == null) {
      return null;
    }
    return EntrepriseDto.builder()
        .id(entreprise.getId())
        .nom(entreprise.getNom())
        .description(entreprise.getDescription())
        .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
        .codeFiscal(entreprise.getCodeFiscal())
        .photo(entreprise.getPhoto())
        .email(entreprise.getEmail())
        .numTel(entreprise.getNumTel())
        .steWeb(entreprise.getSteWeb())
        .build();
  }

  public static Entreprise toEntity(EntrepriseDto dto) {
    if (dto == null) {
      return null;
    }
    Entreprise entreprise = new Entreprise();
    entreprise.setId(dto.getId());
    entreprise.setNom(dto.getNom());
    entreprise.setDescription(dto.getDescription());
    entreprise.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
    entreprise.setCodeFiscal(dto.getCodeFiscal());
    entreprise.setPhoto(dto.getPhoto());
    entreprise.setEmail(dto.getEmail());
    entreprise.setNumTel(dto.getNumTel());
    entreprise.setSteWeb(dto.getSteWeb());

    return entreprise;
  }

}
