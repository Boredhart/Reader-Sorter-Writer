package org.example.output;

import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.input.InputService;
import org.example.sorting.SortingService;

import java.util.List;

public class OutputToConsole {

    public void toConsole(List<Department> departments, SortingService sortingService, InputService inputService,
                          String sortType, String order) {

        for (Department department : departments) {
            String departmentName = department.getName();
            String manager = department.getManager().toString();

            System.out.println(departmentName);
            System.out.println(manager);

            List<Employee> employees = sortingService.sort(department, sortType, order);
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }

            System.out.println(department.calculateAverageSalary());
        }

        System.out.println("Некорректные данные:");
        for (String invalid : inputService.getInvalidData()) {
            System.out.println(invalid);
        }
    }

}
