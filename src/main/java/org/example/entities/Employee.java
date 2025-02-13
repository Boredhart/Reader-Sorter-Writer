package org.example.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {

    private final Long managerId;

    public Employee(Long id, String name, Double salary, Long managerId) {
        super(id, name, salary);
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Employee," + super.getId() + ", " + super.getName() + ", " + super.getSalary();
    }
}
