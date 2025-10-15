package seedu.duke;

import java.util.ArrayList;
import java.util.List;

public class ModuleList {
    private final List<Module> modules;

    public ModuleList() {
        modules = new ArrayList<>();
    }

    public ModuleList(List<Module> initialTasks) {
        this.modules = new ArrayList<>(initialTasks);
    }

    public List<Module> getAllModules() {
        return new ArrayList<>(modules);
    }

    public void addModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        modules.add(module);
    }

    public Module getModule(int index) throws UniflowException {
        if (index < 0 || index >= modules.size()) {
            throw new UniflowException("OOPS!!! Task number " + (index + 1)
                    + " does not exist. Please check your task list.");
        }
        return modules.get(index);
    }

    public Module deleteModule(int index) throws UniflowException {
        Module module = getModule(index - 1);
        modules.remove(index - 1);
        return module;
    }
    public boolean isEmpty() {
        return modules.isEmpty();
    }

    public int getSize() {
        return modules.size();
    }
}
