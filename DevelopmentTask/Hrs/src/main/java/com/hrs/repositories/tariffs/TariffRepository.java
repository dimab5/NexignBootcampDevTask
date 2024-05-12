package com.hrs.repositories.tariffs;

import com.hrs.models.tariffs.Tariff;
import com.hrs.models.traffics.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing tariff information.
 */
@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {
    /**
     * Retrieves a tariff by its tariff ID.
     * @param tariffId The ID of the tariff to retrieve
     * @return The Tariff object associated with the specified tariff ID
     */
    Tariff findTariffByTariffId(Integer tariffId);
}
