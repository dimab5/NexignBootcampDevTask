package com.hrs.services.tariffs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;

import java.time.Month;

public interface ITariffHandler {
    void handle(CostDto costDto, CdrDto cdrDto, String tariffRules, ObjectMapper objectMapper, Integer currentMonth);
    ITariffHandler setNextHandler(ITariffHandler handler);
}
