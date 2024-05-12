package com.brt.services.clients;

import com.brt.models.clients.Client;
import com.brt.models.clients.dto.BalanceDto;
import com.brt.models.clients.dto.ClientDto;
import com.brt.models.clients.dto.TariffDto;
import com.brt.repositories.clients.ClientRepository;
import com.brt.services.mappers.clients.ClientMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation
 */
@Service
@AllArgsConstructor
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public BalanceDto depositBalance(BalanceDto balanceDto) {
        Client client = clientRepository.findClientById(balanceDto.getMsisdn());
        client.setBalance(client.getBalance() + balanceDto.getMoney());
        clientRepository.save(client);

        return balanceDto;
    }

    @Override
    public BalanceDto checkBalance(String msisdn) {
        return new BalanceDto(msisdn, clientRepository.findClientById(msisdn).getBalance());
    }

    @Override
    public TariffDto changeTariff(TariffDto tariffDto) {
        Client client = clientRepository.findClientById(tariffDto.getMsisdn());
        client.setTariffId(tariffDto.getTariffId());
        clientRepository.save(client);

        return tariffDto;
    }

    @Override
    public ClientDto addClient(ClientDto clientDto) {
        clientRepository.save(clientMapper.clientFromClientDto(clientDto));

        return clientDto;
    }

    @Override
    public List<ClientDto> getClientsInfo() {
        return clientRepository
                .findAll()
                .stream()
                .map(clientMapper::clientDtoFromClient)
                .toList();
    }

    @Override
    public ClientDto findClient(String id) {
        return clientMapper.clientDtoFromClient(clientRepository.findClientById(id));
    }
}
