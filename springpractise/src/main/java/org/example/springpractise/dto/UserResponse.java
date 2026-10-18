package org.example.springpractise.dto;

public class UserResponse {

    private String name;
    private int age;

    public UserResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
