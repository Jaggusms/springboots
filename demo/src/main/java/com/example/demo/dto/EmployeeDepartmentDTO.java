package com.example.demo.dto;

import java.math.BigDecimal;

public record EmployeeDepartmentDTO(
        Long employeeId,
        String employeeName,
        BigDecimal salary,
        String departmentName
) {
}