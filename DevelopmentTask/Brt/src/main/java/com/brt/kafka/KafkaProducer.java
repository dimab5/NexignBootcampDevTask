package com.brt.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for producing messages to Kafka topics.
 */
@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to the "cdr_enriched_brt_to_hrs" Kafka topic.
     * @param message The message to be sent
     */
    public void sendMessage(String message) {
        kafkaTemplate.send("cdr_enriched_brt_to_hrs", message);
    }
}
