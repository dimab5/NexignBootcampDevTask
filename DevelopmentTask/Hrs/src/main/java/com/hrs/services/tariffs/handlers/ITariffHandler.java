package com.hrs.services.tariffs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;

import java.time.Month;

public interface ITariffHandler {
    /**
     * Handles tariff-related operations based on the provided parameters.
     * @param costDto The CostDto object containing cost-related information
     * @param cdrDto The CdrDto object containing Call Detail Record (CDR) information
     * @param tariffRules The tariff rules to apply
     * @param objectMapper The ObjectMapper used for JSON serialization/deserialization
     * @param currentMonth The current month for tariff calculation
     */
    void handle(CostDto costDto, CdrDto cdrDto, String tariffRules, ObjectMapper objectMapper, Integer currentMonth);
    /**
     * Sets the next handler in the chain of responsibility.
     * @param handler The next handler to set
     * @return The next handler set
     */
    ITariffHandler setNextHandler(ITariffHandler handler);
}
