package com.hrs.services.tariffs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;

import java.time.Month;

public abstract class BaseTariffHandler implements ITariffHandler {
    protected ITariffHandler nextHandler = null;

    public abstract void handle(CostDto costDto, CdrDto cdrDto, String tariffRules, ObjectMapper objectMapper, Integer currentMonth);

    public ITariffHandler setNextHandler(ITariffHandler handler) {
        this.nextHandler = handler;

        return this.nextHandler;
    }
}
