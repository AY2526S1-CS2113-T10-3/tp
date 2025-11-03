package seedu.duke;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ReviewFileManager {

    private static final String REVIEW_FILE_PATH = "data/reviews.txt";

    public static void ensureFileExists() {
        Path path = Path.of(REVIEW_FILE_PATH);
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                resetToDefault();
                System.out.println("Review file created with default records.");
            }
        } catch (IOException e) {
            System.out.println("Failed to ensure review file exists: " + e.getMessage());
        }
    }

    public static void resetToDefault() {
        List<String> defaultRecords = List.of(
                "MA1521|Alex|Challenging but rewarding",
                "CS2113|John|Great module with lots of practical examples||Very practical and hands-on",
                "CS2113|Mary|Could use more real-world projects",
                "CS2113|Maks|Old review||Another review from Maks"
        );

        try {
            Path path = Path.of(REVIEW_FILE_PATH);
            Files.createDirectories(path.getParent());
            Files.write(path, defaultRecords, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Failed to reset review file: " + e.getMessage());
        }
    }

    public static void saveAllReviews(ReviewManager manager) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(REVIEW_FILE_PATH),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            // Group reviews by course and user
            Map<String, Map<String, List<String>>> grouped = new HashMap<>();

            for (String course : manager.getAllCourseIds()) {
                List<String> reviews = manager.getReviews(course);
                grouped.putIfAbsent(course, new HashMap<>());

                for (String review : reviews) {
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
            System.out.println("Error saving reviews: " + e.getMessage());
        }
    }

    public static String getFilePath() {
        return REVIEW_FILE_PATH;
    }
}
