package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ModuleTest {

    @Test
    public void constructor_validInputs_createsModule() {
        Module module = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", "lecture");

        assertEquals("CS2113", module.getId());
        assertEquals("Software Engineering", module.getName());
        assertEquals("Monday", module.getDay());
        assertEquals("14:00", module.getStartTime());
        assertEquals("16:00", module.getEndTime());
        assertEquals("lecture", module.getSessionType());
    }

    @Test
    public void constructor_nullSessionType_defaultsToLecture() {
        Module module = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", null);
        assertEquals("lecture", module.getSessionType());
    }

    @Test
    public void hasTutorial_tutorialSessionType_returnsTrue() {
        Module module = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", "tutorial");
        assertTrue(module.hasTutorial());
    }

    @Test
    public void hasTutorial_lectureSessionType_returnsFalse() {
        Module module = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", "lecture");
        assertFalse(module.hasTutorial());
    }

    @Test
    public void hasTutorial_caseInsensitive_returnsTrue() {
        Module module = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", "TUTORIAL");
        assertTrue(module.hasTutorial());
    }

    @Test
    public void toString_containsAllFields() {
        Module module = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", "lecture");
        String result = module.toString();

        assertTrue(result.contains("CS2113"));
        assertTrue(result.contains("Software Engineering"));
        assertTrue(result.contains("Monday"));
        assertTrue(result.contains("14:00"));
        assertTrue(result.contains("16:00"));
        assertTrue(result.contains("lecture"));
    }
}
