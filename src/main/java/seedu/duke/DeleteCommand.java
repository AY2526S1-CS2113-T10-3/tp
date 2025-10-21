package seedu.duke;

public class DeleteCommand extends Command {
    private final String moduleId;

    DeleteCommand(String moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        Module deletedModule = modules.deleteModuleById(moduleId);
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
