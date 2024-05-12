package com.cdrGenerator.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Kafka topics.
 */
@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic cdrSwitchToBrt() {
        return new NewTopic("cdr_switch_to_brt", 1, (short) 1);
    }
}
