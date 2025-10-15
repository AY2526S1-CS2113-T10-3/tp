package seedu.duke;

public class ResetTimetable extends Command {
    @Override
    public void execute(UI ui) throws UniflowException {
        ui.showMessage("Are you sure? This will remove all current existing entries in timetable. (Y/N)");
        String reply = ui.readCommand().trim();
        if (reply.equalsIgnoreCase("Y")) {
            Uniflow.getTimetable().clear();
            ui.showMessage("Timetable cleared.");
        } else {
            ui.showMessage("Cancelled.");
        }
    }
}
