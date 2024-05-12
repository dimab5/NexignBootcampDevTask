package com.hrs.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic cdrEnrichedBrtToHrs() {
        return new NewTopic("cdr_enriched_brt_to_hrs", 1, (short) 1);
    }

    @Bean
    public NewTopic costHrsToBrt() {
        return new NewTopic("cost_hrs_to_brt", 1, (short) 1);
    }

    @Bean
    public NewTopic tariffHrsToBrt() {
        return new NewTopic("tariff_hrs_to_brt", 1, (short) 1);
    }
}
