package com.example.payment.controller;

import com.example.payment.dto.PaymentRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping("/process")
    public String processPayment(@RequestBody PaymentRequest request) {
        System.out.println("-> Payment Service: Processing payment for Order ID: " 
            + request.getOrderId() + " with amount: " + request.getAmount());
        System.out.println("Request Came to payment services");
        // Simulating successful transaction response
        return "SUCCESS: Payment processed for Order ID: " + request.getOrderId();
    }
}