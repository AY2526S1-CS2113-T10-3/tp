package seedu.duke;

/**
 * A command to add a predicted course grade to a temporary record.
 * The temporary record will be used in the computation of projected GPA.
 * The added course will not affect the permanent record for completed courses.
 */
public class AddTestGradeCommand extends Command{
    private final Course tempCourse;
    private final CourseRecord tempRecord = Uniflow.getTempRecord();

    /**
     * Creates an AddTestGradeCommand with the course details input by users.
     *
     * @param code The course code.
     * @param credits The number of credits for the course.
     * @param grade The grade the users predict they might attain for the course.
     * @param isMajor True if the course is a major course, false if not.
     */
    public AddTestGradeCommand(String code, int credits, String grade, boolean isMajor) {
        this.tempCourse = new Course(code, credits, grade, isMajor);
    }

    /**
     * Executes the command and add the course to the temporary course record
     *
     * @param ui The UI object.
     * @param modules The list storing the modules' information.
     * @param courseRecord The list storing the users' course grade information.
     * @throws UniflowException If error occurs when adding the course.
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        tempRecord.addCourse(tempCourse);
        ui.showMessage("Added course: " + tempCourse + " in temporary record.");
    }
}
