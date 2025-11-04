package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UITest {

    private UI ui;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        ui = new UI();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
        ui.close();
    }

    @Test
    void showWelcome_displaysWelcomeMessage() {
        ui.showWelcome();
        String output = outputStream.toString();

        assertTrue(output.contains("Uniflow"));
        assertTrue(output.contains("What can I do for you?"));
    }

    @Test
    void showGoodbye_displaysGoodbyeMessage() {
        ui.showGoodbye();
        String output = outputStream.toString();

        assertTrue(output.contains("Bye"));
    }

    @Test
    void showMessage_displaysMessage() {
        ui.showMessage("Test message");
        String output = outputStream.toString();

        assertTrue(output.contains("Test message"));
    }

    @Test
    void showError_displaysError() {
        ui.showError("Test error");
        String output = outputStream.toString();

        assertTrue(output.contains("Test error"));
    }

    @Test
    void showLine_displaysDivider() {
        ui.showLine();
        String output = outputStream.toString();

        assertTrue(output.contains("_"));
    }

    @Test
    void showLoadingError_displaysError() {
        ui.showLoadingError();
        String output = outputStream.toString();

        assertTrue(output.toLowerCase().contains("error"));
        assertTrue(output.toLowerCase().contains("loading"));
    }

    @Test
    void showGradeLoadingError_displaysError() {
        ui.showGradeLoadingError();
        String output = outputStream.toString();

        assertTrue(output.toLowerCase().contains("error"));
        assertTrue(output.toLowerCase().contains("grade"));
    }

    @Test
    void showModuleList_emptyList_displaysEmptyMessage() {
        ModuleList emptyList = new ModuleList();
        ui.showModuleList(emptyList);

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("empty"));
    }

    @Test
    void showModuleList_withModules_displaysModules() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS2113", "SE", "Monday", "10:00", "12:00", "lecture"));
        modules.addModule(new Module("CS2040", "DSA", "Tuesday", "14:00", "16:00", "lecture"));

        ui.showModuleList(modules);

        String output = outputStream.toString();
        assertTrue(output.contains("CS2113"));
        assertTrue(output.contains("CS2040"));
    }

    @Test
    void showFilteredModules_emptyList_displaysNoMatch() {
        ModuleList emptyList = new ModuleList();
        ui.showFilteredModules(emptyList, "test filter");

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("no modules"));
    }

    @Test
    void showFilteredModules_withModules_displaysCount() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS2113", "SE", "Monday", "10:00", "12:00", "lecture"));

        ui.showFilteredModules(modules, "test filter");

        String output = outputStream.toString();
        assertTrue(output.contains("1 module"));
        assertTrue(output.contains("CS2113"));
    }

    @Test
    void showClashWarning_displaysWarning() {
        Module module1 = new Module("CS2113", "SE", "Monday", "10:00", "12:00", "lecture");
        Module module2 = new Module("CS2040", "DSA", "Monday", "10:00", "12:00", "lecture");

        ui.showClashWarning(module1, module2);

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("warning"));
        assertTrue(output.toLowerCase().contains("clash"));
    }

    @Test
    void readCommand_readsInput() {
        String testInput = "test command\n";
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));
        UI testUI = new UI();

        String command = testUI.readCommand();
        assertEquals("test command", command);
        testUI.close();
    }
}
