package com.brt.services.costs;

import com.brt.models.costs.CostDto;
import com.brt.models.tariffPayments.TariffPayment;

import java.util.List;

public interface ICostService {
    List<CostDto> parseCosts(String costs);
    List<TariffPayment> parseMonthCosts(String costs);
}
