package com.cdrGenerator.repositories.cdrs;

import com.cdrGenerator.models.cdrs.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing data.
 */
@Repository
public interface CdrRepository extends JpaRepository<Cdr, Long> {
}
