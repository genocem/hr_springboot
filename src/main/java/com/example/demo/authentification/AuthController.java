package com.example.demo.authentification;

import com.example.demo.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmployeeService employeeService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, EmployeeService employeeService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            // Call the service to register the user
            employeeService.registerEmployee(request.getName(), request.getRole(), request.getPassword(), request.getCin());

            // Return a JSON response
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            // Log the exception (optional)
            e.printStackTrace();

            // Return a JSON response for errors
            return ResponseEntity
                    .status(400)
                    .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
            );

            // Generate and return the JWT token
            String token = jwtTokenUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(Map.of("token", token));  // Return the token in a JSON response

        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Return a failure response with an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }
    }
    @PostMapping("/check")
    public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Missing or invalid Authorization header"));
            }
            String token = authorizationHeader.substring(7);

            // Validate the token
            if (!jwtTokenUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid or expired token"));
            }

            // Extract the username from the token
            String username = jwtTokenUtil.extractUsername(token);

            // Optionally, fetch user details (if needed)
            var employee = employeeService.findByName(username);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found"));
            }

            // Return success response with user details
            return ResponseEntity.ok(Map.of(
                    "message", "Token is valid",
                    "username", username,
                    "roles", employee.getRole() // Add roles or other user-specific details
            ));
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            // Return failure response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred: " + e.getMessage()));
        }
    }

}
