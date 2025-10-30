package seedu.duke;

/**
 * It is used to add a course grade to course record.
 */
public class AddGradeCommand extends Command {
    private final Course course;

    public AddGradeCommand(String code, int credits, String grade, boolean isMajor) {
        this.course = new Course(code, credits, grade, isMajor);
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        courseRecord.addCourse(course);
        ui.showMessage("OK! Now I have added your record of the course " + course);
    }
}
