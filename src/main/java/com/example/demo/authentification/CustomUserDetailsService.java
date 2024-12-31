package com.example.demo.authentification;

import com.example.demo.Employee;
import com.example.demo.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        Optional<Employee> employeeOpt = employeeRepository.findByName(username);

        // Check if the user exists
        Employee user = employeeOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Make sure to hash the password (BCrypt) when you save it and compare here
        return User.builder()
                .username(user.getName())
                .password(user.getPassword()) // Password should be hashed in DB
                .roles(user.getRole()) // Assuming the role is a single value; adapt if it's a list
                .build();
    }
}
