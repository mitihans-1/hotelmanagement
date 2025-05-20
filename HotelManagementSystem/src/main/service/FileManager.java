package main.service;

import java.io.*;
import java.util.*;

public class FileManager {
 public static List<String[]> readFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
        }
        return data;
    }
    public static void overwriteFilearray(String filePath, List<String> lines) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error overwriting file: " + filePath);
    }
}
public static void overwriteFile(String path, List<String[]> data) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(path, false))) {
        for (String[] line : data) {
            writer.println(String.join(",", line));
        }
    } catch (IOException e) {
        System.out.println("Error overwriting file: " + e.getMessage());
    }
}


    public static void writeFile(String filePath, String content) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // 'true' means append mode
        bw.write(content);
        bw.newLine();
    } catch (IOException e) {
        System.out.println("Error writing to file: " + filePath);
    }
}

}
