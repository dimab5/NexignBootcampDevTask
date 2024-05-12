package com.hrs.repositories.histories;

import com.hrs.models.histories.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing historical data.
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
