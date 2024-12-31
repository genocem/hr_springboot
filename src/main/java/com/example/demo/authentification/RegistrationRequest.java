package com.example.demo.authentification;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class RegistrationRequest {
    @Getter
    @NotBlank
    private String name;

    @Getter
    @NotBlank
    private String role;

    @Getter
    @NotBlank
    private String password;

    @Getter
    private int cin;

    // Getters and Setters
}
