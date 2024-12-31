package com.example.demo.authentification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;


@Configuration
public class JwtConfig {

    private final JwtTokenUtil jwtTokenUtil;  // Inject the JwtTokenUtil to access the secret key

    // Constructor injection for JwtTokenUtil
    public JwtConfig(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Use the secret key generated in JwtTokenUtil for decoding the JWT
        return NimbusJwtDecoder.withSecretKey(jwtTokenUtil.getSecretKey()).build();
    }
}
