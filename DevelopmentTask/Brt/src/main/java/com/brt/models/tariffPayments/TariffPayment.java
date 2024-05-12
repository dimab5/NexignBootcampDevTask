package com.brt.models.tariffPayments;

import com.brt.models.clients.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tariff_payments_history")
public class TariffPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client clientId;

    @Column(name = "tariff_id")
    private Integer tariffId;

    private Double cost;

    private Long time;
}
