package com.brt.models.clients;

import com.brt.models.cdrs.Cdr;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "client_id")
    private String id;

    private String name;

    @Column(name = "tariff_id")
    private Integer tariffId;
    private Double balance;

    @OneToMany(mappedBy = "clientId", fetch = FetchType.EAGER)
    private List<Cdr> cdrRecords;
}
