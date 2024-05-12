package com.brt.controllers.abonents;

import com.brt.models.clients.dto.BalanceDto;
import com.brt.models.clients.dto.ClientDto;
import com.brt.services.clients.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {
    private final IClientService clientService;

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

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Сервер не понял запрос пользователя из-за синтаксической ошибки");
    }
}
