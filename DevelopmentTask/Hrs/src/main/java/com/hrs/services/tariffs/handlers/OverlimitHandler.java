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

import java.time.Month;

@Service
@AllArgsConstructor
public class OverlimitHandler extends BaseTariffHandler {
    private final TrafficRepository trafficRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void handle(CostDto costDto, CdrDto cdrDto, String tariffRules, ObjectMapper objectMapper, Integer currentMonth) {
        int duration = (int) Math.ceil((double) (cdrDto.getEndTime() - cdrDto.getStartTime()) / 60000);
        costDto.setDuration(duration);

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(tariffRules);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode prepaid = jsonNode.get("prepaid");
        JsonNode overlimit = jsonNode.get("overlimit");
        double cost = 0D;

        if (prepaid.isNull()) {
            Traffic traffic = trafficRepository.findTrafficByClientId(cdrDto.getClientId());

            if (traffic != null) {
                MonthCostDto monthCost = new MonthCostDto();

                monthCost.setClientId(cdrDto.getClientId());
                monthCost.setTariffId(cdrDto.getTariffId());
                monthCost.setTime(cdrDto.getEndTime());

                String oldTariffRules = traffic.getTariffId().getTariffRules();

                JsonNode oldJsonNode;
                try {
                    oldJsonNode = objectMapper.readTree(oldTariffRules);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                JsonNode oldPrepaid = oldJsonNode.get("prepaid");

                monthCost.setCost(oldPrepaid.get("cost").asDouble());

                try {
                    String monthCosts = objectMapper.writeValueAsString(monthCost);

                    kafkaProducer.sendMessageTariff(monthCosts);
                    trafficRepository.delete(trafficRepository.findTrafficByClientId(cdrDto.getClientId()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            if (cdrDto.getInternal()) {
                if (cdrDto.getType().equals("01")) {
                    cost += (overlimit.get("internal_outcoming").asDouble() * duration);
                } else if (cdrDto.getType().equals("02")) {
                    cost += (overlimit.get("internal_incoming").asDouble() * duration);
                }
            } else {
                if (cdrDto.getType().equals("01")) {
                    cost += (overlimit.get("external_outcoming").asDouble() * duration);
                } else if (cdrDto.getType().equals("02")) {
                    cost += (overlimit.get("extermal_incoming").asDouble() * duration);
                }
            }
        }

        costDto.setCost(cost);

        if (nextHandler == null) {
            return;
        }

        nextHandler.handle(costDto, cdrDto, tariffRules, objectMapper, currentMonth);
    }
}

