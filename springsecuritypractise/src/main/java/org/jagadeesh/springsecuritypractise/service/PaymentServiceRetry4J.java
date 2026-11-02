package org.jagadeesh.springsecuritypractise.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PaymentServiceRetry4J {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Retry(name = "paymentService")
    public String processPayment() throws IOException {

        int attempt = counter.incrementAndGet();

        System.out.println("Payment attempt: " + attempt);

        if (attempt < 3) {
            throw new IOException("Temporary payment service failure");
        }

        return "Payment processed successfully";
    }
}





// package com.example.resilience.service;

// import io.github.resilience4j.retry.annotation.Retry;
// import org.springframework.stereotype.Service;

// import java.io.IOException;
// import java.util.concurrent.atomic.AtomicInteger;

// @Service
// public class PaymentService {

//     private final AtomicInteger counter = new AtomicInteger(0);

//     @Retry(
//         name = "paymentService",
//         fallbackMethod = "paymentFallback"
//     )
//     public String processPayment() throws IOException {

//         int attempt = counter.incrementAndGet();

//         System.out.println("Payment attempt: " + attempt);

//         // Simulate failure on every attempt.
//         throw new IOException("Payment service unavailable");
//     }

//     public String paymentFallback(IOException exception) {

//         System.out.println("All retry attempts failed");

//         return "Payment service is temporarily unavailable";
//     }
// }



// @Retry(name = "databaseRetry")
// @Transactional
// public void updateOrder() {

//     // Update database records
// }


// @Service
// public class OrderRetryService {

//     private final OrderTransactionService transactionService;

//     public OrderRetryService(OrderTransactionService transactionService) {
//         this.transactionService = transactionService;
//     }

//     @Retry(name = "databaseRetry")
//     public void updateOrderWithRetry() {

//         transactionService.updateOrder();
//     }
// }



// @CircuitBreaker(
//     name = "paymentService",
//     fallbackMethod = "paymentFallback"
// )
// public String callPaymentService() {
//     return paymentClient.processPayment();
// }


// package com.example.orderservice.service;

// import org.springframework.stereotype.Service;
// import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

// @Service
// public class PaymentService {

//     private final PaymentClient paymentClient;

//     public PaymentService(PaymentClient paymentClient) {
//         this.paymentClient = paymentClient;
//     }

//     @CircuitBreaker(
//         name = "paymentService",
//         fallbackMethod = "paymentFallback"
//     )
//     public String callPaymentService() {
//         return paymentClient.processPayment();
//     }

//     public String paymentFallback(Throwable exception) {
//         return "Payment Service is temporarily unavailable. Please try again later.";
//     }
// }


// package com.example.orderservice.service;

// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestClient;

// @Component
// public class PaymentClient {

//     private final RestClient restClient;

//     public PaymentClient(RestClient.Builder builder) {
//         this.restClient = builder
//                 .baseUrl("http://localhost:8082")
//                 .build();
//     }

//     public String processPayment() {
//         return restClient.post()
//                 .uri("/payments/process")
//                 .retrieve()
//                 .body(String.class);
//     }
// }