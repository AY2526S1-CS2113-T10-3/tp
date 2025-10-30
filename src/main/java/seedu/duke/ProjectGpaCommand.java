package seedu.duke;

/**
 * It is used to compute the projected GPA of the user based on courses' predicted grade in the temporary record.
 */
public class ProjectGpaCommand extends Command {
    private final CourseRecord tempRecord = Uniflow.getTempRecord();

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
