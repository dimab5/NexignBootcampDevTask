package com.brt.services.clients;

import com.brt.models.clients.dto.BalanceDto;
import com.brt.models.clients.dto.ClientDto;
import com.brt.models.clients.dto.TariffDto;

import java.util.List;

/**
 * Service interface for client-related operations.
 */
public interface IClientService {
    /**
     * Deposits balance for a client.
     * @param balanceDto The BalanceDto containing the balance information to deposit
     * @return The updated BalanceDto after depositing the balance
     */
    BalanceDto depositBalance(BalanceDto balanceDto);
    /**
     * Checks the balance for a client.
     * @param msisdn The MSISDN (mobile subscriber ISDN) of the client to check balance for
     * @return The BalanceDto containing the balance information for the client
     */
    BalanceDto checkBalance(String msisdn);

    /**
     * Changes the tariff for a client.
     * @param tariffDto The TariffDto containing the new tariff information
     * @return The updated TariffDto after changing the tariff
     */
    TariffDto changeTariff(TariffDto tariffDto);
    /**
     * Adds a new client.
     * @param clientDto The ClientDto containing information about the new client
     * @return The added ClientDto
     */
    ClientDto addClient(ClientDto clientDto);
    /**
     * Retrieves information about all clients.
     * @return A list of ClientDto objects containing information about all clients
     */
    List<ClientDto> getClientsInfo();
    /**
     * Finds a client by their ID.
     * @param id The ID of the client to find
     * @return The found ClientDto, or null if not found
     */
    ClientDto findClient(String id);
}
