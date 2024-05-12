package com.cdrGenerator.controllers;

import com.cdrGenerator.services.cdrs.ICdrGenerator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GenerateController {
    private final ICdrGenerator cdrGenerator;

    @PostMapping("/generate")
    public void send() {
        cdrGenerator.generateCdr();
    }
}
