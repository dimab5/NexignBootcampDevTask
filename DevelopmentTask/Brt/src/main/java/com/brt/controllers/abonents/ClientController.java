package com.brt.controllers.abonents;

import com.brt.models.clients.dto.BalanceDto;
import com.brt.models.clients.dto.ClientDto;
import com.brt.services.clients.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST controller for client operations.
 */
@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final IClientService clientService;

    /**
     * Retrieves the balance of a client based on their phone number.
     * @param msisdn The client's phone number
     * @param principal The current user
     * @return ResponseEntity object with the client's balance and response status
     */
    @GetMapping("/{msisdn}/balance")
    public ResponseEntity<BalanceDto> checkBalance(
            @PathVariable String msisdn,
            Principal principal) {
        if (!principal.getName().equals(msisdn)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        BalanceDto balanceDto = clientService.checkBalance(msisdn);

        if (balanceDto != null) {
            return ResponseEntity.ok(balanceDto);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Deposits money into a client's balance.
     * @param msisdn The client's phone number
     * @param balanceDto Object containing the amount to deposit into the balance
     * @param principal The current user
     * @return ResponseEntity object with the updated client's balance and response status
     */
    @PatchMapping("/{msisdn}/balance")
    public ResponseEntity<BalanceDto> depositMoney(
            @PathVariable String msisdn,
            @RequestBody BalanceDto balanceDto,
            Principal principal) {
        if (!principal.getName().equals(msisdn)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (balanceDto.getMoney() < 0) {
            return ResponseEntity.badRequest().build();
        }

        ClientDto clientDto = clientService.findClient(msisdn);

        if (clientDto == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        clientService.depositBalance(balanceDto);

        return ResponseEntity.ok(balanceDto);
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
