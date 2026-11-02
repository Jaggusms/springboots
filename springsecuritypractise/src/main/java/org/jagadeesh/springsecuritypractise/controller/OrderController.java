package org.jagadeesh.springsecuritypractise.controller;

import org.jagadeesh.springsecuritypractise.dto.OrderRequest;
import org.jagadeesh.springsecuritypractise.service.OrderProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Map<String, String>>> createOrder(
            @RequestBody OrderRequest order)
    throws JsonProcessingException {

        return orderProducer.publish(order)
                .thenApply((SendResult<String, String> result) ->
                        ResponseEntity.ok(Map.of(
                                "status", "PUBLISHED",
                                "topic", result.getRecordMetadata().topic(),
                                "partition", String.valueOf(
                                        result.getRecordMetadata().partition()),
                                "offset", String.valueOf(
                                        result.getRecordMetadata().offset())
                        )))
                .exceptionally(ex ->
                        ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body(Map.of(
                                        "status", "FAILED",
                                        "message", "Kafka publish failed"
                                )));
    }
}