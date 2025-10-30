package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RatingManagerTest {
    private RatingManager ratingManager;
    private static final String TEST_FILE = "data/ratings.txt";

    @BeforeEach
    public void setUp() {
        // Delete the ratings file before each test to ensure isolation
        deleteTestFile();
        ratingManager = new RatingManager();
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test
        deleteTestFile();
    }

    private void deleteTestFile() {
        try {
            Path path = Paths.get(TEST_FILE);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // Ignore - file might not exist
        }
    }

    @Test
    public void addRating_validRating_updatesAverage() {
        ratingManager.addRating("CS2113", 5);

        assertEquals(1, ratingManager.getCount("CS2113"));
        assertEquals(5.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void addRating_multipleRatings_calculatesCorrectAverage() {
        ratingManager.addRating("CS2113", 5);
        ratingManager.addRating("CS2113", 3);
        ratingManager.addRating("CS2113", 4);

        assertEquals(3, ratingManager.getCount("CS2113"));
        assertEquals(4.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void addRating_caseInsensitive_treatsSameModule() {
        ratingManager.addRating("CS2113", 5);
        ratingManager.addRating("cs2113", 3);

        assertEquals(2, ratingManager.getCount("CS2113"));
        assertEquals(4.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void getCount_nonExistentModule_returnsZero() {
        assertEquals(0, ratingManager.getCount("CS9999"));
    }

    @Test
    public void getAverage_nonExistentModule_returnsZero() {
        assertEquals(0.0, ratingManager.getAverage("CS9999"), 0.01);
    }

    @Test
    public void getmoduleRatings_returnsNonNull() {
        assertNotNull(ratingManager.getmoduleRatings());
    }
}
