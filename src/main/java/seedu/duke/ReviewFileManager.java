package seedu.duke;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

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
                "CS2113|John|Great module with lots of practical examples",
                "CS2113|Mary|Could use more real-world projects",
                "CS2113|Maks|Old review"
        );

        try {
            Path path = Path.of(REVIEW_FILE_PATH);
            Files.createDirectories(path.getParent());
            Files.write(path, defaultRecords, StandardCharsets.UTF_8);
            System.out.println("Review file reset to default state.");
        } catch (IOException e) {
            System.out.println("Failed to reset review file: " + e.getMessage());
        }
    }

    public static void saveAllReviews(ReviewManager manager) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(REVIEW_FILE_PATH),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String course : manager.getAllCourseIds()) {
                List<String> reviews = manager.getReviews(course);
                for (String review : reviews) {
                    writer.write(course + "|" + review.replace(": ", "|"));
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
