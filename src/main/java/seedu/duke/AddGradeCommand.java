package seedu.duke;

public class AddGradeCommand extends Command {
    private final CourseRecord courseRecord;
    private final Course course;

    public AddGradeCommand(String code, int credits, String grade, CourseRecord courseRecord) {
        this.courseRecord = courseRecord;
        this.course = new Course(code, credits, grade);
    }

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        courseRecord.addCourse(course);
        ui.showMessage("OK! Now I have added your record of the course " + course);
    }
}
