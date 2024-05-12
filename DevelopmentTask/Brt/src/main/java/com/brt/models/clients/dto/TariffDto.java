package com.brt.models.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TariffDto {
    private String msisdn;
    private Integer tariffId;
}
