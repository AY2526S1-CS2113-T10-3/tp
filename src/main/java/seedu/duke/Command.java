package seedu.duke;

public abstract class Command {
    public abstract void execute(UI ui) throws UniflowException;
    /**
     * Returns whether this command should cause the application to exit.
     *
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
