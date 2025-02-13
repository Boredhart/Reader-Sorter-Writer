package org.example.argsParser;

import java.util.HashMap;
import java.util.Map;

public class ArgsParser {

    private final Map<String, String> arguments = new HashMap<>();

    public ArgsParser(String[] args) {
        parseArgs(args);
        validateArgs();
    }

    private void parseArgs(String[] args) {

        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.substring(2).split("=", 2);
                if (parts.length == 2) {
                    arguments.put(parts[0], parts[1]);
                } else {
                    arguments.put(parts[0], "none");
                }
            } else if (arg.startsWith("-")) {
                String[] parts = arg.substring(1).split("=", 2);
                if (parts.length == 2) {
                    arguments.put(parts[0], parts[1]);
                } else {
                    arguments.put(parts[0], "none");
                }
            } else {
                throw new IllegalArgumentException("Ошибка: некорректный аргумент -> " + arg);
            }
        }
    }

    private void validateArgs() {

        arguments.putIfAbsent("sort", arguments.getOrDefault("s", "none"));
        arguments.putIfAbsent("order", "none");
        arguments.putIfAbsent("output", arguments.getOrDefault("o", "console"));

        String sortType = arguments.get("sort");
        String order = arguments.get("order");
        String outputMode = arguments.get("output");
        String outputPath = arguments.get("path");

        if (!"none".equals(sortType) && !"name".equals(sortType) && !"salary".equals(sortType)) {
            throw new IllegalArgumentException("Ошибка: Некорректный параметр сортировки. Доступны: name, salary.");
        }

        if (!"none".equals(order) && !"asc".equals(order) && !"desc".equals(order)) {
            throw new IllegalArgumentException("Ошибка: Некорректный порядок сортировки. Доступны: asc, desc.");
        }

        if ("none".equals(sortType) && !"none".equals(order)) {
            throw new IllegalArgumentException("Ошибка: нельзя задавать порядок сортировки без указания типа сортировки.");
        }

        if (!"console".equals(outputMode) && !"file".equals(outputMode)) {
            throw new IllegalArgumentException("Ошибка: Некорректный параметр output. Доступны: console, file.");
        }

        if ("console".equals(outputMode) && outputPath != null) {
            throw new IllegalArgumentException("Ошибка: Нельзя указывать путь без указания флага --output=file.");
        }

        if ("file".equals(outputMode) && (outputPath == null) || "none".equals(outputPath) ) {
            throw new IllegalArgumentException("Ошибка: Вывод в файл требует указания --path=\"путь к файлу\".");
        }

    }

    public String getArgument(String key) {
        return arguments.get(key);
    }

}
