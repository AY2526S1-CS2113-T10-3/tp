package seedu.duke;

public class ListCommand extends Command{
    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        ui.showModuleList(modules);
    }
}
