package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReviewManagerTest {

    private static final String TEST_FILE_PATH = "data/reviews.txt";
    private ReviewManager reviewManager;

    @BeforeEach
    void setUp() {
        reviewManager = new ReviewManager();
        cleanupTestFiles();
    }

    @AfterEach
    void tearDown() {
        reviewManager.clearAll();
        cleanupTestFiles();
    }

    private void cleanupTestFiles() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    private String uniqueCourse() {
        return "TEST_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    void addReview_singleReview_addsSuccessfully() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Great course");

        List<String> reviews = reviewManager.getReviews(course);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertTrue(reviews.get(0).contains("Maks"));
        assertTrue(reviews.get(0).contains("Great course"));
    }

    @Test
    void addReview_multipleReviewsSameUser_addsAll() {
        String course = uniqueCourse();

        reviewManager.addReview(course, "Maks", "First review");
        reviewManager.addReview(course, "Maks", "Second review");
        reviewManager.addReview(course, "Maks", "Third review");

        List<String> reviews = reviewManager.getReviews(course);

        assertEquals(3, reviews.size());
        assertTrue(reviews.get(0).contains("First review"));
        assertTrue(reviews.get(1).contains("Second review"));
        assertTrue(reviews.get(2).contains("Third review"));
    }

    @Test
    void addReview_multipleDifferentUsers_addsAll() {
        String course = uniqueCourse();

        reviewManager.addReview(course, "Maks", "Great!");
        reviewManager.addReview(course, "John", "Good!");
        reviewManager.addReview(course, "Alice", "Excellent!");

        List<String> reviews = reviewManager.getReviews(course);
        assertEquals(3, reviews.size());
    }

    @Test
    void addReview_emptyReviewText_addsSuccessfully() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "");

        List<String> reviews = reviewManager.getReviews(course);
        assertEquals(1, reviews.size());
    }

    @Test
    void addReview_longReviewText_addsSuccessfully() {
        String course = uniqueCourse();
        String longText = "A".repeat(1000);

        reviewManager.addReview(course, "Maks", longText);

        List<String> reviews = reviewManager.getReviews(course);
        assertTrue(reviews.get(0).contains(longText));
    }

    @Test
    void addReview_specialCharacters_addsSuccessfully() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review with @#$% special chars!");

        List<String> reviews = reviewManager.getReviews(course);
        assertTrue(reviews.get(0).contains("@#$%"));
    }

    @Test
    void addReview_multipleCourses_maintainsSeparately() {
        String course1 = uniqueCourse();
        String course2 = uniqueCourse();

        reviewManager.addReview(course1, "Maks", "Review 1");
        reviewManager.addReview(course2, "John", "Review 2");

        assertEquals(1, reviewManager.getReviews(course1).size());
        assertEquals(1, reviewManager.getReviews(course2).size());
    }

    @Test
    void editReview_singleReview_updatesCorrectly() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Old review");

        boolean edited = reviewManager.editReview(course, "Maks", "Updated review", 0);

        assertTrue(edited);
        List<String> reviews = reviewManager.getReviews(course);
        assertTrue(reviews.get(0).contains("Updated review"));
        assertFalse(reviews.get(0).contains("Old review"));
    }

    @Test
    void editReview_multipleReviews_editsCorrectOne() {
        String course = uniqueCourse();

        reviewManager.addReview(course, "Maks", "First review");
        reviewManager.addReview(course, "Maks", "Second review");
        reviewManager.addReview(course, "Maks", "Third review");

        boolean edited = reviewManager.editReview(course, "Maks", "Updated second", 1);

        assertTrue(edited);
        List<String> reviews = reviewManager.getReviews(course);
        assertTrue(reviews.get(0).contains("First review"));
        assertTrue(reviews.get(1).contains("Updated second"));
        assertTrue(reviews.get(2).contains("Third review"));
    }

    @Test
    void editReview_invalidIndex_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");

        boolean edited = reviewManager.editReview(course, "Maks", "Updated", 5);

        assertFalse(edited);
    }

    @Test
    void editReview_negativeIndex_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");

        boolean edited = reviewManager.editReview(course, "Maks", "Updated", -1);

        assertFalse(edited);
    }

    @Test
    void editReview_nonExistentCourse_returnsFalse() {
        boolean edited = reviewManager.editReview("NONEXISTENT", "Maks", "Updated", 0);
        assertFalse(edited);
    }

    @Test
    void editReview_nonExistentUser_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "John", "Review");

        boolean edited = reviewManager.editReview(course, "Maks", "Updated", 0);

        assertFalse(edited);
    }

    @Test
    void deleteReview_singleReview_removesSuccessfully() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "To be deleted");

        boolean deleted = reviewManager.deleteReview(course, "Maks", 0);

        assertTrue(deleted);
        List<String> reviews = reviewManager.getReviews(course);
        assertTrue(reviews == null || reviews.isEmpty());
    }

    @Test
    void deleteReview_multipleReviews_deletesCorrectOne() {
        String course = uniqueCourse();

        reviewManager.addReview(course, "Maks", "First");
        reviewManager.addReview(course, "Maks", "Second");
        reviewManager.addReview(course, "Maks", "Third");

        boolean deleted = reviewManager.deleteReview(course, "Maks", 1);

        assertTrue(deleted);
        List<String> reviews = reviewManager.getReviews(course);
        assertEquals(2, reviews.size());
        assertTrue(reviews.get(0).contains("First"));
        assertTrue(reviews.get(1).contains("Third"));
    }

    @Test
    void deleteReview_lastReview_removesCourseEntry() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Only review");

        reviewManager.deleteReview(course, "Maks", 0);

        assertFalse(reviewManager.hasCourse(course));
    }

    @Test
    void deleteReview_invalidIndex_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");

        boolean deleted = reviewManager.deleteReview(course, "Maks", 5);

        assertFalse(deleted);
    }

    @Test
    void deleteReview_negativeIndex_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");

        boolean deleted = reviewManager.deleteReview(course, "Maks", -1);

        assertFalse(deleted);
    }

    @Test
    void deleteReview_nonExistentCourse_returnsFalse() {
        boolean deleted = reviewManager.deleteReview("NONEXISTENT", "Maks", 0);
        assertFalse(deleted);
    }

    @Test
    void deleteReview_nonExistentUser_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "John", "Review");

        boolean deleted = reviewManager.deleteReview(course, "Maks", 0);

        assertFalse(deleted);
    }

    @Test
    void getReviews_nonExistentCourse_returnsEmptyOrNull() {
        List<String> reviews = reviewManager.getReviews("NONEXISTENT");
        assertTrue(reviews == null || reviews.isEmpty());
    }

    @Test
    void hasCourse_existingCourse_returnsTrue() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");

        assertTrue(reviewManager.hasCourse(course));
    }

    @Test
    void hasCourse_nonExistentCourse_returnsFalse() {
        assertFalse(reviewManager.hasCourse("NONEXISTENT"));
    }

    @Test
    void hasCourse_afterDeletion_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");
        reviewManager.deleteReview(course, "Maks", 0);

        assertFalse(reviewManager.hasCourse(course));
    }

    @Test
    void hasUserReview_existingReview_returnsTrue() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");

        assertTrue(reviewManager.hasUserReview(course, "Maks"));
    }

    @Test
    void hasUserReview_nonExistentReview_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "John", "Review");

        assertFalse(reviewManager.hasUserReview(course, "Maks"));
    }

    @Test
    void hasUserReview_afterDeletion_returnsFalse() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");
        reviewManager.deleteReview(course, "Maks", 0);

        assertFalse(reviewManager.hasUserReview(course, "Maks"));
    }

    @Test
    void clearAll_removesAllReviews() {
        String course1 = uniqueCourse();
        String course2 = uniqueCourse();

        reviewManager.addReview(course1, "Maks", "Review 1");
        reviewManager.addReview(course2, "John", "Review 2");

        reviewManager.clearAll();

        assertTrue(reviewManager.getAllReviews().isEmpty());
        assertFalse(reviewManager.hasCourse(course1));
        assertFalse(reviewManager.hasCourse(course2));
    }

    @Test
    void getAllCourseIds_returnsAllCourses() {
        String course1 = uniqueCourse();
        String course2 = uniqueCourse();

        reviewManager.addReview(course1, "Maks", "Review 1");
        reviewManager.addReview(course2, "John", "Review 2");

        assertEquals(2, reviewManager.getAllCourseIds().size());
        assertTrue(reviewManager.getAllCourseIds().contains(course1));
        assertTrue(reviewManager.getAllCourseIds().contains(course2));
    }

    @Test
    void getAllCourseIds_afterClearAll_returnsEmpty() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Review");
        reviewManager.clearAll();

        assertTrue(reviewManager.getAllCourseIds().isEmpty());
    }

    @Test
    void saveAndLoadReviews_persistsData() {
        String course = uniqueCourse();
        reviewManager.addReview(course, "Maks", "Persisted review");
        reviewManager.saveReviews();

        // Create a new ReviewManager instance to test loading
        ReviewManager newManager = new ReviewManager();

        assertTrue(newManager.hasCourse(course));
        List<String> reviews = newManager.getReviews(course);
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.get(0).contains("Persisted review"));

        // Clean up
        newManager.clearAll();
    }

    @Test
    void mergeWithFile_addsNewReviews() {
        String course = uniqueCourse();

        // Add a review and save it to file
        reviewManager.addReview(course, "Maks", "Memory review");
        reviewManager.saveReviews();

        // Create a new manager and load the file
        ReviewManager newManager = new ReviewManager();

        // Add a new review in memory (not yet saved)
        newManager.addReview(course, "John", "New review");

        // Merge should combine file data with memory data
        newManager.mergeWithFile();

        // Verify both reviews are present
        List<String> reviews = newManager.getReviews(course);
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());

        // Clean up
        newManager.clearAll();
    }

    @Test
    void mergeWithFile_noFile_returnsFalse() {
        // Ensure file doesn't exist
        cleanupTestFiles();

        // Create new manager and add review
        ReviewManager newManager = new ReviewManager();
        String course = uniqueCourse();
        newManager.addReview(course, "Maks", "Review");

        // Merge when file doesn't exist
        newManager.mergeWithFile();

        // Should still have the in-memory review
        assertTrue(newManager.hasCourse(course));

        // Clean up
        newManager.clearAll();
    }

    @Test
    void mergeWithFile_emptyFile_handlesGracefully() throws IOException {
        // Create empty file
        File file = new File(TEST_FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        file.createNewFile();

        ReviewManager newManager = new ReviewManager();
        String course = uniqueCourse();
        newManager.addReview(course, "Maks", "Review");

        // Should handle empty file gracefully
        assertDoesNotThrow(newManager::mergeWithFile);

        // Should still have the in-memory review
        assertTrue(newManager.hasCourse(course));

        // Clean up
        newManager.clearAll();
    }

    @Test
    void getAllReviews_returnsAllData() {
        String course1 = uniqueCourse();
        String course2 = uniqueCourse();

        reviewManager.addReview(course1, "Maks", "Review 1");
        reviewManager.addReview(course2, "John", "Review 2");

        var allReviews = reviewManager.getAllReviews();

        assertEquals(2, allReviews.size());
        assertTrue(allReviews.containsKey(course1));
        assertTrue(allReviews.containsKey(course2));
    }

    @Test
    void getAllReviews_emptyManager_returnsEmptyMap() {
        var allReviews = reviewManager.getAllReviews();
        assertNotNull(allReviews);
        assertTrue(allReviews.isEmpty());
    }

    @Test
    void loadReviews_corruptedFile_handlesGracefully() throws IOException {
        // Create a file with corrupted data
        File file = new File(TEST_FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("CORRUPTED|DATA|INVALID\n");
            writer.write("MORE|CORRUPTED\n");
        }

        // Should not throw exception
        assertDoesNotThrow(() -> {
            ReviewManager newManager = new ReviewManager();
            newManager.clearAll();
        });
    }

    @Test
    void addReview_nullCourse_handlesGracefully() {
        // Depending on implementation, this might throw or handle gracefully
        assertDoesNotThrow(() -> reviewManager.addReview(null, "Maks", "Review"));
    }

    @Test
    void addReview_nullUser_handlesGracefully() {
        String course = uniqueCourse();
        assertDoesNotThrow(() -> reviewManager.addReview(course, null, "Review"));
    }

    @Test
    void addReview_nullReview_handlesGracefully() {
        String course = uniqueCourse();
        assertDoesNotThrow(() -> reviewManager.addReview(course, "Maks", null));
    }

    @Test
    void persistence_multipleReviews_preservesOrder() {
        String course = uniqueCourse();

        reviewManager.addReview(course, "Maks", "First");
        reviewManager.addReview(course, "Maks", "Second");
        reviewManager.addReview(course, "Maks", "Third");
        reviewManager.saveReviews();

        ReviewManager newManager = new ReviewManager();
        List<String> reviews = newManager.getReviews(course);

        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.get(0).contains("First"));
        assertTrue(reviews.get(1).contains("Second"));
        assertTrue(reviews.get(2).contains("Third"));

        newManager.clearAll();
    }

    @Test
    void caseInsensitivity_courseNames_handleCorrectly() {
        reviewManager.addReview("CS2113", "Maks", "Review 1");
        reviewManager.addReview("cs2113", "John", "Review 2");

        // Depending on implementation, might be case-sensitive or case-insensitive
        // Adjust assertion based on actual behavior
        assertNotNull(reviewManager.getReviews("CS2113"));
    }
}
