package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class MongoUser {

    @Id
    private String id;

    private String name;

    private double balance;

    // Default constructor
    public MongoUser() {
    }

    // Parameterized constructor
    public MongoUser(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MongoUser{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", balance=" + balance +
               '}';
    }
}