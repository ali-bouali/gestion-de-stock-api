package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.dto.ArticleDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.services.ArticleService;
import com.bouali.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {

  private FlickrService flickrService;
  private ArticleService articleService;

  @Autowired
  public SaveArticlePhoto(FlickrService flickrService, ArticleService articleService) {
    this.flickrService = flickrService;
    this.articleService = articleService;
  }

  @Override
  public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
    ArticleDto article = articleService.findById(id);
    String urlPhoto = flickrService.savePhoto(photo, titre);
    if (!StringUtils.hasLength(urlPhoto)) {
      throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
    }
    article.setPhoto(urlPhoto);
    return articleService.save(article);
  }
}
