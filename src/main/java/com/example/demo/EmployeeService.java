package com.example.demo;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee registerEmployee(String name, String role, String password, int cin) {
        if (employeeRepository.existsByName(name)) {
            throw new IllegalArgumentException("Username already exists");
        }
//        todo add checking if cin exists and stuff


        Employee employee = new Employee(name, role, cin);
        employee.setPassword(passwordEncoder.encode(password));
        return employeeRepository.save(employee);
    }
    public Employee findByName(String name) {
        return employeeRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Employee with name " + name + " not found"));
    }


}
