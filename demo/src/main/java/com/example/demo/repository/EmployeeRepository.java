package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Employee;
import com.example.demo.dto.EmployeeDepartmentDTO;


public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    @Query("""
        SELECT e
        FROM Employee e
        JOIN e.department d
        """)
    List<Employee> findEmployeesWithDepartments();

    @Query("""
    SELECT new com.example.demo.dto.EmployeeDepartmentDTO(
        e.id,
        e.name,
        e.salary,
        d.name
    )
    FROM Employee e
    JOIN e.department d
    """)
    List<EmployeeDepartmentDTO> findEmployeeDepartmentDetails();
}