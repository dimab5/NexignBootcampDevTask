package com.brt.controllers.managers;

import com.brt.models.clients.dto.ClientDto;
import com.brt.models.clients.dto.TariffDto;
import com.brt.services.clients.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for manager operations.
 */
@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ManagerController {
    private final IClientService clientService;

    /**
     * Changes the tariff ID for a client.
     * @param tariffDto The TariffDto object containing the new tariff ID and client MSISDN
     * @return ResponseEntity object with the updated tariffDto and response status
     */
    @PatchMapping("/tariffID")
    public ResponseEntity<TariffDto> changeTariffId(@RequestBody TariffDto tariffDto) {
        ClientDto clientDto = clientService.findClient(tariffDto.getMsisdn());

        if (clientDto != null) {
            clientService.changeTariff(tariffDto);

            return ResponseEntity.ok(tariffDto);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Adds a new client.
     * @param clientDto The ClientDto object containing information about the new client
     * @return ResponseEntity object with the added clientDto and response status
     */
    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        if (clientDto.getMoney() < 0) {
            return ResponseEntity.badRequest().build();
        }

        clientService.addClient(clientDto);

        return ResponseEntity.ok(clientDto);
    }

    /**
     * Retrieves information about all clients.
     * @return ResponseEntity object with a list of ClientDto objects and response status
     */
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAbonentsInfo() {
        return ResponseEntity.ok(clientService.getClientsInfo());
    }

    /**
     * Handles the exception when it's not possible to read an HTTP message.
     * @param ex The HttpMessageNotReadableException
     * @return ResponseEntity object with an error message and response status
     */
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Сервер не понял запрос пользователя из-за синтаксической ошибки");
    }
}
