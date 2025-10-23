package seedu.duke;

public class ListCommand extends Command{
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        ui.showModuleList(modules);
    }
}
