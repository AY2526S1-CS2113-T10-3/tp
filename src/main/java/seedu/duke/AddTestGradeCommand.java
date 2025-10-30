package seedu.duke;

public class AddTestGradeCommand extends Command{
    private final Course tempCourse;
    private final CourseRecord tempRecord = Uniflow.getTempRecord();

    public AddTestGradeCommand(String code, int credits, String grade, boolean isMajor) {
        this.tempCourse = new Course(code, credits, grade, isMajor);
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        tempRecord.addCourse(tempCourse);
        ui.showMessage("Added course: " + tempCourse + " in temporary record.");
    }
}
