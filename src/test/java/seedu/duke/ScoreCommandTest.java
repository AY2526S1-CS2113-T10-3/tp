package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreCommandTest {

    @Test
    public void execute_validBreakdown_savesScoresCorrectly() throws UniflowException {
        ModuleList modules = new ModuleList();
        Module module = new Module("CS1010", "Programming", "Monday", "10:00", "12:00", "Lecture");
        modules.addModule(module);
        UI ui = new UI();
        ScoreCommand command = new ScoreCommand("CS1010", "exam:50 project:40");

        command.execute(ui, modules);

        Module saved = modules.getModuleByID("CS1010");
        assertEquals(2, saved.getScoreBreakdown().size());
        assertEquals(50, saved.getScoreBreakdown().get("exam"));
        assertEquals(40, saved.getScoreBreakdown().get("project"));
    }

    @Test
    public void execute_invalidValue_throwsException() {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS1234", "Test", "Mon", "9:00", "10:00", "Lecture"));
        UI ui = new UI();
        ScoreCommand command = new ScoreCommand("CS1234", "exam:abc");

        assertThrows(UniflowException.class, () -> command.execute(ui, modules));
    }

    @Test
    public void execute_moduleDoesNotExist_throwsException() {
        ModuleList modules = new ModuleList(); // empty list
        UI ui = new UI();
        ScoreCommand command = new ScoreCommand("CS9999", "exam:50");

        assertThrows(UniflowException.class, () -> command.execute(ui, modules));
    }
}
