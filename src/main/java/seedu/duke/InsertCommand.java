package seedu.duke;

public class InsertCommand extends Command {
    private final Module module;

    InsertCommand(String id, String moduleName, String day, String startTime, String endTime) {
        this.module = new Module(id, moduleName, day, startTime, endTime);
    }
    @Override
<<<<<<< HEAD
    public void execute(UI ui) throws UniflowException {
        String entry = moduleName + " " + day + " " + startTime + "-" + endTime;
        Uniflow.getTimetable().add(entry);
        ui.showMessage(entry + " has been inserted into the timetable.");
    }

=======
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
>>>>>>> 33c15f92a5174ab04794e7d05b7ae1f4343b3720
}
