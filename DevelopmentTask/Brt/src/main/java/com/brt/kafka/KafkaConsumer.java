package com.brt.kafka;

import com.brt.models.cdrs.Cdr;
import com.brt.models.clients.Client;
import com.brt.models.costs.CostDto;
import com.brt.models.tariffPayments.TariffPayment;
import com.brt.repositories.cdrs.CdrRepository;
import com.brt.repositories.clients.ClientRepository;
import com.brt.repositories.tariffPayments.TariffPaymentRepository;
import com.brt.services.cdrs.ICdrService;
import com.brt.services.costs.ICostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Kafka consumer service for consuming messages from Kafka topics.
 */
@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final ICdrService cdrService;
    private final KafkaProducer kafkaProducer;
    private final ICostService costService;
    private final CdrRepository cdrRepository;
    private final ClientRepository clientRepository;
    private final TariffPaymentRepository tariffPaymentRepository;

    /**
     * Listens to the "cdr_switch_to_brt" Kafka topic for CDR messages.
     * Parses and processes the CDR messages and sends them to the appropriate service.
     * @param message The CDR message received from Kafka
     */
    @KafkaListener(topics = "cdr_switch_to_brt", groupId = "group")
    public void listenCdrs(String message) {
        List<Cdr> cdrs = cdrService.parseCdrs(message);

        for (Cdr cdr : cdrs) {
            cdrService.addCdrInHistory(cdr);
        }

        kafkaProducer.sendMessage(cdrService.createJsonCdr(cdrs));
    }

    /**
     * Listens to the "cost_hrs_to_brt" Kafka topic for cost messages.
     * Parses and processes the cost messages and updates client balances accordingly.
     * @param message The cost message received from Kafka
     */
    @KafkaListener(topics = "cost_hrs_to_brt", groupId = "group")
    public void listenCosts(String message) {
        List<CostDto> costDtoList = costService.parseCosts(message);

        for (CostDto costDto : costDtoList) {
            Client client = clientRepository.findClientById(costDto.getClientId());

            Cdr cdr = cdrRepository.findCdrById(costDto.getId());

            if (costDto.getCost() == null) {
                cdr.setHrsStatus('E');
            }
            else {
                client.setBalance(client.getBalance() - costDto.getCost());
                cdr.setHrsStatus('C');
            }

            cdr.setCost(costDto.getCost());

            cdrRepository.save(cdr);
            clientRepository.save(client);
        }

        Random random = new Random();
        int randomMoney = random.nextInt(51);

        List<Client> clients = clientRepository.findAll();
        clients.forEach(client -> {
            client.setBalance(client.getBalance() + randomMoney);
            clientRepository.save(client);
        });
    }

    /**
     * Listens to the "tariff_hrs_to_brt" Kafka topic for tariff messages.
     * Parses and processes the tariff messages and updates client balances accordingly.
     * @param message The tariff message received from Kafka
     */
    @KafkaListener(topics = "tariff_hrs_to_brt", groupId = "group")
    public void listenTariff(String message) {
        List<TariffPayment> tariffPaymentList = costService.parseMonthCosts(message);

        for (TariffPayment tariffPayment : tariffPaymentList) {
            Client client = clientRepository.findClientById(tariffPayment.getClientId().getId());

            client.setBalance(client.getBalance() - tariffPayment.getCost());
            clientRepository.save(client);
            tariffPaymentRepository.save(tariffPayment);
        }
    }
}
