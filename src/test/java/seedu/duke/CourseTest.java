package seedu.duke;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    public void constructor_validInputs_createsCourse() {
        Course course = new Course("CS2113", 4, "A", true);

        assertEquals("CS2113", course.getCode());
        assertEquals(4, course.getCredits());
        assertEquals("A", course.getGrade());
        assertTrue(course.isMajor());
    }

    @Test
    public void constructor_notMajor_createsCourse() {
        Course course = new Course("CS2040", 4, "B+", false);
        assertFalse(course.isMajor());
    }

    @Test
    public void toString_majorCourse_containsMajorLabel() {
        Course course = new Course("CS2113", 4, "A", true);
        String result = course.toString();

        assertTrue(result.contains("CS2113"));
        assertTrue(result.contains("4 credits"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("Major course"));
    }

    @Test
    public void toString_nonMajorCourse_noMajorLabel() {
        Course course = new Course("CS2040", 4, "B", false);
        String result = course.toString();

        assertFalse(result.contains("Major"));
    }

    @Test
    public void toStorage_majorCourse_correctFormat() {
        Course course = new Course("CS2113", 4, "A-", true);
        String storage = course.toStorage();

        assertEquals("CS2113 | 4 | A- | 1", storage);
    }

    @Test
    public void toStorage_nonMajorCourse_correctFormat() {
        Course course = new Course("CS2040", 4, "B+", false);
        String storage = course.toStorage();

        assertEquals("CS2040 | 4 | B+ | 0", storage);
    }

    @Test
    public void setScoreBreakdown_validMap_storesCorrectly() {
        Course course = new Course("CS2113", 4, "A", false);
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);
        breakdown.put("project", 30);

        course.setScoreBreakdown(breakdown);

        Map<String, Integer> retrieved = course.getScoreBreakdown();
        assertEquals(2, retrieved.size());
        assertEquals(50, retrieved.get("exam"));
        assertEquals(30, retrieved.get("project"));
    }

    @Test
    public void setScoreBreakdown_nullMap_clearsBreakdown() {
        Course course = new Course("CS2113", 4, "A", false);
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);
        course.setScoreBreakdown(breakdown);

        course.setScoreBreakdown(null);

        assertTrue(course.getScoreBreakdown().isEmpty());
        assertFalse(course.hasBreakdown());
    }

    @Test
    public void hasBreakdown_emptyBreakdown_returnsFalse() {
        Course course = new Course("CS2113", 4, "A", false);
        assertFalse(course.hasBreakdown());
    }

    @Test
    public void hasBreakdown_withBreakdown_returnsTrue() {
        Course course = new Course("CS2113", 4, "A", false);
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);
        course.setScoreBreakdown(breakdown);

        assertTrue(course.hasBreakdown());
    }

    @Test
    public void getScoreBreakdown_returnsIndependentMap() {
        Course course = new Course("CS2113", 4, "A", false);
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);
        course.setScoreBreakdown(breakdown);

        Map<String, Integer> retrieved = course.getScoreBreakdown();
        retrieved.put("quiz", 20);

        // Original breakdown should not be affected
        assertEquals(1, course.getScoreBreakdown().size());
    }
}
