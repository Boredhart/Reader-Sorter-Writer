package org.example.input;

import lombok.Getter;
import org.example.entities.Department;
import org.example.entities.Employee;
import org.example.entities.Manager;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class InputService {

    private final Map<String, Department> departments = new TreeMap<>();

    @Getter
    private final List<String> invalidData = new ArrayList<>();

    public List<Department> readFromFile(String filePath) {
        File file = new File(filePath);
        Path path = file.toPath();

        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Ошибка: Файл для чтения не найден.");
        }

        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Ошибка: Из этого файла невозможно читать.");
        }

        List<String> managerLines = new ArrayList<>();
        List<String> employeeLines = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Ошибка: Файл пуст.");
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("Manager") || line.startsWith("manager")) {
                    managerLines.add(line);
                } else if (line.startsWith("Employee") || line.startsWith("employee")) {
                    employeeLines.add(line);
                } else {
                    invalidData.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Ошибка: Файл не найден.");
        }

        for (String line : managerLines) {
            processLine(line, true);
        }

        for (String line : employeeLines) {
            processLine(line, false);
        }

        return new ArrayList<>(departments.values());

    }

    private void processLine(String line, boolean isManager) {

        String[] parts = line.split(",");

        if (parts.length != 5) {
            invalidData.add(line);
            return;
        }

        Long id;
        String name;
        double salary;
        String departmentOrManagerId;

        try {
            id = Long.parseLong(parts[1].trim());
            name = parts[2].trim();
            salary = Double.parseDouble(parts[3].trim());
            departmentOrManagerId = parts[4].trim();

            if (salary <= 0) {
                invalidData.add(line);
                return;
            }
        } catch (NumberFormatException e) {
            invalidData.add(line);
            return;
        }

        if (isManager) {
            processManager(id, name, salary, departmentOrManagerId);
        } else {
            processEmployee(id, name, salary, departmentOrManagerId);
        }

    }

    private void processManager(Long id, String name, double salary, String departmentName) {

        Manager manager = new Manager(id, name, salary, departmentName);

        departments.putIfAbsent(departmentName, new Department(departmentName, manager));

    }

    private void processEmployee(Long id, String name, double salary, String managerId) {

        Employee employee = new Employee(id, name, salary, Long.parseLong(managerId));

        for (Department department : departments.values()) {
            if (department.hasManager(Long.parseLong(managerId))) {
                department.addEmployee(employee);
                return;
            }
        }

        invalidData.add("Employee," + id + "," + name + "," + salary + "," + managerId);
    }

}
