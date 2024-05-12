package com.hrs.models.tariffs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tariffs")
public class Tariff {
    @Id
    @Column(name = "tariff_id", nullable = false)
    private Integer tariffId;

    @Column(name = "tariff_rules", nullable = false, columnDefinition = "jsonb")
    private String tariffRules;
}
