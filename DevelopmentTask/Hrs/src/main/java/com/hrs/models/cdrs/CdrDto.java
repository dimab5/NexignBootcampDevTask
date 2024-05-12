package com.hrs.models.cdrs;

import lombok.Data;

@Data
public class CdrDto {
    private Long id;
    private String type;
    private String clientId;
    private String callerId;
    private Long startTime;
    private Long endTime;
    private Integer tariffId;
    private Boolean internal;
}
