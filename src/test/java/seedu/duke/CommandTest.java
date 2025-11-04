package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CommandTest {

    private UI ui;
    private ModuleList modules;
    private CourseRecord courseRecord;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        ui = new UI();
        modules = new ModuleList();
        courseRecord = new CourseRecord();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
    }

    @Test
    void exitCommand_isExit_returnsTrue() {
        ExitCommand command = new ExitCommand();
        assertTrue(command.isExit());
    }

    @Test
    void insertCommand_isExit_returnsFalse() {
        InsertCommand command = new InsertCommand("CS2113", "SE", "Monday", "10:00", "12:00", "lecture");
        assertFalse(command.isExit());
    }

    @Test
    void deleteCommand_isExit_returnsFalse() {
        DeleteCommand command = new DeleteCommand(1);
        assertFalse(command.isExit());
    }

    @Test
    void listCommand_execute_doesNotThrow() {
        ListCommand command = new ListCommand();
        assertDoesNotThrow(() -> command.execute(ui, modules, courseRecord));
    }

    @Test
    void exitCommand_execute_showsGoodbye() throws UniflowException {
        ExitCommand command = new ExitCommand();
        command.execute(ui, modules, courseRecord);

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("bye"));
    }

    @Test
    void showTimetableCommand_emptyList_showsMessage() throws UniflowException {
        ShowTimetableCommand command = new ShowTimetableCommand();
        command.execute(ui, modules, courseRecord);

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("empty"));
    }

    @Test
    void resetTimetableCommand_clearsModules() throws UniflowException {
        modules.addModule(new Module("CS2113", "SE", "Monday", "10:00", "12:00", "lecture"));

        ResetTimetableCommand command = new ResetTimetableCommand();
        command.execute(ui, modules, courseRecord);

        assertTrue(modules.isEmpty());
    }
}
