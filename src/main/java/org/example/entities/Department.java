package org.example.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Department {

    private final String name;
    private final Manager manager;
    private final List<Employee> employees;

    public Department(String name, Manager manager) {
        this.name = name;
        this.manager = manager;
        this.employees = new ArrayList<>();
    }

    public boolean hasManager(long id) {
        return manager != null && manager.getId() == id;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public int getEmployeeCount() {
        return employees.size() + 1;
    }

    public String calculateAverageSalary() {

        if (employees.isEmpty()) {
            return "0.0";
        }

        double sum = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        sum += manager.getSalary();

        double average = Math.ceil(sum / (employees.size() + 1) * 100) / 100;

        return getEmployeeCount() + ", " + average;
    }

}
