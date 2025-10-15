package seedu.duke;

public class InsertCommand extends Command {
    private final Module module;

    InsertCommand(String id, String moduleName, String day, String startTime, String endTime, String sessionType) {
        this.module = new Module(id, moduleName, day, startTime, endTime, sessionType);
    }

    // Backward compatibility constructor
    InsertCommand(String id, String moduleName, String day, String startTime, String endTime) {
        this(id, moduleName, day, startTime, endTime, "lecture");
    }

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        modules.addModule(module);
        ui.showMessage("Got it! I've added this module:\n  " + module.toString() +
                "\nNow you have " + modules.getSize() + " module(s) in the list.");
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