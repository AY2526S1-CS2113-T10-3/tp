package seedu.duke;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ScoreCommandTest {

    private static final String MODULE_ID = "CS2113";
    private static final String QUERY_MODE = "-1";

    private UI ui;
    private ModuleList modules;
    private CourseRecord record;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws UniflowException {
        System.setOut(new PrintStream(outputStream));
        ui = new UI();
        modules = new ModuleList();
        record = new CourseRecord();

        Module m = new Module(MODULE_ID, "Software Engineering", "Monday", "10:00", "12:00", "lecture");
        modules.addModule(m);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
        Uniflow.getScoreManager().clearAll();
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
    void execute_singleComponent_works() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:100");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(1, scores.size());
        assertEquals(100, scores.get("exam"));
    }

    @Test
    void execute_zeroValue_accepted() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "bonus:0");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(0, scores.get("bonus"));
    }

    @Test
    void execute_replaceExistingBreakdown_works() throws Exception {
        ScoreCommand cmd1 = new ScoreCommand(MODULE_ID, "exam:50 quiz:10");
        cmd1.execute(ui, modules, record);

        ScoreCommand cmd2 = new ScoreCommand(MODULE_ID, "exam:60 project:40");
        cmd2.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(2, scores.size());
        assertEquals(60, scores.get("exam"));
        assertEquals(40, scores.get("project"));
        assertFalse(scores.containsKey("quiz"));
    }

    @Test
    void executeQueryMode_noBreakdown_showsMessage() throws Exception {
        ScoreCommand query = new ScoreCommand(MODULE_ID, QUERY_MODE);
        query.execute(ui, modules, record);

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("not found") ||
                output.toLowerCase().contains("no score"));
    }

    @Test
    void executeQueryMode_existingBreakdown_displaysCorrectly() throws Exception {
        ScoreCommand seed = new ScoreCommand(MODULE_ID, "exam:60 quiz:10");
        seed.execute(ui, modules, record);

        outputStream.reset();

        ScoreCommand query = new ScoreCommand(MODULE_ID, QUERY_MODE);
        query.execute(ui, modules, record);

        String output = outputStream.toString();
        assertTrue(output.contains("60"));
        assertTrue(output.contains("10"));
    }

    @Test
    void execute_moduleDoesNotExist_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand("CS9999", "exam:50").execute(ui, modules, record),
                "Should throw exception for non-existent module"
        );
    }

    @Test
    void execute_nullModuleId_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(null, "exam:50").execute(ui, modules, record),
                "Should throw exception for null module ID"
        );
    }

    @Test
    void execute_emptyModuleId_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand("", "exam:50").execute(ui, modules, record),
                "Should throw exception for empty module ID"
        );
    }

    @Test
    void execute_emptyBreakdown_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, "").execute(ui, modules, record),
                "Should throw exception for empty breakdown"
        );
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, "   ").execute(ui, modules, record),
                "Should throw exception for whitespace-only breakdown"
        );
    }

    @Test
    void execute_malformedPairNoColon_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, "exam50").execute(ui, modules, record),
                "Should throw exception for malformed pair without colon"
        );
    }

    @Test
    void execute_malformedPairMissingValue_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, "exam:").execute(ui, modules, record),
                "Should throw exception for missing value"
        );
    }

    @Test
    void execute_malformedPairMissingName_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, ":50").execute(ui, modules, record),
                "Should throw exception for missing component name"
        );
    }

    @Test
    void execute_nonNumericValue_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, "exam:fifty").execute(ui, modules, record),
                "Should throw exception for non-numeric value"
        );
    }

    @Test
    void execute_negativeValue_throws() {
        assertThrows(UniflowException.class, () ->
                        new ScoreCommand(MODULE_ID, "exam:-10").execute(ui, modules, record),
                "Should throw exception for negative value"
        );
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

    @Test
    void execute_commaAsSeparator_parsesCorrectly() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:50,project:30,quiz:20");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(3, scores.size());
        assertEquals(50, scores.get("exam"));
        assertEquals(30, scores.get("project"));
        assertEquals(20, scores.get("quiz"));
    }

    @Test
    void execute_mixedSeparators_parsesCorrectly() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:50, project:30   quiz:20");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(3, scores.size());
    }

    @Test
    void execute_longComponentNames_works() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "midterm_examination:40 final_project_submission:60");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(2, scores.size());
        assertEquals(40, scores.get("midterm_examination"));
        assertEquals(60, scores.get("final_project_submission"));
    }

    @Test
    void execute_specialCharactersInName_works() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "lab-work:20 peer-review:10");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(2, scores.size());
        assertEquals(20, scores.get("lab-work"));
        assertEquals(10, scores.get("peer-review"));
    }

    @Test
    void execute_largeValues_works() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:1000 project:5000");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(1000, scores.get("exam"));
        assertEquals(5000, scores.get("project"));
    }

    @Test
    void execute_caseInsensitiveModuleId_works() throws Exception {
        ScoreCommand cmd = new ScoreCommand("cs2113", "exam:50");
        cmd.execute(ui, modules, record);

        Module m = modules.getModuleByID(MODULE_ID);
        Map<String, Integer> scores = m.getScoreBreakdown();

        assertEquals(50, scores.get("exam"));
    }

    @Test
    void execute_persistence_savesAndLoadsCorrectly() throws Exception {
        ScoreCommand cmd = new ScoreCommand(MODULE_ID, "exam:50 project:30");
        cmd.execute(ui, modules, record);

        // Verify persisted data
        ScoreManager sm = Uniflow.getScoreManager();
        Map<String, Integer> loaded = sm.getBreakdown(MODULE_ID);

        assertEquals(2, loaded.size());
        assertEquals(50, loaded.get("exam"));
        assertEquals(30, loaded.get("project"));
    }
}
