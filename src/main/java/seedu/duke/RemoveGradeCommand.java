package seedu.duke;

/**
 * Allows users to remove a course grade record from the saved record,
 * by specifying the index of that record.
 */
public class RemoveGradeCommand extends Command {
    private final int index;

    /**
     * Constructs the RemoveGradeCommand with the users' specified index
     *
     * @param index The corresponding index of the course grade record users want to remove.
     */
    public RemoveGradeCommand(int index) {
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
        if (courseRecord.isEmpty()) {
            ui.showMessage("There are no courses in the record now.");
            return;
        }

        if (index <= 0 || index > courseRecord.getCourseRecord().size()) {
            throw new UniflowException("This is not a valid index.");
        }

        //stored the removed course for later display
        Course removed = courseRecord.getCourseRecord().remove(index - 1);
        ui.showMessage("Removed course grade record: " + removed.toString());
    }
}
