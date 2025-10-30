package seedu.duke;

/**
 * It is used to add a predicted course grade to a temporary record.
 * The temporary record will be used in the computation of projected GPA.
 */
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
