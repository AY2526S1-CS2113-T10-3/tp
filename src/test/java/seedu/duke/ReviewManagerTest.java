package seedu.duke;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ReviewManagerTest {

    private String uniqueCourse() {
        return "TEST_" + UUID.randomUUID();
    }

    @Test
    void addReview_addsSuccessfully() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Great course");
        List<String> reviews = rm.getReviews(course);

        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertTrue(reviews.get(0).contains("Maks"));
        assertTrue(reviews.get(0).contains("Great course"));
    }

    @Test
    void addReview_multipleReviewsSameUser() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "First review");
        rm.addReview(course, "Maks", "Second review");
        List<String> reviews = rm.getReviews(course);

        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        assertTrue(reviews.get(0).contains("First review"));
        assertTrue(reviews.get(1).contains("Second review"));
    }

    @Test
    void editReview_updatesTextCorrectly() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Old review");
        boolean edited = rm.editReview(course, "Maks", "Updated review", 0);
        assertTrue(edited);

        List<String> reviews = rm.getReviews(course);
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertTrue(reviews.get(0).contains("Updated review"));
        assertFalse(reviews.get(0).contains("Old review"));
    }

    @Test
    void editReview_multipleReviews_editsCorrectOne() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "First review");
        rm.addReview(course, "Maks", "Second review");

        boolean edited = rm.editReview(course, "Maks", "Updated second review", 1);
        assertTrue(edited);

        List<String> reviews = rm.getReviews(course);
        assertEquals(2, reviews.size());
        assertTrue(reviews.get(0).contains("First review"));
        assertTrue(reviews.get(1).contains("Updated second review"));
    }

    @Test
    void editReview_invalidIndex_returnsFalse() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Review");
        boolean edited = rm.editReview(course, "Maks", "Updated", 5);
        assertFalse(edited);
    }

    @Test
    void editReview_nonExistentCourse_returnsFalse() {
        ReviewManager rm = new ReviewManager();
        boolean edited = rm.editReview("NONEXISTENT", "Maks", "Updated", 0);
        assertFalse(edited);
    }

    @Test
    void deleteReview_removesReview() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "To be deleted");
        boolean deleted = rm.deleteReview(course, "Maks", 0);
        assertTrue(deleted);

        List<String> reviews = rm.getReviews(course);
        assertTrue(reviews == null || reviews.isEmpty());
    }

    @Test
    void deleteReview_multipleReviews_deletesCorrectOne() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "First review");
        rm.addReview(course, "Maks", "Second review");
        rm.addReview(course, "Maks", "Third review");

        boolean deleted = rm.deleteReview(course, "Maks", 1);
        assertTrue(deleted);

        List<String> reviews = rm.getReviews(course);
        assertEquals(2, reviews.size());
        assertTrue(reviews.get(0).contains("First review"));
        assertTrue(reviews.get(1).contains("Third review"));
    }

    @Test
    void deleteReview_invalidIndex_returnsFalse() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Review");
        boolean deleted = rm.deleteReview(course, "Maks", 5);
        assertFalse(deleted);
    }

    @Test
    void deleteReview_nonExistentCourse_returnsFalse() {
        ReviewManager rm = new ReviewManager();
        boolean deleted = rm.deleteReview("NONEXISTENT", "Maks", 0);
        assertFalse(deleted);
    }

    @Test
    void getReviews_noReviews_returnsEmptyOrNull() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        List<String> reviews = rm.getReviews(course);
        assertTrue(reviews == null || reviews.isEmpty());
    }

    @Test
    void hasCourse_existingCourse_returnsTrue() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Review");
        assertTrue(rm.hasCourse(course));
    }

    @Test
    void hasCourse_nonExistentCourse_returnsFalse() {
        ReviewManager rm = new ReviewManager();
        assertFalse(rm.hasCourse("NONEXISTENT"));
    }

    @Test
    void hasUserReview_existingReview_returnsTrue() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Review");
        assertTrue(rm.hasUserReview(course, "Maks"));
    }

    @Test
    void hasUserReview_nonExistentReview_returnsFalse() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "John", "Review");
        assertFalse(rm.hasUserReview(course, "Maks"));
    }

    @Test
    void clearAll_removesAllReviews() {
        ReviewManager rm = new ReviewManager();
        String course1 = uniqueCourse();
        String course2 = uniqueCourse();

        rm.addReview(course1, "Maks", "Review 1");
        rm.addReview(course2, "John", "Review 2");

        rm.clearAll();

        assertTrue(rm.getAllReviews().isEmpty());
    }

    @Test
    void getAllCourseIds_returnsAllCourses() {
        ReviewManager rm = new ReviewManager();
        String course1 = uniqueCourse();
        String course2 = uniqueCourse();

        rm.addReview(course1, "Maks", "Review 1");
        rm.addReview(course2, "John", "Review 2");

        assertEquals(2, rm.getAllCourseIds().size());
        assertTrue(rm.getAllCourseIds().contains(course1));
        assertTrue(rm.getAllCourseIds().contains(course2));
    }

    @Test
    void addReview_multipleDifferentUsers() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Great!");
        rm.addReview(course, "John", "Good!");
        rm.addReview(course, "Alice", "Excellent!");

        List<String> reviews = rm.getReviews(course);
        assertEquals(3, reviews.size());
    }
}
