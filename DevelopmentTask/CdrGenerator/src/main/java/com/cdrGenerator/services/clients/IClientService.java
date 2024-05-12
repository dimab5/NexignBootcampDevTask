package com.cdrGenerator.services.clients;

import com.cdrGenerator.models.clients.Client;

/**
 * Service interface for accessing Client-related functionality.
 */
public interface IClientService {
    /**
     * Retrieves a random client that is not equal to the specified target client ID.
     * @param targetClientId The ID of the target client to exclude from the random selection
     * @return A random Client object that is not equal to the target client ID
     */
    Client getRandomClientNonEqual(String targetClientId);
    /**
     * Retrieves a random client from the database.
     * @return A random Client object
     */
    Client getRandomClient();
}
