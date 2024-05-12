package com.hrs.repositories.tariffs;

import com.hrs.models.tariffs.Tariff;
import com.hrs.models.traffics.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {
    Tariff findTariffByTariffId(Integer tariffId);
}
