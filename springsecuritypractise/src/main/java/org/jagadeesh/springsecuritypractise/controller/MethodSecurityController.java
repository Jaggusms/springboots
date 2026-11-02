package org.jagadeesh.springsecuritypractise.controller;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security-demo")
public class MethodSecurityController {

    // 1. @PreAuthorize: Allow users with USER role
    @GetMapping("/preauthorize/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "Access granted: USER role";
    }

    // 2. @PreAuthorize: Allow users with ADMIN role
    @GetMapping("/preauthorize/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Access granted: ADMIN role";
    }

    // 3. @PreAuthorize: Allow either USER or ADMIN
    @GetMapping("/preauthorize/user-or-admin")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String userOrAdminAccess() {
        return "Access granted: USER or ADMIN";
    }

    // 4. @PreAuthorize: Check a specific authority
    @GetMapping("/preauthorize/read")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public String readReport() {
        return "You have REPORT_READ authority";
    }

    // 5. @PreAuthorize: Check the requested username
    @GetMapping("/preauthorize/profile/{username}")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public String accessProfile(@PathVariable String username) {
        return "You can access the profile of: " + username;
    }

    // 6. @PostAuthorize: Check the returned object's username
    @GetMapping("/postauthorize/profile/{username}")
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public Profile getProfile(@PathVariable String username) {
        // This method executes before @PostAuthorize checks the result.
        return new Profile(username, username + "@example.com");
    }

    // 7. @Secured: Restrict access to ADMIN
    @GetMapping("/secured/admin")
    @Secured("ROLE_ADMIN")
    public String securedAdmin() {
        return "Access granted using @Secured";
    }

    // 8. @RolesAllowed: Restrict access to ADMIN
    @GetMapping("/roles-allowed/admin")
    @RolesAllowed("ADMIN")
    public String rolesAllowedAdmin() {
        return "Access granted using @RolesAllowed";
    }

    // 9. @AuthenticationPrincipal: Get the logged-in user's details
    @GetMapping("/current-user")
    public String currentUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        return "Logged-in username: " + userDetails.getUsername()
               + ", authorities: " + userDetails.getAuthorities();
    }

    // 10. Read the current Authentication directly
    @GetMapping("/authentication")
    public String authenticationDetails() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return "Username: " + authentication.getName()
               + ", authorities: " + authentication.getAuthorities()
               + ", authenticated: " + authentication.isAuthenticated();
    }

    // 11. @PostFilter: Filter returned records
    @GetMapping("/postfilter/reports")
    @PostFilter("filterObject.owner == authentication.name or hasRole('ADMIN')")
    public List<Report> getReports() {

        return List.of(
                new Report(1, "User report", "jagadeesh"),
                new Report(2, "Admin report", "admin"),
                new Report(3, "Another user report", "someone")
        );
    }

    // 12. @PreFilter: Filter incoming list elements
    @PostMapping("/prefilter/names")
    @PreFilter("filterObject == authentication.name or hasRole('ADMIN')")
    public List<String> filterNames(@RequestBody List<String> names) {
        return names;
    }

    // DTO returned by the profile endpoint
    public record Profile(String username, String email) {
    }

    // DTO returned by the reports endpoint
    public record Report(int id, String title, String owner) {
    }
}