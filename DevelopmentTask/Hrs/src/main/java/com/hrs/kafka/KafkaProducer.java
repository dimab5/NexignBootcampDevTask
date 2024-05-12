package com.hrs.kafka;

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

    public void sendMessageTariff(String message) {
        kafkaTemplate.send("tariff_hrs_to_brt", message);
    }
    public void sendMessageCost(String message) {
        kafkaTemplate.send("cost_hrs_to_brt", message);
    }
}
