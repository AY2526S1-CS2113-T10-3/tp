package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterCommandTest {

    private static final Logger LOGGER = Logger.getLogger(FilterCommandTest.class.getName());

    private ModuleList moduleList;
    private UI ui;
    private CourseRecord courseRecord;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws UniflowException {
        // Setup logging
        setupLogger();
        LOGGER.log(Level.INFO, "Setting up FilterCommandTest");

        moduleList = new ModuleList();
        ui = new UI();
        courseRecord = new CourseRecord();

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Add sample modules using the correct constructor
        addTestModule("CS1010", "Programming Methodology", "Monday", "10:00", "12:00", "lecture");
        addTestModule("CS2040", "Data Structures and Algorithms", "Tuesday", "14:00", "16:00", "lecture");
        addTestModule("CS2113", "Software Engineering & Object-Oriented Programming",
                "Wednesday", "09:00", "11:00", "tutorial");
        addTestModule("CS2030", "Programming Paradigms", "Thursday", "15:00", "17:00", "lecture");
        addTestModule("MA1521", "Calculus for Computing", "Friday", "10:00", "12:00", "lecture");
        addTestModule("MA2001", "Linear Algebra I", "Monday", "14:00", "16:00", "tutorial");
        addTestModule("ST2334", "Probability and Statistics", "Tuesday", "10:00", "12:00", "lecture");

        LOGGER.log(Level.INFO, "Setup complete. Added {0} modules", moduleList.getSize());
        assert moduleList.getSize() == 7 : "Expected 7 modules after setup";
    }

    private void setupLogger() {
        LOGGER.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);
    }

    private void addTestModule(String id, String name, String day, String startTime,
                               String endTime, String sessionType) throws UniflowException {
        Module module = new Module(id, name, day, startTime, endTime, sessionType);
        assert module != null : "Module creation failed";
        moduleList.addModule(module);
        LOGGER.log(Level.FINE, "Added module: {0}", id);
    }

    @AfterEach
    void tearDown() {
        LOGGER.log(Level.INFO, "Tearing down FilterCommandTest");
        // Restore System.out
        System.setOut(originalOut);
        outputStream.reset();

        // Verify cleanup
        assertNotNull(moduleList, "ModuleList should not be null after teardown");
    }

    @Test
    void testFilterByIdPrefix() {
        LOGGER.log(Level.INFO, "Testing filter by ID prefix 'CS'");
        FilterCommand command = new FilterCommand("id", "CS");
        assertNotNull(command, "FilterCommand should not be null");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS1010"), "Output should contain CS1010");
        assertTrue(output.contains("CS2040"), "Output should contain CS2040");
        assertTrue(output.contains("CS2113"), "Output should contain CS2113");
        assertTrue(output.contains("CS2030"), "Output should contain CS2030");
        assertFalse(output.contains("MA1521"), "Output should not contain MA1521");
        assertFalse(output.contains("ST2334"), "Output should not contain ST2334");

        assert !output.isEmpty() : "Output should not be empty";
        LOGGER.log(Level.INFO, "Filter by ID prefix test passed");
    }

    @Test
    void testFilterByName() {
        LOGGER.log(Level.INFO, "Testing filter by name 'programming'");
        FilterCommand command = new FilterCommand("name", "programming");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS1010") || output.contains("Programming Methodology"),
                "Output should contain CS1010 or Programming Methodology");
        assertTrue(output.contains("CS2030") || output.contains("Programming Paradigms"),
                "Output should contain CS2030 or Programming Paradigms");
        assertFalse(output.contains("MA1521"), "Output should not contain MA1521");

        LOGGER.log(Level.INFO, "Filter by name test passed");
    }

    @Test
    void testFilterByNameCaseInsensitive() {
        LOGGER.log(Level.INFO, "Testing filter by name case insensitive 'PROGRAMMING'");
        FilterCommand command = new FilterCommand("name", "PROGRAMMING");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS1010") || output.contains("Programming"),
                "Output should contain CS1010 or Programming");
        assertTrue(output.contains("CS2030") || output.contains("Paradigms"),
                "Output should contain CS2030 or Paradigms");

        assert output.toLowerCase().contains("programming") : "Output should contain 'programming' (case-insensitive)";
        LOGGER.log(Level.INFO, "Case insensitive filter test passed");
    }

    @Test
    void testFilterByDay() {
        LOGGER.log(Level.INFO, "Testing filter by day 'Monday'");
        FilterCommand command = new FilterCommand("day", "Monday");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS1010"), "Output should contain CS1010");
        assertTrue(output.contains("MA2001"), "Output should contain MA2001");
        assertFalse(output.contains("CS2040"), "Output should not contain CS2040 (Tuesday)");

        LOGGER.log(Level.INFO, "Filter by day test passed");
    }

    @Test
    void testFilterByType() {
        LOGGER.log(Level.INFO, "Testing filter by type 'tutorial'");
        FilterCommand command = new FilterCommand("type", "tutorial");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS2113") || output.contains("tutorial"),
                "Output should contain CS2113 or 'tutorial'");
        assertTrue(output.contains("MA2001") || output.contains("tutorial"),
                "Output should contain MA2001 or 'tutorial'");
        assertFalse(output.contains("CS1010"), "Output should not contain lecture modules like CS1010");

        LOGGER.log(Level.INFO, "Filter by type test passed");
    }

    @Test
    void testFilterHasTutorial() {
        LOGGER.log(Level.INFO, "Testing filter has tutorial");
        FilterCommand command = new FilterCommand("hastutorial", null);
        assertNotNull(command, "FilterCommand should not be null");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS2113") || output.contains("MA2001"),
                "Output should contain tutorial modules");

        int tutorialCount = (output.contains("CS2113") ? 1 : 0) + (output.contains("MA2001") ? 1 : 0);
        assert tutorialCount >= 2 : "Should find at least 2 tutorial modules";

        LOGGER.log(Level.INFO, "Filter has tutorial test passed");
    }

    @Test
    void testFilterNoTutorial() {
        LOGGER.log(Level.INFO, "Testing filter no tutorial");
        FilterCommand command = new FilterCommand("notutorial", null);

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS1010"), "Output should contain CS1010");
        assertTrue(output.contains("CS2040"), "Output should contain CS2040");
        assertTrue(output.contains("CS2030"), "Output should contain CS2030");
        assertFalse(output.contains("CS2113") && output.contains("tutorial"),
                "Output should not contain tutorial modules");

        LOGGER.log(Level.INFO, "Filter no tutorial test passed");
    }

    @Test
    void testFilterExactId() {
        LOGGER.log(Level.INFO, "Testing filter by exact ID 'CS2113'");
        FilterCommand command = new FilterCommand("id", "CS2113");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS2113"), "Output should contain CS2113");
        assertTrue(output.contains("Software Engineering"), "Output should contain module name");

        assert output.contains("CS2113") : "CS2113 should appear in output";
        LOGGER.log(Level.INFO, "Filter exact ID test passed");
    }

    @Test
    void testFilterPartialId() {
        LOGGER.log(Level.INFO, "Testing filter by partial ID '21'");
        FilterCommand command = new FilterCommand("id", "21");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("MA1521"), "Output should contain MA1521");
        assertTrue(output.contains("CS2113"), "Output should contain CS2113");

        int matchCount = 0;
        if (output.contains("MA1521")) {
            matchCount++;
        }
        if (output.contains("CS2113")) {
            matchCount++;
        }
        assertEquals(2, matchCount, "Should find exactly 2 modules with '21' in ID");

        LOGGER.log(Level.INFO, "Filter partial ID test passed");
    }

    @Test
    void testFilterNoMatches() {
        LOGGER.log(Level.INFO, "Testing filter with no matches 'XYZ'");
        FilterCommand command = new FilterCommand("id", "XYZ");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.toLowerCase().contains("no") || output.toLowerCase().contains("matching"),
                "Output should indicate no matches found");

        assert !output.contains("CS") : "Output should not contain any CS modules";
        LOGGER.log(Level.INFO, "Filter no matches test passed");
    }

    @Test
    void testFilterWithEmptyList() {
        LOGGER.log(Level.INFO, "Testing filter with empty module list");
        ModuleList emptyList = new ModuleList();
        assertNotNull(emptyList, "Empty list should not be null");
        assertEquals(0, emptyList.getSize(), "Empty list should have size 0");

        FilterCommand command = new FilterCommand("id", "CS");

        assertDoesNotThrow(() -> command.execute(ui, emptyList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.toLowerCase().contains("no") || output.toLowerCase().contains("empty"),
                "Output should indicate no modules or empty list");

        LOGGER.log(Level.INFO, "Filter empty list test passed");
    }

    @Test
    void testFilterMultipleMatches() {
        LOGGER.log(Level.INFO, "Testing filter with multiple matches 'CS'");
        FilterCommand command = new FilterCommand("id", "CS");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        // Should contain multiple CS courses
        int csCount = output.split("CS").length - 1;
        assertTrue(csCount >= 4, "Should find at least 4 CS courses, found: " + csCount);

        assert csCount > 0 : "Should have at least one CS module";
        LOGGER.log(Level.INFO, "Found {0} CS module references", csCount);
        LOGGER.log(Level.INFO, "Filter multiple matches test passed");
    }

    @Test
    void testFilterNamePartialMatch() {
        LOGGER.log(Level.INFO, "Testing filter by partial name 'linear'");
        FilterCommand command = new FilterCommand("name", "linear");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("MA2001"), "Output should contain MA2001");
        assertTrue(output.contains("Linear Algebra"), "Output should contain Linear Algebra");

        LOGGER.log(Level.INFO, "Filter name partial match test passed");
    }

    @Test
    void testFilterIdWithLowerCase() {
        LOGGER.log(Level.INFO, "Testing filter by ID with lowercase 'cs'");
        FilterCommand command = new FilterCommand("id", "cs");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS"), "Output should contain CS modules (case-insensitive match)");

        assert output.toUpperCase().contains("CS") : "Should match CS regardless of case";
        LOGGER.log(Level.INFO, "Filter ID lowercase test passed");
    }

    @Test
    void testFilterNameWithSpecialCharacters() {
        LOGGER.log(Level.INFO, "Testing filter by name with special characters 'object-oriented'");
        FilterCommand command = new FilterCommand("name", "object-oriented");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS2113") || output.contains("Object-Oriented"),
                "Output should contain CS2113 or Object-Oriented");

        LOGGER.log(Level.INFO, "Filter name with special characters test passed");
    }

    @Test
    void testFilterEmptyValue() {
        LOGGER.log(Level.INFO, "Testing filter with empty value");
        assertThrows(UniflowException.class, () -> {
            FilterCommand command = new FilterCommand("id", "");
            command.execute(ui, moduleList, courseRecord);
        }, "Empty filter value should throw exception");

        LOGGER.log(Level.INFO, "Filter empty value test passed");
    }

    @Test
    void testFilterNullValue() {
        LOGGER.log(Level.INFO, "Testing filter with null value");
        assertThrows(UniflowException.class, () -> {
            FilterCommand command = new FilterCommand("id", null);
            command.execute(ui, moduleList, courseRecord);
        }, "Null filter value should throw exception");

        LOGGER.log(Level.INFO, "Filter null value test passed");
    }

    @Test
    void testFilterUnknownType() {
        LOGGER.log(Level.INFO, "Testing filter with unknown type");
        assertThrows(UniflowException.class, () -> {
            FilterCommand command = new FilterCommand("unknown", "value");
            command.execute(ui, moduleList, courseRecord);
        }, "Unknown filter type should throw exception");

        LOGGER.log(Level.INFO, "Filter unknown type test passed");
    }

    @Test
    void testFilterDayCaseInsensitive() {
        LOGGER.log(Level.INFO, "Testing filter by day case insensitive 'monday'");
        FilterCommand command = new FilterCommand("day", "monday");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        LOGGER.log(Level.FINE, "Filter output: {0}", output);

        assertTrue(output.contains("CS1010") || output.contains("Monday"),
                "Output should contain Monday modules (case-insensitive)");

        assert output.toLowerCase().contains("monday") || output.contains("CS1010") :
                "Should find Monday modules regardless of case";
        LOGGER.log(Level.INFO, "Filter day case insensitive test passed");
    }
}
