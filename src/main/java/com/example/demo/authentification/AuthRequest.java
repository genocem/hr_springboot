package com.example.demo.authentification;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String password;



    // Getters and Setters


}
