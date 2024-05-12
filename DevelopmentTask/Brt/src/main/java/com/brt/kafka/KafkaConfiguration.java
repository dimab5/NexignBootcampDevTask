package com.brt.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Kafka topics.
 */
@Configuration
public class KafkaConfiguration {
    /**
     * Creates a new Kafka topic for CDR switch to BRT.
     *
     * @return NewTopic object representing the CDR switch to BRT topic
     */
    @Bean
    public NewTopic cdrSwitchToBrt() {
        return new NewTopic("cdr_switch_to_brt", 1, (short) 1);
    }

    /**
     * Creates a new Kafka topic for enriched CDR BRT to HRS.
     *
     * @return NewTopic object representing the enriched CDR BRT to HRS topic
     */
    @Bean
    public NewTopic cdrEnrichedBrtToHrs() {
        return new NewTopic("cdr_enriched_brt_to_hrs", 1, (short) 1);
    }

    /**
     * Creates a new Kafka topic for cost HRS to BRT.
     *
     * @return NewTopic object representing the cost HRS to BRT topic
     */
    @Bean
    public NewTopic costHrsToBrt() {
        return new NewTopic("cost_hrs_to_brt", 1, (short) 1);
    }

    /**
     * Creates a new Kafka topic for tariff HRS to BRT.
     *
     * @return NewTopic object representing the tariff HRS to BRT topic
     */
    @Bean
    public NewTopic tariffHrsToBrt() {
        return new NewTopic("tariff_hrs_to_brt", 1, (short) 1);
    }
}
