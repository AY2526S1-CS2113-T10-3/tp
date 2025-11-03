package seedu.duke;

/**
 * A command to add a course grade to the course record.
 */
public class AddGradeCommand extends Command {
    private final Course course;

    /**
     * Creates an AddGradeCommand with the course details input by users.
     *
     * @param code The course code.
     * @param credits The number of credits for the course.
     * @param grade The grade the users attained for the course.
     * @param isMajor True if the course is a major course, false if not.
     */
    public AddGradeCommand(String code, int credits, String grade, boolean isMajor) {
        this.course = new Course(code, credits, grade, isMajor);
    }

    /**
     * Executes the command and add the course to the course record
     *
     * @param ui The UI object.
     * @param modules The list storing the modules' information.
     * @param courseRecord The list storing the users' course grade information.
     * @throws UniflowException If error occurs when adding the course.
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        courseRecord.addCourse(course);
        ui.showMessage("OK! Now I have added your record of the course " + course);
    }
}
