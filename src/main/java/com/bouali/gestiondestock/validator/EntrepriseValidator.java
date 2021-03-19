package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.EntrepriseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class EntrepriseValidator {

  public static List<String> validate(EntrepriseDto dto) {
    List<String> errors = new ArrayList<>();
    if (dto == null) {
      errors.add("Veuillez renseigner le nom de l'entreprise");
      errors.add("Veuillez reseigner la description de l'entreprise");
      errors.add("Veuillez reseigner le code fiscal de l'entreprise");
      errors.add("Veuillez reseigner l'email de l'entreprise");
      errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
      errors.addAll(AdresseValidator.validate(null));
      return errors;
    }

    if (!StringUtils.hasLength(dto.getNom())) {
      errors.add("Veuillez renseigner le nom de l'entreprise");
    }
    if (!StringUtils.hasLength(dto.getDescription())) {
      errors.add("Veuillez reseigner la description de l'entreprise");
    }
    if (!StringUtils.hasLength(dto.getCodeFiscal())) {
      errors.add("Veuillez reseigner le code fiscal de l'entreprise");
    }
    if (!StringUtils.hasLength(dto.getEmail())) {
      errors.add("Veuillez reseigner l'email de l'entreprise");
    }
    if (!StringUtils.hasLength(dto.getNumTel())) {
      errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
    }

    errors.addAll(AdresseValidator.validate(dto.getAdresse()));
    return errors;
  }

}
