package com.hrs.services.tariffs;

import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;
import com.hrs.repositories.tariffs.TariffRepository;
import com.hrs.services.tariffs.handlers.TariffsHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
@AllArgsConstructor
public class CostCalculator implements ICostCalculator {
    private final TariffRepository tariffRepository;
    private final TariffsHandler tariffsHandler;

    @Override
    public CostDto calculateCost(CdrDto cdrDto, Integer currentMonth) {
        CostDto cost = new CostDto();

        cost.setId(cdrDto.getId());
        cost.setClientId(cdrDto.getClientId());
        cost.setCallerId(cdrDto.getCallerId());
        cost.setStartTime(cdrDto.getStartTime());
        cost.setEndTime(cdrDto.getEndTime());
        cost.setCost(null);

        String tariffRules = tariffRepository.findTariffByTariffId(cdrDto.getTariffId()).getTariffRules();
        tariffsHandler.handle(cost, cdrDto, tariffRules, currentMonth);

        return cost;
    }
}