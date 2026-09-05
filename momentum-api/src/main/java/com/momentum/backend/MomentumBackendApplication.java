package com.momentum.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.momentum.backend", "com.momentum.domain"})
@EntityScan("com.momentum.domain")
@EnableJpaRepositories("com.momentum.domain")
public class MomentumBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomentumBackendApplication.class, args);
    }
}
