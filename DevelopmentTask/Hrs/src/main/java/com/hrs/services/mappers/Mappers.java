package com.hrs.services.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mappers {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
