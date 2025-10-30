package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ScoreCommand behavior: add & query modes, validation, and errors.
 */
public class ScoreCommandTest {

    /** Simple UI spy to capture last shown message. */
    static class SpyUI extends UI {
        String last;
        @Override
        public void showMessage(String message) {
            last = message;
        }
    }

    private SpyUI ui;
    private ModuleList modules;         // Not used by ScoreCommand, but required by signature
    private CourseRecord record;
    private static final String COURSE = "CS2113";
    private static final String QUERY_SENTINEL = "-1"; // must match ScoreCommand.SCORE_QUERY_MODE

    @BeforeEach
    void setUp() {
        ui = new SpyUI();
        modules = new ModuleList();
        record = new CourseRecord();
        record.addCourse(new Course(COURSE, 4, "A", true));
    }

    @Test
    void execute_addValidBreakdown_savesToCourseAndShowsMessage() throws Exception {
        ScoreCommand cmd = new ScoreCommand(COURSE, "exam:50 project:30 participation:20");
        cmd.execute(ui, modules, record);

        assertNotNull(ui.last, "UI should show a confirmation message");
        assertTrue(ui.last.contains(COURSE), "Message should mention course code");

        Course c = record.getCourse(COURSE);
        Map<String, Integer> map = c.getScoreBreakdown();
        assertEquals(3, map.size());
        assertEquals(50, map.get("exam"));
        assertEquals(30, map.get("project"));
        assertEquals(20, map.get("participation"));
    }

    @Test
    void execute_queryMode_noBreakdown_showsNotFound() throws Exception {
        ScoreCommand query = new ScoreCommand(COURSE, QUERY_SENTINEL);
        query.execute(ui, modules, record);

        assertNotNull(ui.last);
        assertTrue(ui.last.toLowerCase().contains("not found"),
                "Should inform user when no breakdown exists");
    }

    @Test
    void execute_queryMode_withExistingBreakdown_showsScores() throws Exception {
        // Add some scores
        new ScoreCommand(COURSE, "exam:60 quiz:10").execute(ui, modules, record);

        // Query them
        new ScoreCommand(COURSE, QUERY_SENTINEL).execute(ui, modules, record);

        assertNotNull(ui.last);
        assertTrue(ui.last.startsWith("Score breakdown for " + COURSE),
                "Should print a header with course code");
        assertTrue(ui.last.contains("exam") && ui.last.contains("60"));
        assertTrue(ui.last.contains("quiz") && ui.last.contains("10"));
    }

    @Test
    void execute_moduleDoesNotExist_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () -> {
            new ScoreCommand("CS9999", "exam:50").execute(ui, modules, record);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("does not exist"));
    }

    @Test
    void execute_emptyBreakdown_throws() {
        UniflowException ex1 = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE, "").execute(ui, modules, record);
        });
        UniflowException ex2 = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE, "   ").execute(ui, modules, record);
        });
        assertTrue(ex1.getMessage().toLowerCase().contains("provide scores"));
        assertTrue(ex2.getMessage().toLowerCase().contains("provide scores"));
    }

    @Test
    void execute_malformedPair_missingColon_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () -> {
            // One good pair, one bad pair (no colon)
            new ScoreCommand(COURSE, "exam:50 project30").execute(ui, modules, record);
        });
        // Depending on your exact message; accept either message variant:
        assertTrue(
                ex.getMessage().toLowerCase().contains("invalid format")
                        || ex.getMessage().toLowerCase().contains("please provide breakdown"),
                "Should complain about malformed name:value pairs"
        );
    }

    @Test
    void execute_nonNumericValue_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE, "exam:fifty").execute(ui, modules, record);
        });
        String msg = ex.getMessage().toLowerCase();
        assertTrue(msg.contains("format") || msg.contains("numeric"),
                "Should guide user toward correct name:value format");
    }

    @Test
    void execute_negativeValue_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE, "exam:-10").execute(ui, modules, record);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("positive"),
                "Should reject negative component scores");
    }

    @Test
    void execute_multipleSpacesAndPairs_parsesCorrectly() throws Exception {
        // Robustness to extra whitespace
        new ScoreCommand(COURSE, "exam:50   project:30    quiz:20").execute(ui, modules, record);
        Course c = record.getCourse(COURSE);
        Map<String, Integer> map = c.getScoreBreakdown();
        assertEquals(3, map.size());
        assertEquals(50, map.get("exam"));
        assertEquals(30, map.get("project"));
        assertEquals(20, map.get("quiz"));
    }
}
