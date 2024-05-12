package com.cdrGenerator.repositories.clients;

import com.cdrGenerator.models.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Client data.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    /**
     * Retrieves a random client from the database.
     * @return A random Client object
     */
    @Query("SELECT c FROM Client c ORDER BY RANDOM() LIMIT 1")
    Client getRandomClient();
    /**
     * Finds a client by their ID.
     * @param id The ID of the client to find
     * @return The found Client object, or null if not found
     */
    Client findClientById(String id);
}
