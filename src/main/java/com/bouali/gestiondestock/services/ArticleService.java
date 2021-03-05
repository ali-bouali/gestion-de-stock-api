package com.bouali.gestiondestock.services;

import com.bouali.gestiondestock.dto.ArticleDto;
import java.util.List;

public interface ArticleService {

  ArticleDto save(ArticleDto dto);

  ArticleDto findById(Integer id);

  ArticleDto findByCodeArticle(String codeArticle);

  List<ArticleDto> findAll();

  void delete(Integer id);

}
