package seedu.duke;

public class ResetTimetableCommand extends Command {

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        if (modules.isEmpty()) {
            ui.showMessage("Timetable is already empty!");
            return;
        }
        modules.clear();
        ui.showMessage("Timetable has been reset!");
    }
}
