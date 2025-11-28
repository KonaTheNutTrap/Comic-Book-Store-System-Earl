package utils;

import java.io.*;
import java.util.*;

/**
 * FileHandler utility class - Provides file I/O operations for the Comic Book Store System.
 * 
 * This class contains static methods for reading from and writing to text files.
 * It handles file creation, directory creation, and basic error handling for file operations.
 * 
 * @author Comic Book Store System
 * @version 1.0
 */
public class FileHandler {
    /**
     * Reads all lines from a text file and returns them as a list of strings.
     * Creates the file and necessary directories if they don't exist.
     * 
     * @param filename The path to the file to read
     * @return List of strings, each representing a line from the file
     */
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        try {
            // Ensure parent directories exist
            file.getParentFile().mkdirs();
            // Create the file if it doesn't exist
            if (!file.exists()) file.createNewFile();

            // Read all lines from the file
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
                lines.add(line.trim()); // Trim whitespace and add to list
            br.close();
        } catch (IOException e) {
            // Handle file reading errors gracefully
            System.out.println("Error reading file: " + filename);
        }
        return lines;
    }

    /**
     * Writes a list of strings to a text file, overwriting any existing content.
     * Uses try-with-resources to ensure the file is properly closed.
     * 
     * @param filename The path to the file to write
     * @param data List of strings to write to the file
     */
    public static void writeFile(String filename, List<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            // Write each string as a separate line in the file
            for (String line : data) {
                bw.write(line);
                bw.newLine(); // Add line separator after each line
            }
        } catch (IOException e) {
            // Handle file writing errors gracefully
            System.out.println("Error writing file: " + filename);
        }
    }
}
