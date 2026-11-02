package com.example.demo.validation;

public class ValidationGroups {

    public interface Create {
    }

    public interface Update {
    }
}


package com.example.demo.dto;

import com.example.demo.validation.ValidationGroups;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    
    @Pattern(
    regexp = "^[a-zA-Z0-9]{3,15}$",
    message = "Username must contain 3-15 letters or digits"
    )
    private String username;

    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Enter a valid 10-digit Indian mobile number"
    )
    private String mobileNumber;


    @NotBlank(
        message = "Name is required",
        groups = ValidationGroups.Create.class
    )
    private String name;

    @NotBlank(
        message = "Email is required",
        groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
        }
    )
    @Email(
        message = "Invalid email",
        groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
        }
    )
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



package com.example.demo.controller;

import com.example.demo.dto.UserRequest;
import com.example.demo.validation.ValidationGroups;

import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping
    public ResponseEntity<String> createUser(
            @Validated(ValidationGroups.Create.class)
            @RequestBody UserRequest request) {

        return ResponseEntity.ok("User created");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(
            @Validated(ValidationGroups.Update.class)
            @RequestBody UserRequest request) {

        return ResponseEntity.ok("User updated");
    }
}