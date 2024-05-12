package com.cdrGenerator.repositories.cdrs;

import com.cdrGenerator.models.cdrs.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdrRepository extends JpaRepository<Cdr, Long> {
}
