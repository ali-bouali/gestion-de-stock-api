package com.bouali.gestiondestock.controller.api;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.dto.LigneCommandeClientDto;
import com.bouali.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.bouali.gestiondestock.dto.LigneVenteDto;
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

@Api("articles")
public interface ArticleApi {

  @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Enregistrer un article", notes = "Cette methode permet d'enregistrer ou modifier un article", response = ArticleDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "L'objet article cree / modifie"),
      @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
  })
  ArticleDto save(@RequestBody ArticleDto dto);

  @GetMapping(value = APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet de chercher un article par son ID", response = ArticleDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
      @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
  })
  ArticleDto findById(@PathVariable("idArticle") Integer id);

  @GetMapping(value = APP_ROOT + "/articles/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet de chercher un article par son CODE", response =
      ArticleDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
      @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
  })
  ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

  @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existent "
      + "dans la BDD", responseContainer = "List<ArticleDto>")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
  })
  List<ArticleDto> findAll();

  @GetMapping(value = APP_ROOT + "/articles/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
  List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

  @GetMapping(value = APP_ROOT + "/articles/historique/commandeclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
  List<LigneCommandeClientDto> findHistoriaueCommandeClient(@PathVariable("idArticle") Integer idArticle);

  @GetMapping(value = APP_ROOT + "/articles/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
  List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

  @GetMapping(value = APP_ROOT + "/articles/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
  List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idCategory);

  @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
  @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "L'article a ete supprime")
  })
  void delete(@PathVariable("idArticle") Integer id);

}
