package com.hrs.models.histories;

import com.hrs.models.tariffs.Tariff;
import com.hrs.models.traffics.Traffic;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
@Entity
@Table(name = "history")
public class History {
    @Id
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "caller_id", nullable = false)
    private String callerId;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time", nullable = false)
    private Long endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id", nullable = false)
    private Tariff tariffId;

    @Column(name = "internal", nullable = false)
    private Boolean internal;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "duration", nullable = false)
    private Integer duration;
}
