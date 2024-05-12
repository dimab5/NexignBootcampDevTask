package com.hrs.models.traffics;

import com.hrs.models.histories.History;
import com.hrs.models.tariffs.Tariff;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "traffic")
public class Traffic {
    @Id
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "minutes_int_current_month", nullable = false)
    private Long minutesInternalCurrentMonth;

    @Column(name = "minutes_ext_current_month", nullable = false)
    private Long minutesExternalCurrentMonth;

    @Column(name = "month", nullable = false)
    private Short month;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariffId;

    @OneToMany(mappedBy = "clientId", fetch = FetchType.EAGER)
    private List<History> histories;
}
