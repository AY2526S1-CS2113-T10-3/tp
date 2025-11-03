package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ScoreManagerTest {

    private static final String TEST_FILE_PATH = "data/scores.txt";
    private ScoreManager manager;

    @BeforeEach
    void setUp() {
        deleteTestFile();
        manager = new ScoreManager();
    }

    @AfterEach
    void tearDown() {
        manager.clearAll();
        deleteTestFile();
    }

    private void deleteTestFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveBreakdown_savesSuccessfully() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);
        breakdown.put("project", 30);

        manager.saveBreakdown("CS2113", breakdown);

        assertTrue(manager.hasBreakdown("CS2113"));
    }

    @Test
    void getBreakdown_nonExistent_returnsEmpty() {
        Map<String, Integer> breakdown = manager.getBreakdown("CS9999");
        assertTrue(breakdown.isEmpty());
    }

    @Test
    void saveAndGetBreakdown_preservesData() {
        Map<String, Integer> original = new HashMap<>();
        original.put("exam", 50);
        original.put("project", 30);
        original.put("quiz", 20);

        manager.saveBreakdown("CS2113", original);
        Map<String, Integer> retrieved = manager.getBreakdown("CS2113");

        assertEquals(3, retrieved.size());
        assertEquals(50, retrieved.get("exam"));
        assertEquals(30, retrieved.get("project"));
        assertEquals(20, retrieved.get("quiz"));
    }

    @Test
    void hasBreakdown_existingBreakdown_returnsTrue() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);

        manager.saveBreakdown("CS2113", breakdown);

        assertTrue(manager.hasBreakdown("CS2113"));
    }

    @Test
    void hasBreakdown_nonExistent_returnsFalse() {
        assertFalse(manager.hasBreakdown("CS9999"));
    }

    @Test
    void removeBreakdown_removesSuccessfully() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);

        manager.saveBreakdown("CS2113", breakdown);
        manager.removeBreakdown("CS2113");

        assertFalse(manager.hasBreakdown("CS2113"));
    }

    @Test
    void removeBreakdown_nonExistent_doesNotThrow() {
        assertDoesNotThrow(() -> manager.removeBreakdown("CS9999"));
    }

    @Test
    void removeBreakdown_null_doesNotThrow() {
        assertDoesNotThrow(() -> manager.removeBreakdown(null));
    }

    @Test
    void clearAll_removesAllBreakdowns() {
        Map<String, Integer> breakdown1 = new HashMap<>();
        breakdown1.put("exam", 50);

        Map<String, Integer> breakdown2 = new HashMap<>();
        breakdown2.put("project", 40);

        manager.saveBreakdown("CS2113", breakdown1);
        manager.saveBreakdown("CS2040", breakdown2);

        manager.clearAll();

        assertFalse(manager.hasBreakdown("CS2113"));
        assertFalse(manager.hasBreakdown("CS2040"));
    }

    @Test
    void pruneAgainst_removesNonExistent() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS2113", "SE", "Monday", "10:00", "12:00", "lecture"));

        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);

        manager.saveBreakdown("CS2113", breakdown);
        manager.saveBreakdown("CS9999", breakdown);

        manager.pruneAgainst(modules);

        assertTrue(manager.hasBreakdown("CS2113"));
        assertFalse(manager.hasBreakdown("CS9999"));
    }

    @Test
    void pruneAgainst_nullModules_doesNothing() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);

        manager.saveBreakdown("CS2113", breakdown);

        assertDoesNotThrow(() -> manager.pruneAgainst(null));

        assertTrue(manager.hasBreakdown("CS2113"));
    }

    @Test
    void pruneAgainst_emptyModules_removesAll() {
        ModuleList modules = new ModuleList();

        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);

        manager.saveBreakdown("CS2113", breakdown);

        manager.pruneAgainst(modules);

        assertFalse(manager.hasBreakdown("CS2113"));
    }

    @Test
    void saveBreakdown_caseInsensitive() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 50);

        manager.saveBreakdown("cs2113", breakdown);

        assertTrue(manager.hasBreakdown("CS2113"));
        assertTrue(manager.hasBreakdown("cs2113"));
    }

    @Test
    void persistence_savesAndLoads() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("exam", 60);
        breakdown.put("project", 40);

        manager.saveBreakdown("CS2113", breakdown);

        ScoreManager newManager = new ScoreManager();
        Map<String, Integer> loaded = newManager.getBreakdown("CS2113");

        assertEquals(2, loaded.size());
        assertEquals(60, loaded.get("exam"));
        assertEquals(40, loaded.get("project"));

        newManager.clearAll();
    }
}
