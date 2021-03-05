package com.bouali.gestiondestock.validator;

import com.bouali.gestiondestock.dto.EntrepriseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class EntrepriseValidator {

  public static List<String> validate(EntrepriseDto dto) {
    List<String> errors = new ArrayList<>();

    if (!StringUtils.hasLength(dto.getNom())) {
      errors.add("Veuillez renseigner le nom de l'entreprise");
    }
    if (!StringUtils.hasLength(dto.getDescription())) {
      errors.add("Veuillez reseigner la description de l'entreprise");
    }
    return errors;
  }

}
