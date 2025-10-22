package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ScoreCommandTest {

    @Test
    public void execute_validBreakdown_savesScoresCorrectly() throws UniflowException {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS1010","Programming","Mon","10:00","12:00","Lecture"));
        UI ui = new UI();
        new ScoreCommand("CS1010", "exam:50 project:40").execute(ui, modules);

        Module m = modules.getModuleByID("CS1010");
        assertEquals(2, m.getScoreBreakdown().size());
        assertEquals(Integer.valueOf(50), m.getScoreBreakdown().get("exam"));
        assertEquals(Integer.valueOf(40), m.getScoreBreakdown().get("project"));
    }

    @Test
    public void execute_invalidValue_throwsException() {
        ModuleList modules = new ModuleList();
        modules.addModule(new Module("CS1234","Test","Mon","9:00","10:00","Lecture"));
        UI ui = new UI();

        try {
            new ScoreCommand("CS1234", "exam:abc").execute(ui, modules);
            fail("Expected UniflowException");
        } catch (UniflowException expected) {
        }
    }

    @Test
    public void execute_moduleDoesNotExist_throwsException() {
        ModuleList modules = new ModuleList();
        UI ui = new UI();

        try {
            new ScoreCommand("CS9999", "exam:50").execute(ui, modules);
            fail("Expected UniflowException");
        } catch (UniflowException expected) {
        }
    }
}
