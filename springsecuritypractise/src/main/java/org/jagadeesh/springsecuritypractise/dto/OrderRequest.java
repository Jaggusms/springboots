package org.jagadeesh.springsecuritypractise.dto;
public record OrderRequest(
        String orderId,
        String customerName,
        double amount
) {
}
