package com.brt.repositories.clients;


import com.brt.models.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Client data.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Client findClientById(String id);
}
