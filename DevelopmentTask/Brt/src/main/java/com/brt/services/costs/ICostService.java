package com.brt.services.costs;

import com.brt.models.costs.CostDto;
import com.brt.models.tariffPayments.TariffPayment;

import java.util.List;

/**
 * Service interface for cost-related operations.
 */
public interface ICostService {
    /**
     * Parses a string containing cost information.
     * @param costs The string containing cost data
     * @return A list of parsed CostDto objects
     */
    List<CostDto> parseCosts(String costs);
    /**
     * Parses a string containing month cost information.
     * @param costs The string containing month cost data
     * @return A list of parsed TariffPayment objects
     */
    List<TariffPayment> parseMonthCosts(String costs);
}
