package seedu.duke;

/**
 * Allows users to remove a predicted course grade from the temporary record,
 * by specifying the index of that record.
 */
public class RemoveTestGradeCommand extends Command {
    private final int index;
    private final CourseRecord tempRecord = Uniflow.getTempRecord();

    /**
     * Constructs the RemoveGradeCommand with the users' specified index
     *
     * @param index The corresponding index of the grade users want to remove from temporary record.
     */
    public RemoveTestGradeCommand(int index) {
        this.index = index;
    }

    /**
     * Remove the record specified by users according to their (valid) input index.
     *
     * @param ui The UI object.
     * @param modules The list storing the modules' information.
     * @param courseRecord The list storing the users' course grade information.
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (tempRecord.isEmpty()) {
            ui.showMessage("There is no course in the temporary record now.");
            return;
        }

        if (index <= 0 || index > tempRecord.getCourseRecord().size()) {
            throw new UniflowException("This is not a valid index.");
        }

        Course removed = tempRecord.getCourseRecord().remove(index - 1);
        ui.showMessage("Removed predicted grade from temporary record: " + removed.toString());
    }
}
