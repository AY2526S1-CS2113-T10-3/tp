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

    /**
     * * Ensures that the reviews file exists.
     * * Creates both folder and file if missing.
     */

    public ReviewStorage() {
        File file = new File(FILE_PATH);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating reviews file: " + e.getMessage());
        }
    }

    /**
     * Loads all reviews from the file.
     * @return a map of course -> list of reviews
     */
    public Map<String, List<String>> load() {
        Map<String, List<String>> reviews = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    reviews.computeIfAbsent(parts[0], k -> new ArrayList<>())
                            .add(parts[1] + ": " + parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews file.");
        }
        return reviews;
    }

    /**
     * Saves all reviews to the file.
     * @param reviews map of course -> list of reviews
     */
    public void save(Map<String, List<String>> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, List<String>> entry : reviews.entrySet()) {
                for (String review : entry.getValue()) {
                    int idx = review.indexOf(": ");
                    if (idx != -1) {
                        String user = review.substring(0, idx);
                        String text = review.substring(idx + 2);
                        writer.write(entry.getKey() + "|" + user + "|" + text);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving reviews.");
        }
    }

    public static String getFilePath() {
        return FILE_PATH;
    }
}
