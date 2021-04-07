package com.bouali.gestiondestock.controller.api;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Api("photos")
public interface PhotoApi {

  @PostMapping(APP_ROOT + "/save/{id}/{title}/{context}")
  Object savePhoto(@PathVariable("context") String context, @PathVariable("id") Integer id,@RequestPart("file") MultipartFile photo, @PathVariable(
      "title") String title) throws IOException,
      FlickrException;

}
