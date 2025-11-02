package seedu.duke;

public class ListCommand extends Command {
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (modules.isEmpty()) {
            System.out.println("No modules in the list.");
            return;
        }

        System.out.println("Your modules:");
        for (int i = 0; i < modules.getSize(); i++) {
            Module module = modules.getAllModules().get(i);
            System.out.println(module.getId());
        }
    }
}
