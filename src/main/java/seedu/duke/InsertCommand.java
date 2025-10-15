package seedu.duke;

public class InsertCommand extends Command {
    private final String moduleName;
    private final String day;
    private final String startTime;
    private final String endTime;

    InsertCommand(String moduleName, String day, String startTime, String endTime) {
        this.moduleName = moduleName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Override
    public void execute(UI ui) throws UniflowException {
        String entry = moduleName + " " + day + " " + startTime + "-" + endTime;
        Uniflow.getTimetable().add(entry);
        ui.showMessage(entry + " has been inserted into the timetable.");
    }

}
