package com.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient paymentRestClient() {
        // Pre-configure the base URL of the Payment Service
        return RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }
}