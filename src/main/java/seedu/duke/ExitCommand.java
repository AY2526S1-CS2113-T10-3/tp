package seedu.duke;

public class ExitCommand extends Command {
    @Override
    public void execute(UI ui) throws UniflowException {
        ui.showGoodbye();
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
