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
        // empty for now
    }

    /**
     * Returns true since this is an exit command.
     *
     * @return true to indicate the application should exit
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
