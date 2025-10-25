package seedu.duke;

public class ShowTimetableCommand extends Command {

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (modules.isEmpty()) {
            ui.showMessage("Your timetable is empty!");
            return;
        }
        ui.showMessage("Here is your timetable:");
        for (int i = 0; i < modules.getSize(); i++) {
            Module m = modules.getAllModules().get(i);
            ui.showMessage((i + 1) + ". " + m.toString());
        }
    }
}
