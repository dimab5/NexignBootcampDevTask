package com.cdrGenerator.repositories.clients;

import com.cdrGenerator.models.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("SELECT c FROM Client c ORDER BY RANDOM() LIMIT 1")
    Client getRandomClient();
    Client findClientById(String id);
}
