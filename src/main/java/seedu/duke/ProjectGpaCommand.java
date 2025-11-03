package seedu.duke;

/**
 * It is used to compute the projected GPA of the user based on courses' predicted grade in the temporary record.
 */
public class ProjectGpaCommand extends Command {
    private final CourseRecord tempRecord = Uniflow.getTempRecord();

    /**
     * Extracts information based on course record and temporary record.
     * Returns the projected cumulative GPA and major required courses cumulative GPA.
     *
     * @param ui The UI object.
     * @param modules The list storing the modules' information.
     * @param courseRecord The list storing the users' courses grade information.
     * @throws UniflowException If there's an error during the computation
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (courseRecord.isEmpty() && tempRecord.isEmpty()) {
            ui.showError("No courses available in the record yet.");
            return;
        }

        double projectedGpa = courseRecord.computeCombinedGpa(tempRecord);
        double projectedMajorGpa = courseRecord.computeCombinedMajorGpa(tempRecord);
        ui.showMessage("Your projected GPA will be: " + String.format("%.2f", projectedGpa) + "\n"
                + " Your projected major GPA will be: " + String.format("%.2f", projectedMajorGpa));
    }
}
