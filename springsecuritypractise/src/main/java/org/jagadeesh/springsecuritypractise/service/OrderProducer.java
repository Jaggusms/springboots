package org.jagadeesh.springsecuritypractise.service;

import org.jagadeesh.springsecuritypractise.dto.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    public OrderProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${app.kafka.topic}") String topic) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public CompletableFuture<SendResult<String, String>> publish(
            OrderRequest order) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(order);

        return kafkaTemplate.send(topic, order.orderId(), json);
    }
}
