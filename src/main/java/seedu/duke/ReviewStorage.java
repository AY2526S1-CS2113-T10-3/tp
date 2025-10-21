package seedu.duke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewStorage {
    private static final String FILE_PATH = "data/reviews.txt";

    public Map<String, List<String>> load() {
        Map<String, List<String>> reviews = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return reviews;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    String course = parts[0];
                    String user = parts[1];
                    String text = parts[2];
                    reviews.computeIfAbsent(course, k -> new ArrayList<>()).add(user + ": " + text);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews file.");
        }
        return reviews;
    }

    public void save(Map<String, List<String>> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, List<String>> entry : reviews.entrySet()) {
                String course = entry.getKey();
                for (String reviewEntry : entry.getValue()) {
                    int idx = reviewEntry.indexOf(": ");
                    if (idx != -1) {
                        String user = reviewEntry.substring(0, idx);
                        String text = reviewEntry.substring(idx + 2);
                        writer.write(course + "|" + user + "|" + text);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving reviews.");
        }
    }
}
