package seedu.duke;

import java.util.ArrayList;

public class ShowTimetable extends Command {
    @Override
    public void execute(UI ui) throws UniflowException {
        ArrayList<String> timetable = Uniflow.getTimetable();
        if (timetable.isEmpty()) {
            ui.showMessage("Your timetable is empty.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String entry : timetable) {
            sb.append(entry).append("\n");
        }
        ui.showMessage(sb.toString().trim());
    }
}
