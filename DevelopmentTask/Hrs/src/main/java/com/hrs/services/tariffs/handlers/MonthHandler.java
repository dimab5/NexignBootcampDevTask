package com.hrs.services.tariffs.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.kafka.KafkaProducer;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;
import com.hrs.models.costs.MonthCostDto;
import com.hrs.models.traffics.Traffic;
import com.hrs.repositories.traffics.TrafficRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@AllArgsConstructor
public class MonthHandler extends BaseTariffHandler {
    private final TrafficRepository trafficRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void handle(CostDto costDto, CdrDto cdrDto, String tariffRules, ObjectMapper objectMapper, Integer currentMonth) {
        if (currentMonth != null &&
                getMonthFromUnixTime(cdrDto.getEndTime()) != currentMonth) {
            List<Traffic> trafficList = trafficRepository.findAll();
            List<MonthCostDto> monthCosts = new ArrayList<>();

            for (Traffic traffic : trafficList) {
                MonthCostDto monthCostDto = new MonthCostDto();

                monthCostDto.setClientId(traffic.getClientId());
                monthCostDto.setTariffId(traffic.getTariffId().getTariffId());
                monthCostDto.setTime(cdrDto.getEndTime());

                String currentTariffRules = traffic.getTariffId().getTariffRules();

                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(currentTariffRules);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                JsonNode prepaid = jsonNode.get("prepaid");

                if (!prepaid.isNull()) {
                    monthCostDto.setCost(prepaid.get("cost").asDouble());

                    monthCosts.add(monthCostDto);

                    traffic.setMinutesExternalCurrentMonth(0L);
                    traffic.setMinutesInternalCurrentMonth(0L);
                    traffic.setMonth((short) getMonthFromUnixTime(cdrDto.getEndTime()));

                    trafficRepository.save(traffic);
                }
            }

            try {
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("month_costs", monthCosts);

                String monthCostsJson =  objectMapper.writeValueAsString(jsonMap);
                kafkaProducer.sendMessageTariff(monthCostsJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (nextHandler == null) {
            return;
        }

        nextHandler.handle(costDto, cdrDto, tariffRules, objectMapper, currentMonth);
    }

    private int getMonthFromUnixTime(long unixTimeMillis) {
        Instant instant = Instant.ofEpochMilli(unixTimeMillis);
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return date.getMonthValue();
    }

    private int getCountMonth(Integer firstMonth, Integer secondMonth) {
        int count;

        if (secondMonth > firstMonth) {
            count = secondMonth - firstMonth;
        }
        else {
            count = Math.abs(12 - firstMonth) + secondMonth;
        }

        return count;
    }
}
