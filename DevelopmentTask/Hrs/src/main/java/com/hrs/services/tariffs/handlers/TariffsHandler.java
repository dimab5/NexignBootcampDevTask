package com.hrs.services.tariffs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.kafka.KafkaProducer;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;
import com.hrs.repositories.tariffs.TariffRepository;
import com.hrs.repositories.traffics.TrafficRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;

/**
 * Service class for handling different tariff operations based on tariff rules.
 */
@Service
public class TariffsHandler {
    private final ObjectMapper objectMapper;
    private ITariffHandler handler;

    @Autowired
    public TariffsHandler(ObjectMapper objectMapper, TariffRepository tariffRepository, TrafficRepository trafficRepository, KafkaProducer kafkaProducer) {
        this.objectMapper = objectMapper;

        handler = new MonthHandler(trafficRepository, kafkaProducer);
        handler
                .setNextHandler(new PrepaidHandler(tariffRepository, trafficRepository))
                .setNextHandler(new OverlimitHandler(trafficRepository, kafkaProducer));
    }


    /**
     * Handles different tariff operations based on the provided parameters.
     * @param costDto The CostDto object containing cost-related information
     * @param cdrDto The CdrDto object containing CDR information
     * @param tariffRules The tariff rules to apply
     * @param currentMonth The current month for tariff calculation
     */
    public void handle(CostDto costDto, CdrDto cdrDto, String tariffRules, Integer currentMonth) {
        handler.handle(costDto, cdrDto, tariffRules, objectMapper, currentMonth);
    }
}