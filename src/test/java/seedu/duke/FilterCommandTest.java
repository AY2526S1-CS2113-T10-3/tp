package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterCommandTest {

    private ModuleList moduleList;
    private UI ui;
    private CourseRecord courseRecord;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws UniflowException {
        moduleList = new ModuleList();
        ui = new UI();
        courseRecord = new CourseRecord();

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Add sample modules using the correct constructor
        moduleList.addModule(new Module("CS1010", "Programming Methodology", "Monday", "10:00", "12:00", "lecture"));
        moduleList.addModule(new Module("CS2040", "Data Structures and Algorithms", "Tuesday", "14:00", "16:00", "lecture"));
        moduleList.addModule(new Module("CS2113", "Software Engineering & Object-Oriented Programming", "Wednesday", "09:00", "11:00", "tutorial"));
        moduleList.addModule(new Module("CS2030", "Programming Paradigms", "Thursday", "15:00", "17:00", "lecture"));
        moduleList.addModule(new Module("MA1521", "Calculus for Computing", "Friday", "10:00", "12:00", "lecture"));
        moduleList.addModule(new Module("MA2001", "Linear Algebra I", "Monday", "14:00", "16:00", "tutorial"));
        moduleList.addModule(new Module("ST2334", "Probability and Statistics", "Tuesday", "10:00", "12:00", "lecture"));
    }

    @AfterEach
    void tearDown() {
        // Restore System.out
        System.setOut(originalOut);
        outputStream.reset();
    }

    @Test
    void testFilterByIdPrefix() throws UniflowException {
        FilterCommand command = new FilterCommand("id", "CS");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS1010"));
        assertTrue(output.contains("CS2040"));
        assertTrue(output.contains("CS2113"));
        assertTrue(output.contains("CS2030"));
        assertFalse(output.contains("MA1521"));
        assertFalse(output.contains("ST2334"));
    }

    @Test
    void testFilterByName() throws UniflowException {
        FilterCommand command = new FilterCommand("name", "programming");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS1010") || output.contains("Programming Methodology"));
        assertTrue(output.contains("CS2030") || output.contains("Programming Paradigms"));
        assertFalse(output.contains("MA1521"));
    }

    @Test
    void testFilterByNameCaseInsensitive() throws UniflowException {
        FilterCommand command = new FilterCommand("name", "PROGRAMMING");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS1010") || output.contains("Programming"));
        assertTrue(output.contains("CS2030") || output.contains("Paradigms"));
    }

    @Test
    void testFilterByDay() throws UniflowException {
        FilterCommand command = new FilterCommand("day", "Monday");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS1010"));
        assertTrue(output.contains("MA2001"));
    }

    @Test
    void testFilterByType() throws UniflowException {
        FilterCommand command = new FilterCommand("type", "tutorial");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS2113") || output.contains("tutorial"));
        assertTrue(output.contains("MA2001") || output.contains("tutorial"));
    }

    @Test
    void testFilterHasTutorial() throws UniflowException {
        FilterCommand command = new FilterCommand("hastutorial", null);

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS2113") || output.contains("MA2001"));
    }

    @Test
    void testFilterNoTutorial() throws UniflowException {
        FilterCommand command = new FilterCommand("notutorial", null);

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS1010"));
        assertTrue(output.contains("CS2040"));
        assertTrue(output.contains("CS2030"));
    }

    @Test
    void testFilterExactId() throws UniflowException {
        FilterCommand command = new FilterCommand("id", "CS2113");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS2113"));
        assertTrue(output.contains("Software Engineering"));
    }

    @Test
    void testFilterPartialId() throws UniflowException {
        FilterCommand command = new FilterCommand("id", "21");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("MA1521"));
        assertTrue(output.contains("CS2113"));
    }

    @Test
    void testFilterNoMatches() throws UniflowException {
        FilterCommand command = new FilterCommand("id", "XYZ");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("no") ||
                output.toLowerCase().contains("matching"));
    }

    @Test
    void testFilterWithEmptyList() throws UniflowException {
        ModuleList emptyList = new ModuleList();
        FilterCommand command = new FilterCommand("id", "CS");

        assertDoesNotThrow(() -> command.execute(ui, emptyList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("no") ||
                output.toLowerCase().contains("empty"));
    }

    @Test
    void testFilterMultipleMatches() throws UniflowException {
        FilterCommand command = new FilterCommand("id", "CS");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        // Should contain multiple CS courses
        int csCount = output.split("CS").length - 1;
        assertTrue(csCount >= 4, "Should find at least 4 CS courses");
    }

    @Test
    void testFilterNamePartialMatch() throws UniflowException {
        FilterCommand command = new FilterCommand("name", "linear");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("MA2001"));
        assertTrue(output.contains("Linear Algebra"));
    }

    @Test
    void testFilterIdWithLowerCase() throws UniflowException {
        FilterCommand command = new FilterCommand("id", "cs");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS"));
    }

    @Test
    void testFilterNameWithSpecialCharacters() throws UniflowException {
        FilterCommand command = new FilterCommand("name", "object-oriented");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS2113") || output.contains("Object-Oriented"));
    }

    @Test
    void testFilterEmptyValue() {
        assertThrows(UniflowException.class, () -> {
            FilterCommand command = new FilterCommand("id", "");
            command.execute(ui, moduleList, courseRecord);
        });
    }

    @Test
    void testFilterNullValue() {
        assertThrows(UniflowException.class, () -> {
            FilterCommand command = new FilterCommand("id", null);
            command.execute(ui, moduleList, courseRecord);
        });
    }

    @Test
    void testFilterUnknownType() {
        assertThrows(UniflowException.class, () -> {
            FilterCommand command = new FilterCommand("unknown", "value");
            command.execute(ui, moduleList, courseRecord);
        });
    }

    @Test
    void testFilterDayCaseInsensitive() throws UniflowException {
        FilterCommand command = new FilterCommand("day", "monday");

        assertDoesNotThrow(() -> command.execute(ui, moduleList, courseRecord));

        String output = outputStream.toString();
        assertTrue(output.contains("CS1010") || output.contains("Monday"));
    }
}
