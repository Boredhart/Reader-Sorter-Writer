package org.example.sorting;

import org.example.entities.Department;
import org.example.entities.Employee;

import java.util.Comparator;
import java.util.List;

public class SortingService {

    public List<Employee> sort(Department department, String sortType, String order) {

        if ("none".equals(sortType)) {
            return department.getEmployees().stream().toList();
        }

        Comparator<Employee> comparator = switch (sortType.toLowerCase()) {
            case "name" -> Comparator.comparing(Employee::getName);
            case "salary" -> Comparator.comparingDouble(Employee::getSalary);
            default -> throw new IllegalArgumentException("Ошибка: Некорректный параметр сортировки.");
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return department.getEmployees().stream()
                .sorted(comparator)
                .toList();
    }
}
