package com.brt.repositories.cdrs;


import com.brt.models.cdrs.Cdr;
import com.brt.models.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdrRepository extends JpaRepository<Cdr, Long> {
    Cdr findCdrById(Long id);
}
