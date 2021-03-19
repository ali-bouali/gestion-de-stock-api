package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class CommandeFournisseurValidator {

  public static List<String> validate(CommandeFournisseurDto dto) {
    List<String> errors = new ArrayList<>();
    if (dto == null) {
      errors.add("Veuillez renseigner le code de la commande");
      errors.add("Veuillez renseigner la date de la commande");
      errors.add("Veuillez renseigner l'etat de la commande");
      errors.add("Veuillez renseigner le client");
      return errors;
    }

    if (!StringUtils.hasLength(dto.getCode())) {
      errors.add("Veuillez renseigner le code de la commande");
    }
    if (dto.getDateCommande() == null) {
      errors.add("Veuillez renseigner la date de la commande");
    }
    if (!StringUtils.hasLength(dto.getEtatCommande().toString())) {
      errors.add("Veuillez renseigner l'etat de la commande");
    }
    if (dto.getFournisseur() == null || dto.getFournisseur().getId() == null) {
      errors.add("Veuillez renseigner le fournisseur");
    }

    return errors;
  }

}
