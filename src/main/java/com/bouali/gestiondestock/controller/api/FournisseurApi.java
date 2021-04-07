package com.bouali.gestiondestock.controller.api;


import static com.bouali.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

import com.bouali.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("fournisseur")
public interface FournisseurApi {

  @PostMapping(FOURNISSEUR_ENDPOINT + "/create")
  FournisseurDto save(@RequestBody FournisseurDto dto);

  @GetMapping(FOURNISSEUR_ENDPOINT + "/{idFournisseur}")
  FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

  @GetMapping(FOURNISSEUR_ENDPOINT + "/all")
  List<FournisseurDto> findAll();

  @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}")
  void delete(@PathVariable("idFournisseur") Integer id);

}
