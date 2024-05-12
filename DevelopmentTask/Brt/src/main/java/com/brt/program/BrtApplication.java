package com.brt.program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan("com.brt")
@EnableJpaRepositories("com.brt.repositories")
@EntityScan("com.brt.models")
public class BrtApplication {
    public static void main(String [] args) {
        SpringApplication.run(BrtApplication.class);
    }
}
