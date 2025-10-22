package seedu.duke;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ScoreCommandTest {

    @Test
    public void execute_validBreakdown_savesScoresCorrectly() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS1010", "Programming", "Mon", "10:00", "12:00", "Lecture"));
        UI ui = new UI();

        new ScoreCommand("CS1010", "exam:50 project:40").execute(ui, modules);

        Module module = modules.getModuleByID("CS1010");
        assertEquals(2, module.getScoreBreakdown().size());
        assertEquals(Integer.valueOf(50), module.getScoreBreakdown().get("exam"));
        assertEquals(Integer.valueOf(40), module.getScoreBreakdown().get("project"));
    }

    @Test(expected = UniflowException.class)
    public void execute_invalidValue_throwsException() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS1234", "Test", "Mon", "9:00", "10:00", "Lecture"));
        UI ui = new UI();

        new ScoreCommand("CS1234", "exam:abc").execute(ui, modules);
    }

    @Test(expected = UniflowException.class)
    public void execute_moduleDoesNotExist_throwsException() throws UniflowException {
        ModuleList modules = new ModuleList();
        UI ui = new UI();

        new ScoreCommand("CS9999", "exam:50").execute(ui, modules);
    }
}
