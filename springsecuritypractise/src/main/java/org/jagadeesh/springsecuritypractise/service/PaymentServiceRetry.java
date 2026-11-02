package org.jagadeesh.springsecuritypractise.service;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PaymentServiceRetry {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Retryable(
            retryFor = IOException.class,
            maxAttempts = 3
    )
    public String processPayment() throws IOException {

        int attempt = counter.incrementAndGet();

        System.out.println("Payment attempt: " + attempt);

        if (attempt < 3) {
            throw new IOException("Temporary payment service failure");
        }

        return "Payment processed successfully";
    }
}


// package com.example.demo.service;

// import org.springframework.retry.annotation.Backoff;
// import org.springframework.retry.annotation.Recover;
// import org.springframework.retry.annotation.Retryable;
// import org.springframework.stereotype.Service;

// import java.io.IOException;
// import java.util.concurrent.atomic.AtomicInteger;

// @Service
// public class InventoryService {

//     private final AtomicInteger attempts = new AtomicInteger();

//     @Retryable(
//         retryFor = IOException.class,
//         maxAttempts = 3,
//         backoff = @Backoff(delay = 1000)
//     )
//     public String checkInventory(Long productId) throws IOException {

//         int attempt = attempts.incrementAndGet();

//         System.out.println(
//                 "Calling Inventory Service. Attempt: " + attempt
//         );

//         // Simulate an external service failure.
//         throw new IOException("Inventory Service is unavailable");
//     }

//     @Recover
//     public String recoverFromIOException(
//             IOException exception,
//             Long productId) {

//         System.out.println(
//                 "All retry attempts failed for product: " + productId
//         );

//         return "Inventory temporarily unavailable for product "
//                 + productId
//                 + ". Please try again later.";
//     }
// }



// @Service
// public class ExternalService {

//     @Retryable(
//         retryFor = IOException.class,
//         maxAttempts = 3
//     )
//     public String callPaymentService(Long orderId) throws IOException {
//         throw new IOException("Payment service failed");
//     }

//     @Retryable(
//         retryFor = TimeoutException.class,
//         maxAttempts = 2
//     )
//     public String callInventoryService(Long productId)
//             throws TimeoutException {
//         throw new TimeoutException("Inventory service timed out");
//     }

//     @Recover
//     public String recoverPayment(
//             IOException exception,
//             Long orderId) {

//         return "Payment recovery for order " + orderId;
//     }

//     @Recover
//     public String recoverInventory(
//             TimeoutException exception,
//             Long productId) {

//         return "Inventory recovery for product " + productId;
//     }
// }

// It iwll match which recover to pick from paramaters...

// Remember these rules
// 1. Method name: Can be any valid Java method name.
// 2. Exception type: Spring uses it to select the appropriate recovery method.
// 3. Original arguments: Must be compatible with the retryable method's arguments.
// 4. Return type: Must match the retryable method's return type.
// 5. Multiple recovery methods: Avoid ambiguous signatures; use distinct exception or argument types.
// 6. No matching recovery method: The final exception is propagated to the caller.
// Interview answer: @Recover is Spring Retry's recovery mechanism. After all retry attempts fail, Spring selects a compatible recovery method based on the exception and original method arguments. The recovery method name itself does not determine which retry it belongs to.


// @Retryable(retryFor = RuntimeException.class, maxAttempts = 3)
// @Transactional
// public void transferMoney() {
//     // Database operations
// }



// solveing Indempotency in retry..
// package com.example.demo.entity;

// import jakarta.persistence.*;

// @Entity
// @Table(
//     name = "payments",
//     uniqueConstraints = {
//         @UniqueConstraint(
//             name = "uk_payment_idempotency_key",
//             columnNames = "idempotency_key"
//         )
//     }
// )
// public class Payment {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(name = "idempotency_key", nullable = false, unique = true)
//     private String idempotencyKey;

//     private Long customerId;

//     private int amount;

//     private String status;

//     public Payment() {
//     }

//     public Payment(String idempotencyKey, Long customerId, int amount) {
//         this.idempotencyKey = idempotencyKey;
//         this.customerId = customerId;
//         this.amount = amount;
//         this.status = "SUCCESS";
//     }

// }