package com.example.demo.dto;

public record SaveBothResponse(
        Long sqlEmployeeId,
        String mongoEmployeeId,
        String name,
        String message
) {
}