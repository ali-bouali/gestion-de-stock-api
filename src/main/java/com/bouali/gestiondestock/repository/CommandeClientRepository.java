package com.bouali.gestiondestock.repository;

import com.bouali.gestiondestock.model.CommandeClient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {

  Optional<CommandeClient> findCommandeClientByCode(String code);
}
