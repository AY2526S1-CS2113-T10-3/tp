package seedu.duke;

public class InsertCommand extends Command {
    private final Module module;

    InsertCommand(String id, String moduleName, String day, String startTime, String endTime, String sessionType) {
        this.module = new Module(id, moduleName, day, startTime, endTime, sessionType);
    }

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        Module clash = modules.findClash(module);
        if (clash != null && clash != module) {
            ui.showMessage("Warning: " + module.getId() + " clashes with " + clash.getId()
                    + " on " + module.getDay() + " (" + module.getStartTime() + "-" + module.getEndTime() + ")");
            ui.showMessage("Do you still want to add this module? (yes/no)");
            String response = ui.readCommand().trim().toLowerCase();
            if (!response.equals("yes")) {
                ui.showMessage("Module not added.");
                return;
            }
        }
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
