package com.bouali.gestiondestock.controller.api;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;

import com.bouali.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("categories")
public interface CategoryApi {

  @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Enregistrer une categorie", notes = "Cette methode permet d'enregistrer ou modifier une categorie", response =
      CategoryDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "L'objet category cree / modifie"),
      @ApiResponse(code = 400, message = "L'objet category n'est pas valide")
  })
  CategoryDto save(@RequestBody CategoryDto dto);

  @GetMapping(value = APP_ROOT + "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Rechercher une categorie par ID", notes = "Cette methode permet de chercher une categorie par son ID", response =
      CategoryDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "La categorie a ete trouve dans la BDD"),
      @ApiResponse(code = 404, message = "Aucune categorie n'existe dans la BDD avec l'ID fourni")
  })
  CategoryDto findById(@PathVariable("idCategory") Integer idCategory);

  @GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Rechercher une categorie par CODE", notes = "Cette methode permet de chercher une categorie par son CODE", response =
      CategoryDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
      @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
  })
  CategoryDto findByCode(@PathVariable("codeCategory") String codeCategory);

  @GetMapping(value = APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Renvoi la liste des categories", notes = "Cette methode permet de chercher et renvoyer la liste des categories qui existent "
      + "dans la BDD", responseContainer = "List<CategoryDto>")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
  })
  List<CategoryDto> findAll();

  @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
  @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer une categorie par ID")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "La categorie a ete supprime")
  })
  void delete(@PathVariable("idCategory") Integer id);

}
