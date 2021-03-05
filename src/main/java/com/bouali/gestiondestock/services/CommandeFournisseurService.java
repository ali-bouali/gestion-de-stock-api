package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.CommandeFournisseurDto;
import java.util.List;

public interface CommandeFournisseurService {

  CommandeFournisseurDto save(CommandeFournisseurDto dto);

  CommandeFournisseurDto findById(Integer id);

  CommandeFournisseurDto findByCode(String code);

  List<CommandeFournisseurDto> findAll();

  void delete(Integer id);

}
