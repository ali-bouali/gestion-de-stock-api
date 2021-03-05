package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.UtilisateurDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class UtilisateurValidator {

  public static List<String> validate(UtilisateurDto utilisateurDto) {
    List<String> errors = new ArrayList<>();

    if (utilisateurDto == null) {
      errors.add("Veuillez renseigner le nom d'utilisateur");
      errors.add("Veuillez renseigner le prenom d'utilisateur");
      errors.add("Veuillez renseigner le mot de passe d'utilisateur");
      errors.add("Veuillez renseigner l'adresse d'utilisateur");
      return errors;
    }

    if (!StringUtils.hasLength(utilisateurDto.getNom())) {
      errors.add("Veuillez renseigner le nom d'utilisateur");
    }
    if (!StringUtils.hasLength(utilisateurDto.getPrenom())) {
      errors.add("Veuillez renseigner le prenom d'utilisateur");
    }
    if (!StringUtils.hasLength(utilisateurDto.getEmail())) {
      errors.add("Veuillez renseigner l'email d'utilisateur");
    }
    if (!StringUtils.hasLength(utilisateurDto.getMoteDePasse())) {
      errors.add("Veuillez renseigner le mot de passe d'utilisateur");
    }
    if (utilisateurDto.getDateDeNaissance() == null) {
      errors.add("Veuillez renseigner la date de naissance d'utilisateur");
    }
    if (utilisateurDto.getAdresse() == null) {
      errors.add("Veuillez renseigner l'adresse d'utilisateur");
    } else {
      if (!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())) {
        errors.add("Le champs 'Adresse 1' est obligatoir");
      }
      if (!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())) {
        errors.add("Le champs 'Ville' est obligatoir");
      }
      if (!StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale())) {
        errors.add("Le champs 'Code postale' est obligatoir");
      }
      if (!StringUtils.hasLength(utilisateurDto.getAdresse().getPays())) {
        errors.add("Le champs 'Pays' est obligatoir");
      }
    }

    return errors;
  }

}
