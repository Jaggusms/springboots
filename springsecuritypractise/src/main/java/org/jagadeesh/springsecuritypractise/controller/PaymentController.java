package org.jagadeesh.springsecuritypractise.controller;

import org.jagadeesh.springsecuritypractise.service.PaymentServiceRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentServiceRetry paymentService;

    public PaymentController(PaymentServiceRetry paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/process")
    public String processPayment() throws Exception {
        return paymentService.processPayment();
    }
}