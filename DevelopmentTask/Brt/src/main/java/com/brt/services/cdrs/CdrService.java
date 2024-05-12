package com.brt.services.cdrs;

import com.brt.models.cdrs.Cdr;
import com.brt.models.cdrs.dto.CdrDto;
import com.brt.models.clients.Client;
import com.brt.repositories.cdrs.CdrRepository;
import com.brt.repositories.clients.ClientRepository;
import com.brt.services.mappers.cdrs.CdrMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation
 */
@Service
@AllArgsConstructor
public class CdrService implements ICdrService {
    private final CdrRepository cdrRepository;
    private final ClientRepository clientRepository;
    private final CdrMapper cdrMapper;

    @Override
    public void addCdrInHistory(Cdr cdr) {
        cdrRepository.save(cdr);
    }

    @Override
    public List<Cdr> parseCdrs(String cdrs) {
        List<Cdr> cdrList = new ArrayList<>();
        String[] cdrLines = cdrs.split("\n");

        for (String cdrLine : cdrLines) {
            String[] parts = cdrLine.split(",");

            if (parts.length == 5) {
                String type = parts[0];
                Optional<Client> clientId = clientRepository.findById(parts[1]);

                if (clientId.isEmpty()) {
                    continue;
                }

                String callerId = parts[2];
                Long startTime = Long.parseLong(parts[3]);
                Long endTime = Long.parseLong(parts[4].trim());

                Cdr cdr = new Cdr();
                cdr.setType(type);
                cdr.setClientId(clientId.orElse(null));
                cdr.setCallerId(callerId);
                cdr.setStartTime(startTime);
                cdr.setEndTime(endTime);
                cdr.setHrsStatus('S');

                addTariffId(cdr);
                addInternal(cdr);

                cdrList.add(cdr);
            }
        }

        return cdrList;
    }

    @Override
    public String createJsonCdr(List<Cdr> cdrs) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            List<CdrDto> cdrDtos = cdrs.stream()
                    .map(cdrMapper::cdrToDto)
                    .toList();

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("calls_list", cdrDtos);

            return objectMapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void addTariffId(Cdr cdr) {
        Optional<Client> client = clientRepository.findById(cdr.getClientId().getId());
        cdr.setTariffId(client.get().getTariffId());
    }

    private void addInternal(Cdr cdr) {
        Optional<Client> client = clientRepository.findById(cdr.getCallerId());

        if (client.isEmpty()) {
            cdr.setInternal(false);
        }
        else {
            cdr.setInternal(true);
        }
    }
}