package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the ScoreCommand class.
 */
public class ScoreCommandTest {

    private static final String COURSE_ID = "CS2113";
    private static final String QUERY_MODE = "-1";

    /**
     * Simple UI spy to capture messages displayed by the command.
     */
    static class SpyUI extends UI {
        String lastMessage;

        @Override
        public void showMessage(String message) {
            lastMessage = message;
        }
    }

    private SpyUI ui;
    private ModuleList modules;
    private CourseRecord record;

    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    void setUp() {
        ui = new SpyUI();
        modules = new ModuleList();
        record = new CourseRecord();
        record.addCourse(new Course(COURSE_ID, 4, "A", true));
    }

    @Test
    void executeValidBreakdown_savesCorrectly() throws Exception {
        ScoreCommand command = new ScoreCommand(COURSE_ID, "exam:50 project:30 participation:20");
        command.execute(ui, modules, record);

        assertNotNull(ui.lastMessage);
        assertTrue(ui.lastMessage.contains(COURSE_ID));

        Course course = record.getCourse(COURSE_ID);
        Map<String, Integer> scores = course.getScoreBreakdown();
        assertEquals(3, scores.size());
        assertEquals(50, scores.get("exam"));
        assertEquals(30, scores.get("project"));
        assertEquals(20, scores.get("participation"));
    }

    @Test
    void executeQueryModeNoBreakdownShowsNotFound() throws Exception {
        ScoreCommand command = new ScoreCommand(COURSE_ID, QUERY_MODE);
        command.execute(ui, modules, record);

        assertNotNull(ui.lastMessage);
        assertTrue(ui.lastMessage.toLowerCase().contains("not found"));
    }

    @Test
    void executeQueryModeWithExistingBreakdownShowsScores() throws Exception {
        ScoreCommand addCommand = new ScoreCommand(COURSE_ID, "exam:60 quiz:10");
        addCommand.execute(ui, modules, record);

        ScoreCommand queryCommand = new ScoreCommand(COURSE_ID, QUERY_MODE);
        queryCommand.execute(ui, modules, record);

        assertNotNull(ui.lastMessage);
        assertTrue(ui.lastMessage.startsWith("Score breakdown for " + COURSE_ID));
        assertTrue(ui.lastMessage.contains("exam"));
        assertTrue(ui.lastMessage.contains("60"));
        assertTrue(ui.lastMessage.contains("quiz"));
        assertTrue(ui.lastMessage.contains("10"));
    }

    @Test
    void executeModuleDoesNotExist_throws() {
        UniflowException exception = assertThrows(UniflowException.class, () -> {
            new ScoreCommand("CS9999", "exam:50").execute(ui, modules, record);
        });
        assertTrue(exception.getMessage().toLowerCase().contains("does not exist"));
    }

    @Test
    void executeEmptyBreakdown_throws() {
        UniflowException ex1 = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE_ID, "").execute(ui, modules, record);
        });
        UniflowException ex2 = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE_ID, "   ").execute(ui, modules, record);
        });
        assertTrue(ex1.getMessage().toLowerCase().contains("provide scores"));
        assertTrue(ex2.getMessage().toLowerCase().contains("provide scores"));
    }

    @Test
    void executeMalformedPairMissingColon_throws() {
        UniflowException exception = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE_ID, "exam:50 project30").execute(ui, modules, record);
        });
        String message = exception.getMessage().toLowerCase();
        assertTrue(message.contains("invalid format") || message.contains("please provide"));
    }

    @Test
    void executeNonNumericValue_throws() {
        UniflowException exception = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE_ID, "exam:fifty").execute(ui, modules, record);
        });
        String msg = exception.getMessage().toLowerCase();
        // accept either wording to avoid brittle tests
        assertTrue(msg.contains("format") || msg.contains("numeric"),
                "Should guide user toward correct name:value format");
    }

    @Test
    void executeNegativeValue_throws() {
        UniflowException exception = assertThrows(UniflowException.class, () -> {
            new ScoreCommand(COURSE_ID, "exam:-10").execute(ui, modules, record);
        });
        assertTrue(exception.getMessage().toLowerCase().contains("positive"));
    }

    @Test
    void executeMultipleSpacesAndPairsParsesCorrectly() throws Exception {
        ScoreCommand command = new ScoreCommand(COURSE_ID, "exam:50   project:30    quiz:20");
        command.execute(ui, modules, record);

        Course course = record.getCourse(COURSE_ID);
        Map<String, Integer> scores = course.getScoreBreakdown();

        assertEquals(3, scores.size());
        assertEquals(50, scores.get("exam"));
        assertEquals(30, scores.get("project"));
        assertEquals(20, scores.get("quiz"));
    }
}
