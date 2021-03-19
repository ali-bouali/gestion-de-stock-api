package com.bouali.gestiondestock.services.strategy;

import com.bouali.gestiondestock.dto.ClientDto;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidOperationException;
import com.bouali.gestiondestock.model.Client;
import com.bouali.gestiondestock.services.ClientService;
import com.bouali.gestiondestock.services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

  private FlickrService flickrService;
  private ClientService clientService;

  @Autowired
  public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
    this.flickrService = flickrService;
    this.clientService = clientService;
  }

  @Override
  public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
    ClientDto client = clientService.findById(id);
    String urlPhoto = flickrService.savePhoto(photo, titre);
    if (!StringUtils.hasLength(urlPhoto)) {
      throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
    }
    client.setPhoto(urlPhoto);
    return clientService.save(client);
  }
}
