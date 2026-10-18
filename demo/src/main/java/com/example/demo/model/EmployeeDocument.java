package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "employees")
public class EmployeeDocument {

    @Id
    private String id;

    private Long sqlEmployeeId;
    private String name;
    private BigDecimal salary;
    private Long departmentId;
    private String departmentName;

    public EmployeeDocument() {
    }

    public EmployeeDocument(
            Long sqlEmployeeId,
            String name,
            BigDecimal salary,
            Long departmentId,
            String departmentName) {

        this.sqlEmployeeId = sqlEmployeeId;
        this.name = name;
        this.salary = salary;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getId() {
        return id;
    }

    public Long getSqlEmployeeId() {
        return sqlEmployeeId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}