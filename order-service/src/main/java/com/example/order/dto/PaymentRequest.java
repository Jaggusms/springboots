package com.example.order.dto;

public class PaymentRequest {
    private Long orderId;
    private Double amount;

    public PaymentRequest(Long orderId, Double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public Long getOrderId() { return orderId; }
    public Double getAmount() { return amount; }
}