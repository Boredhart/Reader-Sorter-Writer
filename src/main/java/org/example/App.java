package org.example;

import org.example.argsParser.ArgsParser;
import org.example.entities.Department;
import org.example.input.InputService;
import org.example.output.OutputToConsole;
import org.example.output.OutputToFile;
import org.example.sorting.SortingService;

import java.nio.file.Paths;
import java.util.List;

public class App {

    public static void main(String[] args) {

        try {

            ArgsParser parser = new ArgsParser(args);
            SortingService sortingService = new SortingService();
            InputService inputService = new InputService();
            OutputToConsole console = new OutputToConsole();
            OutputToFile outputFile = new OutputToFile();

            String order = parser.getArgument("order");
            String sortType = parser.getArgument("sort");
            String outputPath = parser.getArgument("path");
            String outputMode = parser.getArgument("output");


            String fileName = "test.txt";
            String path = Paths.get(System.getProperty("user.dir"), fileName).toString();

            List<Department> departments = inputService.readFromFile(path);

            switch (outputMode) {
                case "console":
                    console.toConsole(departments, sortingService, inputService, sortType, order);
                    break;
                case "file":
                    outputFile.writeToFile(outputPath, departments, sortingService, inputService, sortType, order);
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }
}
