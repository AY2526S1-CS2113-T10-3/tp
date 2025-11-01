package seedu.duke;

public class DeleteCommand extends Command {
    private final int index;
    DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        Module deletedModule = modules.deleteModule(index - 1);
        ui.showMessage("Noted. I've removed this module:\n  " + deletedModule.toString() +
                "\nNow you have " + modules.getSize() + " module(s) left in the list.");
    }

    /**
     * Returns true since this is an exit command.
     *
     * @return true to indicate the application should exit
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
