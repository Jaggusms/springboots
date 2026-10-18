package com.example.demo.repository;

import com.example.demo.model.EmployeeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoEmployeeRepository
        extends MongoRepository<EmployeeDocument, String> {
}