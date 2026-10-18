package org.example.springpractise.restControllerInputs;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.springpractise.dto.UserRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class InputController {

    // 1. Receive input from URL path
    // GET /api/users/101
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id) {
        return "User ID: " + id;
    }


    // 2. Receive input from query parameters
    // GET /api/users?name=Jagadeesh&age=26
    @GetMapping("/users")
    public String getUsers(
            @RequestParam String name,
            @RequestParam int age) {

        return "Name: " + name + ", Age: " + age;
    }


    // 3. Receive input from a JSON request body
    // POST /api/users
    @PostMapping("/users")
    public String createUser(@RequestBody UserRequest user) {
        return "Name: " + user.getName()
               + ", Age: " + user.getAge();
    }


    // 4. Receive input from HTTP headers
    // GET /api/header
    // Header: Authorization: Bearer abc123
    @GetMapping("/header")
    public String getHeader(
            @RequestHeader("Authorization") String authorization) {

        return "Authorization: " + authorization;
    }


    // 5. Receive input from cookies
    // GET /api/cookie
    // Cookie: sessionId=abc123
    @GetMapping("/cookie")
    public String getCookie(
            @CookieValue("sessionId") String sessionId) {

        return "Session ID: " + sessionId;
    }


    // 6. Receive all query parameters
    // GET /api/search?name=Java&city=Chennai
    @GetMapping("/search")
    public Map<String, String> search(
            @RequestParam Map<String, String> params) {

        return params;
    }


    // 7. Receive HTTP request information
    @GetMapping("/request")
    public String getRequest(HttpServletRequest request) {

        return "Method: " + request.getMethod()
               + ", URI: " + request.getRequestURI();
    }


    // 8. Receive multiple HTTP headers
    @GetMapping("/headers")
    public String getAllHeaders(@RequestHeader HttpHeaders headers) {

        return headers.toString();
    }


    // 9. Receive an uploaded file
    // POST /api/upload
    // Content-Type: multipart/form-data
    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file) {

        return "File name: " + file.getOriginalFilename()
               + ", Size: " + file.getSize();
    }


    // 10. Receive form data
    // POST /api/form
    // Content-Type: application/x-www-form-urlencoded
        @PostMapping("/form")
    public String submitForm(
            @RequestParam String name,
            @RequestParam int age) {

        return "Name: " + name + ", Age: " + age;
    }
}