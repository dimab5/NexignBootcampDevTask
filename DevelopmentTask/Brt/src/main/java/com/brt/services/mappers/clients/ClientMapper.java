package com.brt.services.mappers.clients;

import com.brt.models.clients.Client;
import com.brt.models.clients.dto.ClientDto;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {
    public Client clientFromClientDto(ClientDto clientDto) {
        Client client = new Client();

        client.setId(clientDto.getMsisdn());
        client.setName(clientDto.getName());
        client.setTariffId(clientDto.getTariffId());
        client.setBalance(clientDto.getMoney());

        return client;
    }

    public ClientDto clientDtoFromClient(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getTariffId(),
                client.getBalance()
        );
    }
}
