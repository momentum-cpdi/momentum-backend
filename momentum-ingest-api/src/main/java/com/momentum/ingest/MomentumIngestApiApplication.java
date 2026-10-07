package com.momentum.ingest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.momentum.ingest", "com.momentum.domain"})
@EntityScan("com.momentum.domain")
@EnableJpaRepositories("com.momentum.domain")
public class MomentumIngestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomentumIngestApiApplication.class, args);
    }
}
