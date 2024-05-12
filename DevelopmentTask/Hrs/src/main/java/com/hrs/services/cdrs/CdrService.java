package com.hrs.services.cdrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.kafka.KafkaProducer;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;
import com.hrs.models.histories.History;
import com.hrs.models.tariffs.Tariff;
import com.hrs.repositories.histories.HistoryRepository;
import com.hrs.repositories.tariffs.TariffRepository;
import com.hrs.services.tariffs.ICostCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemEventListener;
import java.time.*;
import java.util.*;

/**
 * Implementation
 */
@Service
public class CdrService implements ICdrService {
    private Integer month = null;

    private final ObjectMapper objectMapper;
    private final ICostCalculator costCalculator;
    private final HistoryRepository historyRepository;
    private final TariffRepository tariffRepository;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public CdrService(ObjectMapper objectMapper, ICostCalculator costCalculator, HistoryRepository historyRepository, TariffRepository tariffRepository, KafkaProducer kafkaProducer) {
        this.objectMapper = objectMapper;
        this.costCalculator = costCalculator;
        this.historyRepository = historyRepository;
        this.tariffRepository = tariffRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public List<CdrDto> parseCdr(String cdr) {
        List<CdrDto> cdrs = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(cdr);

            JsonNode callsListNode = rootNode.get("calls_list");

            for (JsonNode cdrNode : callsListNode) {
                CdrDto cdrDto = objectMapper.treeToValue(cdrNode, CdrDto.class);
                cdrs.add(cdrDto);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return cdrs;
    }

    @Override
    public void handleCdrs(List<CdrDto> cdrs) {
        List<CostDto> costs = new ArrayList<>();

        for (int i = 0; i < cdrs.size(); i++) {
            if (i > 0) {
                updateMonth(cdrs.get(i - 1));
            }

            CostDto cost = costCalculator.calculateCost(cdrs.get(i), this.month);
            costs.add(cost);

            History history = new History();

            history.setId(cdrs.get(i).getId());
            history.setType(cdrs.get(i).getType());
            history.setClientId(cdrs.get(i).getClientId());
            history.setCallerId(cdrs.get(i).getCallerId());
            history.setStartTime(cdrs.get(i).getStartTime());
            history.setEndTime(cdrs.get(i).getEndTime());

            Tariff tariff = tariffRepository.findTariffByTariffId(cdrs.get(i).getTariffId());

            history.setTariffId(tariff);
            history.setInternal(cdrs.get(i).getInternal());
            history.setCost(cost.getCost());
            history.setDuration(cost.getDuration());

            historyRepository.save(history);
        }

        try {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("costs", costs);

            String monthCostsJson =  objectMapper.writeValueAsString(jsonMap);
            kafkaProducer.sendMessageCost(monthCostsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void updateMonth(CdrDto cdrDto) {
        this.month = getMonthFromUnixTime(cdrDto.getEndTime());
    }

    private int getMonthFromUnixTime(long unixTimeMillis) {
        Instant instant = Instant.ofEpochMilli(unixTimeMillis);
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return date.getMonthValue();
    }
}
