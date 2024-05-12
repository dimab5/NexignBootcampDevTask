package com.hrs.services.tariffs.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;
import com.hrs.models.traffics.Traffic;
import com.hrs.repositories.tariffs.TariffRepository;
import com.hrs.repositories.traffics.TrafficRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class PrepaidHandler extends BaseTariffHandler {
    private final TariffRepository tariffRepository;
    private final TrafficRepository trafficRepository;

    @Override
    public void handle(CostDto costDto,
                       CdrDto cdrDto,
                       String tariffRules,
                       ObjectMapper objectMapper,
                       Integer currentMonth) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(tariffRules);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Integer tariffId = jsonNode.get("tariff_id").asInt();

        if (tariffRepository.findTariffByTariffId(tariffId) == null) {
            return;
        }

        JsonNode prepaid = jsonNode.get("prepaid");

        int total_minutes;

        int duration = (int) Math.ceil((double) (cdrDto.getEndTime() - cdrDto.getStartTime()) / 60000);

        if (!prepaid.isNull()) {
            total_minutes = prepaid.get("limits").get("total_minutes").asInt();

            if (trafficRepository.findTrafficByClientId(cdrDto.getClientId()) == null) {
                Traffic traffic = new Traffic();

                traffic.setClientId(cdrDto.getClientId());
                traffic.setMinutesInternalCurrentMonth(0L);
                traffic.setMinutesExternalCurrentMonth(0L);
                traffic.setMonth((short) getMonthFromUnixTime(cdrDto.getEndTime()));
                traffic.setTariffId(tariffRepository.findTariffByTariffId(cdrDto.getTariffId()));

                trafficRepository.save(traffic);
            } else {
                Traffic traffic = trafficRepository.findTrafficByClientId(cdrDto.getClientId());

                if (traffic.getMinutesInternalCurrentMonth() + traffic.getMinutesExternalCurrentMonth()
                        < total_minutes) {
                    if (cdrDto.getInternal()) {
                        traffic.setMinutesInternalCurrentMonth(traffic.getMinutesInternalCurrentMonth() + duration);
                    } else {
                        traffic.setMinutesExternalCurrentMonth(traffic.getMinutesExternalCurrentMonth() + duration);
                    }
                }
            }

            Traffic traffic = trafficRepository.findTrafficByClientId(cdrDto.getClientId());

            long sumMinutes = traffic.getMinutesInternalCurrentMonth() + traffic.getMinutesExternalCurrentMonth();

            if (sumMinutes >= total_minutes) {
                JsonNode overlimit = jsonNode.get("overlimit");

                tariffRules = tariffRepository
                            .findTariffByTariffId(overlimit.get("reference_tariff_id").asInt())
                            .getTariffRules();

                JsonNode newJsonNode;
                try {
                    newJsonNode = objectMapper.readTree(tariffRules);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                JsonNode newOverlimit = newJsonNode.get("overlimit");

                double cost = 0D;

                if (cdrDto.getInternal()) {
                    if (cdrDto.getType().equals("01")) {
                        cost += (newOverlimit.get("internal_outcoming").asDouble());
                    } else if (cdrDto.getType().equals("02")) {
                        cost += (newOverlimit.get("internal_incoming").asDouble());
                    }
                } else {
                    if (cdrDto.getType().equals("01")) {
                        cost += (newOverlimit.get("external_outcoming").asDouble());
                    } else if (cdrDto.getType().equals("02")) {
                        cost += (newOverlimit.get("extermal_incoming").asDouble());
                    }
                }

                costDto.setCost((sumMinutes - total_minutes) * cost);
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
}