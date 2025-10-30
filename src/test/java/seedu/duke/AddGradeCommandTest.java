package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddGradeCommandTest {

    @Test
    public void execute_validCourses_coursesAdded() throws UniflowException {
        CourseRecord record = new CourseRecord();
        UI ui = new UI();
        ModuleList modules = new ModuleList();

        assertTrue(record.isEmpty());

        new AddGradeCommand("CS2113", 5, "B", true).execute(ui, modules,  record);
        new AddGradeCommand("EC3322", 5, "A", false).execute(ui, modules,  record);

        assertEquals(2, record.getSize());
        assertEquals(1, record.getMajorSize());
        assertEquals(4.25, record.computeGpa(), 0.01);
    }

}
