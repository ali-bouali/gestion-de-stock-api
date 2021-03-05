package com.bouali.gestiondestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApiGestionDeStockApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGestionDeStockApplication.class, args);
  }

}
