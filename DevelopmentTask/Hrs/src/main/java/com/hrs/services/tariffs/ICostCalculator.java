package com.hrs.services.tariffs;


import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;

import java.time.Month;

/**
 * Interface for calculating the cost of a call based on tariff rules and call details.
 */
public interface ICostCalculator {
    /**
     * Calculates the cost of a call based on the provided Call Detail Record (CDR) information
     * and the current month.
     *
     * @param cdrDto The Call Detail Record (CDR) object containing call-related information
     * @param currentMonth The current month for tariff calculation
     * @return The CostDto object containing the calculated cost information
     */
    public CostDto calculateCost(CdrDto cdrDto, Integer currentMonth);
}
