package com.brt.services.mappers.costs;

import com.brt.models.costs.MonthCostDto;
import com.brt.models.tariffPayments.TariffPayment;
import com.brt.repositories.clients.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping between cost-related objects.
 */
@Service
@AllArgsConstructor
public class CostMapper {
    private final ClientRepository clientRepository;

    /**
     * Maps a MonthCostDto object to a TariffPayment object.
     * @param costDto The MonthCostDto object to map
     * @return The mapped TariffPayment object
     */
    public TariffPayment costToTariffPayment(MonthCostDto costDto) {
        TariffPayment tariffPayment = new TariffPayment();

        tariffPayment.setTariffId(costDto.getTariffId());
        tariffPayment.setCost(costDto.getCost());
        tariffPayment.setTime(costDto.getTime());
        tariffPayment.setClientId(clientRepository.findClientById(costDto.getClientId()));

        return tariffPayment;
    }
}
