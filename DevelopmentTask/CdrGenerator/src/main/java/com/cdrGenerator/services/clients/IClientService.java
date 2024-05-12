package com.cdrGenerator.services.clients;

import com.cdrGenerator.models.clients.Client;

public interface IClientService {
    Client getRandomClientNonEqual(String targetClientId);
    Client getRandomClient();
}
