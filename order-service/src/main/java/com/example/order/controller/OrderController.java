package com.example.order.controller;

import com.example.order.dto.OrderRequest;
import com.example.order.dto.PaymentRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestClient paymentRestClient;

    // Inject the RestClient bean configured in ClientConfig
    public OrderController(RestClient paymentRestClient) {
        this.paymentRestClient = paymentRestClient;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("-> Order Service: Received order ID: " + orderRequest.getOrderId());

        // Prepare payload for payment service
        PaymentRequest paymentRequest = new PaymentRequest(orderRequest.getOrderId(), orderRequest.getAmount());

        // Call Payment Service via RestClient
        String paymentResponse = paymentRestClient.post()
                .uri("/payments/process")
                .body(paymentRequest)
                .retrieve()
                .body(String.class);

        return "Order placed successfully! Response from Payment Service -> [" + paymentResponse + "]";
    }
}