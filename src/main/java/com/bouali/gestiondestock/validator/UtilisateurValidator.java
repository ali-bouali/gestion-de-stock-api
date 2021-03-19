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
      errors.addAll(AdresseValidator.validate(null));
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
    errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));

    return errors;
  }

}
