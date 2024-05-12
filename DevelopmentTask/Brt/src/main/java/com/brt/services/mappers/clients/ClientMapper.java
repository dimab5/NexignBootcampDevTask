package com.brt.services.mappers.clients;

import com.brt.models.clients.Client;
import com.brt.models.clients.dto.ClientDto;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping between Client and ClientDto objects.
 */
@Service
public class ClientMapper {
    /**
     * Maps a ClientDto object to a Client object.
     * @param clientDto The ClientDto object to map
     * @return The mapped Client object
     */
    public Client clientFromClientDto(ClientDto clientDto) {
        Client client = new Client();

        client.setId(clientDto.getMsisdn());
        client.setName(clientDto.getName());
        client.setTariffId(clientDto.getTariffId());
        client.setBalance(clientDto.getMoney());

        return client;
    }
    /**
     * Maps a Client object to a ClientDto object.
     * @param client The Client object to map
     * @return The mapped ClientDto object
     */
    public ClientDto clientDtoFromClient(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getTariffId(),
                client.getBalance()
        );
    }
}
