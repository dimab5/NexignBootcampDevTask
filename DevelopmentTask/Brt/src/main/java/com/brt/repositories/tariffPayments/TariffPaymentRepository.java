package com.brt.repositories.tariffPayments;

import com.brt.models.tariffPayments.TariffPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffPaymentRepository extends JpaRepository<TariffPayment, Long> {
}
