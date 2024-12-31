package com.example.demo;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Employee {

    @Setter
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String role;
    @Setter
    @Getter
    private int cin;

    @Setter
    @Getter
    private String password;

    protected Employee() {
        this.name = "";
        this.role = "";
        this.cin = 0;
    }

    Employee(String name, String role, int cin) {
        this.name = name;
        this.role = role;
        this.cin = cin;
    }
    public Employee(String name, String role) {

        this.name = name;
        this.role = role;
        this.cin = 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee employee))
            return false;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }


}