package com.hrs.services.tariffs;


import com.hrs.models.cdrs.CdrDto;
import com.hrs.models.costs.CostDto;

import java.time.Month;

public interface ICostCalculator {
    public CostDto calculateCost(CdrDto cdrDto, Integer currentMonth);
}
