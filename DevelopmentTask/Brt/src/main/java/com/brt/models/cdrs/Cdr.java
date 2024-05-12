package com.brt.models.cdrs;

import com.brt.models.clients.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "history")
public class Cdr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type", length = 2, nullable = false)
    String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client clientId;

    @Column(name = "caller_id")
    private String callerId;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time", nullable = false)
    private Long endTime;

    @Column(name = "tariff_id")
    private Integer tariffId;

    @Column(name = "internal")
    private Boolean internal;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "hrs_status")
    private Character hrsStatus;
}
