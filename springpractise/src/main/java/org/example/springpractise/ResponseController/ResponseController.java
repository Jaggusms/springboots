package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.springpractise.dto.UserResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/response")
public class ResponseController {

    // Method 1: Return a simple String
    @GetMapping("/method1")
    public String method1() {
        return "Hello Jagadeesh";
    }


    // Method 2: Return a String using ResponseEntity
    @GetMapping("/method2")
    public ResponseEntity<String> method2() {
        return ResponseEntity.ok("Success");
    }


    // Method 3: Return an object as JSON
    @GetMapping("/method3")
    public ResponseEntity<UserResponse> method3() {

        UserResponse user = new UserResponse("Jagadeesh", 26);

        return ResponseEntity.ok(user);
    }


    // Method 4: Return a List of objects
    @GetMapping("/method4")
    public ResponseEntity<List<UserResponse>> method4() {

        List<UserResponse> users = List.of(
                new UserResponse("Jagadeesh", 26),
                new UserResponse("Ravi", 25),
                new UserResponse("Kiran", 27)
        );

        return ResponseEntity.ok(users);
    }


    // Method 5: Return a Map as JSON
    @GetMapping("/method5")
    public ResponseEntity<Map<String, Object>> method5() {

        Map<String, Object> response = Map.of(
                "message", "Success",
                "status", 200,
                "name", "Jagadeesh"
        );

        return ResponseEntity.ok(response);
    }


    // Method 6: Return HTTP 201 Created
    @PostMapping("/method6")
    public ResponseEntity<UserResponse> method6() {

        UserResponse user = new UserResponse("New User", 22);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }


    // Method 7: Return HTTP 400 Bad Request
    @GetMapping("/method7")
    public ResponseEntity<String> method7() {

        return ResponseEntity
                .badRequest()
                .body("Invalid request");
    }


    // Method 8: Return HTTP 404 Not Found
    @GetMapping("/method8")
    public ResponseEntity<String> method8() {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User not found");
    }


    // Method 9: Return HTTP 404 without a response body
    @GetMapping("/method9")
    public ResponseEntity<Void> method9() {

        return ResponseEntity
                .notFound()
                .build();
    }


    // Method 10: Return HTTP 204 No Content
    @DeleteMapping("/method10")
    public ResponseEntity<Void> method10() {

        // Perform the delete operation here.

        return ResponseEntity
                .noContent()
                .build();
    }


    // Method 11: Return a custom response header
    @GetMapping("/method11")
    public ResponseEntity<String> method11() {

        return ResponseEntity
                .ok()
                .header("X-App-Name", "UserService")
                .body("Custom header added");
    }


    // Method 12: Return different responses based on a condition
    @GetMapping("/method12/{age}")
    public ResponseEntity<?> method12(@PathVariable int age) {

        if (age < 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Age cannot be negative");
        }

        if (age < 18) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("User must be at least 18");
        }

        UserResponse user = new UserResponse("Jagadeesh", age);

        return ResponseEntity.ok(user);
    }


    // Method 13: Return a response using a request parameter
    @GetMapping("/method13")
    public ResponseEntity<String> method13(
            @RequestParam String name) {

        return ResponseEntity.ok("Hello " + name);
    }


    // Method 14: Return an empty successful response
    @GetMapping("/method14")
    public ResponseEntity<Void> method14() {

        return ResponseEntity.ok().build();
    }
}