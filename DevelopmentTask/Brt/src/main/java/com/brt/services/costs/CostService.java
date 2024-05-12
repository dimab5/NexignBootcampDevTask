package com.brt.services.costs;

import com.brt.models.costs.CostDto;
import com.brt.models.costs.MonthCostDto;
import com.brt.models.tariffPayments.TariffPayment;
import com.brt.services.mappers.costs.CostMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation
 */
@Service
@AllArgsConstructor
public class CostService implements ICostService {
    private final ObjectMapper objectMapper;
    private final CostMapper costMapper;

    @Override
    public List<CostDto> parseCosts(String costs) {
        List<CostDto> costDtoList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(costs);

            JsonNode costsListNode = rootNode.get("costs");

            for (JsonNode cdrNode : costsListNode) {
                CostDto cdrDto = objectMapper.treeToValue(cdrNode, CostDto.class);
                costDtoList.add(cdrDto);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return costDtoList;
    }

    @Override
    public List<TariffPayment> parseMonthCosts(String costs) {
        List<MonthCostDto> montsCostDtoList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(costs);

            JsonNode costsListNode = rootNode.get("month_costs");

            for (JsonNode cdrNode : costsListNode) {
                MonthCostDto cdrDto = objectMapper.treeToValue(cdrNode, MonthCostDto.class);
                montsCostDtoList.add(cdrDto);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return montsCostDtoList.stream()
                .map(costMapper::costToTariffPayment)
                .toList();
    }
}
