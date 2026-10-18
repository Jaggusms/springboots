package com.example.demo.dto;

import java.math.BigDecimal;

public record EmployeeSaveRequest(
        String name,
        BigDecimal salary,
        Long departmentId
) {
}