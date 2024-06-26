package com.cdrGenerator.program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.cdrGenerator")
@EnableJpaRepositories("com.cdrGenerator.repositories")
@EntityScan("com.cdrGenerator.models")
public class CdrGeneratorApplication {
    public static void main(String [] args) {
        SpringApplication.run(CdrGeneratorApplication.class);
    }
}
