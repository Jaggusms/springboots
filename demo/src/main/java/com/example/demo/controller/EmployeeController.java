package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.dto.EmployeeDepartmentDTO;
import com.example.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.EmployeeSaveRequest;
import com.example.demo.dto.SaveBothResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/with-departments")
    public List<EmployeeDepartmentDTO> getEmployeesWithDepartments() {
        return employeeService.getEmployeesWithDepartments();
    }
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PostMapping("/save-both")
    public SaveBothResponse saveEmployeeToBoth(
            @RequestBody EmployeeSaveRequest request) {

        return employeeService.saveEmployeeToBoth(request);
    }
}