package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseRecordTest {

    @Test
    public void computeGpa_validCourses_correctGpa() throws UniflowException {
        CourseRecord record = new CourseRecord();

        record.addCourse(new Course("CS2113", 5, "B", true));
        record.addCourse(new Course("EC3322", 5, "A", false));

        assertEquals(4.25, record.computeGpa(), 0.01);
        assertEquals(3.5, record.computeMajorGpa(), 0.01);
    }

    @Test
    public void computeMajorGpa_noMajorCourses_returnsZero() throws UniflowException {
        CourseRecord record = new CourseRecord();
        record.addCourse(new Course("CS2113", 5, "A", false));
        assertEquals(0.0, record.computeMajorGpa(), 0.01);
    }

}
