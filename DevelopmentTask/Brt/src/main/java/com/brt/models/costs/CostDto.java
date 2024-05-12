package com.brt.models.costs;

import lombok.Data;

@Data
public class CostDto {
    private Long id;
    private String clientId;
    private String callerId;
    private Long startTime;
    private Long endTime;
    private Double cost;
    private Integer duration;
}
