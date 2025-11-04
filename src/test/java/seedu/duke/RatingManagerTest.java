package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RatingManagerTest {
    private static final String TEST_FILE = "data/ratings.txt";

    private RatingManager ratingManager;

    @BeforeEach
    public void setUp() {
        deleteTestFile();
        ratingManager = new RatingManager();
    }

    @AfterEach
    public void tearDown() {
        deleteTestFile();
    }

    private void deleteTestFile() {
        try {
            Path path = Paths.get(TEST_FILE);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // Ignore
        }
    }

    @Test
    public void addRating_singleRating_updatesCorrectly() {
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
    public void addRating_allSameRating_correctAverage() {
        ratingManager.addRating("CS2113", 4);
        ratingManager.addRating("CS2113", 4);
        ratingManager.addRating("CS2113", 4);

        assertEquals(4.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void addRating_extremeRatings_correctAverage() {
        ratingManager.addRating("CS2113", 1);
        ratingManager.addRating("CS2113", 5);

        assertEquals(3.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void addRating_caseInsensitive_treatsSameModule() {
        ratingManager.addRating("CS2113", 5);
        ratingManager.addRating("cs2113", 3);
        ratingManager.addRating("Cs2113", 4);

        assertEquals(3, ratingManager.getCount("CS2113"));
        assertEquals(4.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void addRating_multipleModules_maintainsSeparately() {
        ratingManager.addRating("CS2113", 5);
        ratingManager.addRating("CS2040", 3);

        assertEquals(1, ratingManager.getCount("CS2113"));
        assertEquals(1, ratingManager.getCount("CS2040"));
        assertEquals(5.0, ratingManager.getAverage("CS2113"), 0.01);
        assertEquals(3.0, ratingManager.getAverage("CS2040"), 0.01);
    }

    @Test
    public void addRating_manyRatings_correctAverage() {
        for (int i = 1; i <= 100; i++) {
            ratingManager.addRating("CS2113", 3);
        }

        assertEquals(100, ratingManager.getCount("CS2113"));
        assertEquals(3.0, ratingManager.getAverage("CS2113"), 0.01);
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
    public void getAverage_afterAddingRatings_updatesCorrectly() {
        assertEquals(0.0, ratingManager.getAverage("CS2113"), 0.01);

        ratingManager.addRating("CS2113", 4);
        assertEquals(4.0, ratingManager.getAverage("CS2113"), 0.01);

        ratingManager.addRating("CS2113", 2);
        assertEquals(3.0, ratingManager.getAverage("CS2113"), 0.01);
    }

    @Test
    public void getmoduleRatings_returnsNonNull() {
        assertNotNull(ratingManager.getmoduleRatings());
    }

    @Test
    public void getmoduleRatings_afterAddingRatings_containsModule() {
        ratingManager.addRating("CS2113", 5);

        assertTrue(ratingManager.getmoduleRatings().containsKey("CS2113"));
    }

    @Test
    public void pruneAgainst_removesNonExistentModules() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS2113", "SE", "Monday", "10:00", "12:00", "lecture"));

        ratingManager.addRating("CS2113", 5);
        ratingManager.addRating("CS9999", 3);

        ratingManager.pruneAgainst(modules);

        assertEquals(1, ratingManager.getCount("CS2113"));
        assertEquals(0, ratingManager.getCount("CS9999"));
    }

    @Test
    public void pruneAgainst_nullModuleList_doesNothing() {
        ratingManager.addRating("CS2113", 5);

        assertDoesNotThrow(() -> ratingManager.pruneAgainst(null));

        assertEquals(1, ratingManager.getCount("CS2113"));
    }

    @Test
    public void pruneAgainst_emptyModuleList_removesAll() {
        ModuleList modules = new ModuleList();

        ratingManager.addRating("CS2113", 5);
        ratingManager.addRating("CS2040", 3);

        ratingManager.pruneAgainst(modules);

        assertEquals(0, ratingManager.getCount("CS2113"));
        assertEquals(0, ratingManager.getCount("CS2040"));
    }

    @Test
    public void addRating_boundaryValues_works() {
        ratingManager.addRating("CS2113", 1);
        assertEquals(1.0, ratingManager.getAverage("CS2113"), 0.01);

        ratingManager.addRating("CS2040", 5);
        assertEquals(5.0, ratingManager.getAverage("CS2040"), 0.01);
    }

    @Test
    public void persistence_savesAndLoads() {
        ratingManager.addRating("CS2113", 4);
        ratingManager.addRating("CS2113", 5);

        // Create new manager (should load from file)
        RatingManager newManager = new RatingManager();

        assertEquals(2, newManager.getCount("CS2113"));
        assertEquals(4.5, newManager.getAverage("CS2113"), 0.01);
    }
}
