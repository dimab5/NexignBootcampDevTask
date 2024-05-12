package com.brt.services.clients;

import com.brt.models.clients.Client;
import com.brt.models.clients.dto.BalanceDto;
import com.brt.models.clients.dto.ClientDto;
import com.brt.models.clients.dto.TariffDto;

import java.util.List;

public interface IClientService {
    BalanceDto depositBalance(BalanceDto balanceDto);
    BalanceDto checkBalance(String msisdn);
    TariffDto changeTariff(TariffDto tariffDto);
    ClientDto addClient(ClientDto clientDto);
    List<ClientDto> getClientsInfo();
    ClientDto findClient(String id);
}
