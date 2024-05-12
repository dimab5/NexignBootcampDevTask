package com.cdrGenerator.models.clients;

import com.cdrGenerator.models.cdrs.Cdr;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "client_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "clientId", fetch = FetchType.EAGER)
    private List<Cdr> cdrRecords;
}
