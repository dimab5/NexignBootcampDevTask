package com.cdrGenerator.services.clients;

import com.cdrGenerator.models.clients.Client;
import com.cdrGenerator.repositories.clients.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client getRandomClientNonEqual(String targetClientId) {
        Client client = clientRepository.getRandomClient();

        while (client.equals(clientRepository.findClientById(targetClientId))) {
            client = clientRepository.getRandomClient();
        }

        return client;
    }

    @Override
    public Client getRandomClient() {
        return clientRepository.getRandomClient();
    }
}
