package com.brt.models.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {
    private String msisdn;
    private String name;
    private Integer tariffId;
    private Double money;
}
