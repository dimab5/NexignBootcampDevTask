package com.cdrGenerator.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestSender implements CommandLineRunner {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(String... args) {
        restTemplate.postForObject("http://localhost:8080/generate", null, String.class);
    }
}
