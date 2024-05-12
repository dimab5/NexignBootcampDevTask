package com.brt.controllers.managers;

import com.brt.models.clients.dto.ClientDto;
import com.brt.models.clients.dto.TariffDto;
import com.brt.services.clients.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ManagerController {
    private final IClientService clientService;

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

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        if (clientDto.getMoney() < 0) {
            return ResponseEntity.badRequest().build();
        }

        clientService.addClient(clientDto);

        return ResponseEntity.ok(clientDto);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAbonentsInfo() {
        return ResponseEntity.ok(clientService.getClientsInfo());
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Сервер не понял запрос пользователя из-за синтаксической ошибки");
    }
}
