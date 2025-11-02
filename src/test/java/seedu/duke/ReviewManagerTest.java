package seedu.duke;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void editReview_updatesTextCorrectly() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "Old review");
        boolean edited = rm.editReview(course, "Maks", "Updated review");
        assertTrue(edited);

        List<String> reviews = rm.getReviews(course);
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertTrue(reviews.get(0).contains("Updated review"));
    }

    @Test
    void deleteReview_removesReview() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        rm.addReview(course, "Maks", "To be deleted");
        boolean deleted = rm.deleteReview(course, "Maks");
        assertTrue(deleted);

        List<String> reviews = rm.getReviews(course);
        assertTrue(reviews == null || reviews.isEmpty());
    }

    @Test
    void getReviews_noReviews_returnsEmptyOrNull() {
        ReviewManager rm = new ReviewManager();
        String course = uniqueCourse();

        List<String> reviews = rm.getReviews(course);
        assertTrue(reviews == null || reviews.isEmpty());
    }
}
