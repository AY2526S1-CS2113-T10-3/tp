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
     * Format: COURSE|USER|REVIEW_TEXT_1||REVIEW_TEXT_2||REVIEW_TEXT_3
     * @return a map of course -> list of reviews (format: "user: text")
     */
    public Map<String, List<String>> load() {
        Map<String, List<String>> reviews = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    String course = parts[0];
                    String user = parts[1];
                    String reviewTexts = parts[2];

                    // Split multiple reviews by "||"
                    String[] texts = reviewTexts.split("\\|\\|");

                    reviews.computeIfAbsent(course, k -> new ArrayList<>());

                    for (String text : texts) {
                        if (!text.trim().isEmpty()) {
                            reviews.get(course).add(user + ": " + text.trim());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews file.");
        }
        return reviews;
    }

    /**
     * Saves all reviews to the file.
     * Groups reviews by course and user, concatenating multiple reviews with "||"
     * @param reviews map of course -> list of reviews
     */
    public void save(Map<String, List<String>> reviews) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Group reviews by course and user
            Map<String, Map<String, List<String>>> grouped = new HashMap<>();

            for (Map.Entry<String, List<String>> entry : reviews.entrySet()) {
                String course = entry.getKey();
                grouped.putIfAbsent(course, new HashMap<>());

                for (String review : entry.getValue()) {
                    int idx = review.indexOf(": ");
                    if (idx != -1) {
                        String user = review.substring(0, idx);
                        String text = review.substring(idx + 2);

                        grouped.get(course).computeIfAbsent(user, k -> new ArrayList<>()).add(text);
                    }
                }
            }

            // Write grouped reviews
            for (Map.Entry<String, Map<String, List<String>>> courseEntry : grouped.entrySet()) {
                String course = courseEntry.getKey();
                for (Map.Entry<String, List<String>> userEntry : courseEntry.getValue().entrySet()) {
                    String user = userEntry.getKey();
                    String concatenatedTexts = String.join("||", userEntry.getValue());
                    writer.write(course + "|" + user + "|" + concatenatedTexts);
                    writer.newLine();
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
