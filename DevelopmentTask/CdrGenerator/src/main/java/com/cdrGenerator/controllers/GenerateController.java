package com.cdrGenerator.controllers;

import com.cdrGenerator.services.cdrs.ICdrGenerator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for generating and sending CDRs.
 */
@RestController
@AllArgsConstructor
public class GenerateController {
    private final ICdrGenerator cdrGenerator;

    /**
     * Endpoint to trigger generation and sending of CDRs.
     */
    @PostMapping("/generate")
    public void send() {
        cdrGenerator.generateCdr();
    }
}
