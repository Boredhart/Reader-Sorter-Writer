package org.example.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Manager extends Person {

    private final String department;

    public Manager(Long id, String name, Double salary, String department) {
        super(id, name, salary);
        this.department = department;
    }

    @Override
    public String toString() {
        return "Manager," + super.getId() + ", " + super.getName() + ", " + super.getSalary();
    }
}
