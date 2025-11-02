package seedu.duke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReviewCleaner {

    public void cleanInvalidReviews() {
        Path path = Path.of(ReviewStorage.getFilePath());
        if (!Files.exists(path)) {
            return;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read review file: " + e.getMessage());
            return;
        }

        List<String> cleaned = new ArrayList<>();
        int removed = 0;

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                removed++;
                continue;
            }

            String[] parts = line.split("\\|", 3);
            if (parts.length == 3) {
                cleaned.add(line);
            } else {
                removed++;
            }
        }

        try {
            Files.write(path, cleaned);
            if (removed > 0) {
                System.out.println("Review cleanup finished. Removed " + removed + " invalid entries.");
            }
        } catch (IOException e) {
            System.out.println("Failed to rewrite review file: " + e.getMessage());
        }
    }
}
