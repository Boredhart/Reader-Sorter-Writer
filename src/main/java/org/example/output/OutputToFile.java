package org.example.output;

import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.input.InputService;
import org.example.sorting.SortingService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputToFile {

    public void writeToFile(String filePath, List<Department> departments, SortingService sortingService,
                                InputService inputService, String sortType, String order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {

            for (Department department : departments) {
                String departmentName = department.getName();
                String manager = department.getManager().toString();

                List<Employee> employees = sortingService.sort(department, sortType, order);
                
                writer.write(departmentName);
                writer.newLine();
                writer.write(manager);
                writer.newLine();
                for (Employee employee : employees) {
                    writer.write(employee.toString());
                    writer.newLine();
                }

                writer.write(department.calculateAverageSalary());
                writer.newLine();

            }

            writer.write("Некорректные данные:");
            writer.newLine();
            for (String invalid : inputService.getInvalidData()) {
                writer.write(invalid);
                writer.newLine();
            }

            System.out.println("Объекты записаны успешно в " + filePath + ".");

        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка записи в файл: " + e.getMessage());
        }
    }
}