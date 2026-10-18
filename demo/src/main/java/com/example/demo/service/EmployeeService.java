package com.example.demo.service;

import com.example.demo.dto.EmployeeDepartmentDTO;
import com.example.demo.dto.EmployeeSaveRequest;
import com.example.demo.dto.SaveBothResponse;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDocument;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.MongoEmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final MongoEmployeeRepository mongoEmployeeRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            MongoEmployeeRepository mongoEmployeeRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.mongoEmployeeRepository = mongoEmployeeRepository;
    }

    // Existing JOIN method
    @Transactional(readOnly = true)
    public List<EmployeeDepartmentDTO> getEmployeesWithDepartments() {
        return employeeRepository.findEmployeeDepartmentDetails();
    }

    // Save the employee in PostgreSQL and MongoDB
    public SaveBothResponse saveEmployeeToBoth(
            EmployeeSaveRequest request) {

        // 1. Find the department in PostgreSQL
        Department department = departmentRepository
                .findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department not found: " + request.departmentId()
                ));

        // 2. Create the JPA entity
        Employee employee = new Employee(
                request.name(),
                request.salary(),
                department
        );

        // 3. Save to PostgreSQL through Hibernate
        Employee savedEmployee = employeeRepository.save(employee);

        // 4. Create a separate MongoDB document
        EmployeeDocument document = new EmployeeDocument(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getSalary(),
                department.getId(),
                department.getName()
        );

        // 5. Save to MongoDB
        EmployeeDocument savedDocument =
                mongoEmployeeRepository.save(document);

        // 6. Return both database IDs
        return new SaveBothResponse(
                savedEmployee.getId(),
                savedDocument.getId(),
                savedEmployee.getName(),
                "Employee saved to PostgreSQL and MongoDB"
        );
    }
}