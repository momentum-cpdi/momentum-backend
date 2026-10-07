package com.momentum.backend.integration;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SportsApiClient {

    public List<String> fetchEvents() {
        return List.of("FOOTBALL", "BASKETBALL");
    }
}
