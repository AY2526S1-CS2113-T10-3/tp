package seedu.duke;

public class InsertCommand extends Command {
    private final Module module;

    InsertCommand(String id, String moduleName, String day, String startTime, String endTime) {
        this.module = new Module(id, moduleName, day, startTime, endTime);
    }
    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        // empty for now
        modules.addModule(module);
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
