package seedu.duke;

/**
 * Return the users' cumulative gpa and major required courses cumulative gpa.
 */
public class ComputeGpaCommand extends Command {

    /**
     * Extract information based on course record.
     * Return a summary of information for cumulative gpa and major required courses cumulative gpa.
     *
     * @param ui The UI object.
     * @param modules The list storing the modules' information.
     * @param courseRecord The list storing the users' courses grade information.
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (courseRecord.isEmpty()) {
            ui.showError("No courses were added in the record!");
            return;
        }

        ui.showMessage("You have studied " + courseRecord.getSize() + " courses.\n"
                + " Your total grade points are " + courseRecord.getTotalGradePoints() + "\n"
                + " Number of credits you have studied: " + courseRecord.getTotalCredits() + "\n"
                + String.format(" Your GPA is: %.2f", courseRecord.computeGpa()));

        if (courseRecord.getMajorSize()==0) {
            ui.showMessage("You have not studied any major required courses yet.");
        } else {
            ui.showMessage("You have studied " + courseRecord.getMajorSize() + " major courses.\n"
                    + " Your total major course grade points are " + courseRecord.getMajorTotalGradePoints() + "\n"
                    + " Number of credits you have studied: " + courseRecord.getMajorTotalCredits() + "\n"
                    + String.format(" Your major GPA is: %.2f", courseRecord.computeMajorGpa()));
        }
    }

}
