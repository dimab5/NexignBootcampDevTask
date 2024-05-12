package com.hrs.models.costs;

import lombok.Data;

@Data
public class MonthCostDto {
    private String clientId;
    private Integer tariffId;
    private Double cost;
    private Long time;
}
