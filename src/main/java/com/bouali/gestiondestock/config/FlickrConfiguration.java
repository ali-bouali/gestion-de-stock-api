package com.bouali.gestiondestock.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.FlickrApi.FlickrPerm;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class FlickrConfiguration {

  @Value("${flickr.apiKey}")
  private String apiKey;

  @Value("${flickr.apiSecret}")
  private String apiSecret;

  @Value("${flickr.appKey}")
  private String appKey;

  @Value("${flickr.appSecret}")
  private String appSecret;


  //@Bean
//  public Flickr getFlickr() throws InterruptedException, ExecutionException, IOException, FlickrException {
//    Flickr flickr = new
//        Flickr(apiKey, apiSecret, new REST());
//
//    OAuth10aService service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(FlickrApi.instance(FlickrPerm.WRITE));
//
//    final Scanner scanner = new Scanner(System.in);
//
//    final OAuth1RequestToken request = service.getRequestToken();
//
//    final String authUrl = service.getAuthorizationUrl(request);
//
//    System.out.println(authUrl);
//    System.out.println("Paste it here >> ");
//
//    final String authVerifier = scanner.nextLine();
//
//    OAuth1AccessToken accessToken = service.getAccessToken(request, authVerifier);
//
//    System.out.println(accessToken.getToken());
//    System.out.println(accessToken.getTokenSecret());
//
//    Auth auth = flickr.getAuthInterface().checkToken(accessToken);
//
//    System.out.println("---------------------------");
//    System.out.println(auth.getToken());
//    System.out.println(auth.getTokenSecret());
//
//    return flickr;
//  }

  @Bean
  public Flickr getFlickr2() {
    Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
    Auth auth = new Auth();
    auth.setPermission(Permission.READ);
    auth.setToken(appKey);
    auth.setTokenSecret(appSecret);
    RequestContext requestContext = RequestContext.getRequestContext();
    requestContext.setAuth(auth);
    flickr.setAuth(auth);
    return flickr;
  }

}
