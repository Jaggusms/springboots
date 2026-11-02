package org.jagadeesh.springsecuritypractise.service;

import org.jagadeesh.springsecuritypractise.dto.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(OrderConsumer.class);

    private final ObjectMapper objectMapper;

    public OrderConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${app.kafka.topic}")
    public void consume(String message) throws Exception {

        OrderRequest order =
                objectMapper.readValue(message, OrderRequest.class);

        log.info("Received order from MSK: {}", order);
    }
}