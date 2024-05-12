package com.hrs.kafka;

import com.hrs.services.cdrs.ICdrService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for consuming messages from a Kafka topic.
 */
@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final ICdrService cdrService;

    /**
     * Listens for messages from the Kafka topic "cdr_enriched_brt_to_hrs" and handles them.
     * @param message The message received from the Kafka topic
     */
    @KafkaListener(topics = "cdr_enriched_brt_to_hrs", groupId = "group")
    public void listen(String message) {
        cdrService.handleCdrs(cdrService.parseCdr(message));
    }
}
