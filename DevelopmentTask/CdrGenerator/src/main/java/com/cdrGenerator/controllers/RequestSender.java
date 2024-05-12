package com.cdrGenerator.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Component class for sending a POST request to trigger CDR generation.
 */
@Component
public class RequestSender implements CommandLineRunner {
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Sends a POST request to trigger CDR generation upon application startup.
     * @param args Command line arguments (not used)
     */
    @Override
    public void run(String... args) {
        restTemplate.postForObject("http://localhost:8080/generate", null, String.class);
    }
}
