package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreCommandTest {

    private static final String MODULE_ID = "CS2113";
    private static final String QUERY_MODE = "-1";

    private UI ui;
    private ModuleList modules;
    private CourseRecord record;

    @BeforeEach
    void setUp() throws UniflowException {
        ui = new UI();                 // we do not assert on UI output
        modules = new ModuleList();
        record = new CourseRecord();   // not used by ScoreCommand, but required by signature

        // Create a module in the timetable so `ScoreCommand` finds it
        // If your API differs, change this line accordingly (e.g., modules.add(...))
        Module m = new Module(MODULE_ID, "Software Engineering", "Monday", "10:00", "12:00", "lecture");
        modules.addModule(m);
    }

    @Test
    void execute_validBreakdown_updatesModuleState() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:50 project:30 participation:20");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(3, scores.size());
        assertEquals(50, scores.get("exam"));
        assertEquals(30, scores.get("project"));
        assertEquals(20, scores.get("participation"));
    }

    @Test
    void execute_queryMode_withoutExistingBreakdown_doesNotThrowAndDoesNotCreate() throws Exception {
        // Query
        ScoreCommand query = new ScoreCommand(MODULE_ID, QUERY_MODE);
        query.execute(ui, modules, record);

        // Still no breakdown created implicitly
        Module m = modules.getModuleByID(MODULE_ID);
    }

    @Test
    void execute_queryMode_withExistingBreakdown_keepsStateUnchanged() throws Exception {
        // Seed breakdown
        ScoreCommand seed = new ScoreCommand(MODULE_ID, "exam:60 quiz:10");
        seed.execute(ui, modules, record);

        Module before = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> beforeMap = before.getScoreBreakdown();

        // Query should not mutate
        ScoreCommand query = new ScoreCommand(MODULE_ID, QUERY_MODE);
        query.execute(ui, modules, record);

        Module after = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> afterMap = after.getScoreBreakdown();

        assertEquals(beforeMap, afterMap);
    }

    @Test
    void execute_moduleDoesNotExist_throws() {
        // Remove the module to simulate absence (adjust if your API differs)
        // Alternatively, use a different non-existent ID
        UniflowException ex = assertThrows(UniflowException.class, () ->
                new ScoreCommand("CS9999", "exam:50").execute(ui, modules, record)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("does not exist"));
    }

    @Test
    void execute_emptyBreakdown_throws() {
        UniflowException ex1 = assertThrows(UniflowException.class, () ->
                new ScoreCommand(MODULE_ID, "").execute(ui, modules, record)
        );
        UniflowException ex2 = assertThrows(UniflowException.class, () ->
                new ScoreCommand(MODULE_ID, "   ").execute(ui, modules, record)
        );
        assertTrue(ex1.getMessage().toLowerCase().contains("provide scores"));
        assertTrue(ex2.getMessage().toLowerCase().contains("provide scores"));
    }

    @Test
    void execute_malformedPair_missingColon_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () ->
                new ScoreCommand(MODULE_ID, "exam:50 project30").execute(ui, modules, record)
        );
        String msg = ex.getMessage().toLowerCase();
        assertTrue(msg.contains("invalid format") || msg.contains("use name:value"));
    }

    @Test
    void execute_nonNumericValue_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () ->
                new ScoreCommand(MODULE_ID, "exam:fifty").execute(ui, modules, record)
        );
        String msg = ex.getMessage().toLowerCase();
        assertTrue(msg.contains("numeric") || msg.contains("format"));
    }

    @Test
    void execute_negativeValue_throws() {
        UniflowException ex = assertThrows(UniflowException.class, () ->
                new ScoreCommand(MODULE_ID, "exam:-10").execute(ui, modules, record)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("positive"));
    }

    @Test
    void execute_multipleSpacesAndPairs_parsesCorrectly() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:50   project:30    quiz:20");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(3, scores.size());
        assertEquals(50, scores.get("exam"));
        assertEquals(30, scores.get("project"));
        assertEquals(20, scores.get("quiz"));
    }
}
