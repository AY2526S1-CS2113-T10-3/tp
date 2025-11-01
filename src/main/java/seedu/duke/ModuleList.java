package seedu.duke;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public Module getModuleByID(String moduleID) throws UniflowException {
        for (Module module : modules) {
            if (module.getId().equals(moduleID))  {
                return module;
            }
        }
        throw new UniflowException("This module does not exist.");
    }

    public boolean hasModule(String moduleID) {
        for (Module module : modules) {
            if (module.getId().equals(moduleID))  {
                return true;
            }
        }
        return false;
    }

    public Module deleteModuleById(String moduleId) throws UniflowException {
        for (int i = 0; i < modules.size(); i++) {
            Module m = modules.get(i);
            if (m.getId().equalsIgnoreCase(moduleId)) {
                return deleteModule(i);
            }
        }
        throw new UniflowException("No module found with ID: " + moduleId);
    }

    public Module deleteModule(int index) throws UniflowException {
        Module module = getModule(index);
        modules.remove(index);
        return module;
    }

    public boolean isEmpty() {
        return modules.isEmpty();
    }

    public int getSize() {
        return modules.size();
    }

    /**
     * Filters modules based on a given predicate.
     *
     * @param filter the predicate to filter modules
     * @return a new ModuleList containing only modules that match the filter
     */
    public ModuleList filter(Predicate<Module> filter) {
        List<Module> filteredModules = new ArrayList<>();
        for (Module module : modules) {
            if (filter.test(module)) {
                filteredModules.add(module);
            }
        }
        return new ModuleList(filteredModules);
    }

    /**
     * Finds modules by session type.
     *
     * @param sessionType the type of session to search for (e.g., "tutorial", "lecture", "lab")
     * @return a new ModuleList containing modules with the specified session type
     */
    public ModuleList findBySessionType(String sessionType) {
        return filter(module -> module.getSessionType().equalsIgnoreCase(sessionType));
    }

    /**
     * Finds modules that have tutorial sessions.
     *
     * @return a new ModuleList containing modules with tutorial sessions
     */
    public ModuleList findWithTutorials() {
        return filter(Module::hasTutorial);
    }

    /**
     * Finds modules that do not have tutorial sessions.
     *
     * @return a new ModuleList containing modules without tutorial sessions
     */
    public ModuleList findWithoutTutorials() {
        return filter(module -> !module.hasTutorial());
    }

    /**
     * Finds modules by day of the week.
     *
     * @param day the day to search for
     * @return a new ModuleList containing modules on the specified day
     */
    public ModuleList findByDay(String day) {
        return filter(module -> module.getDay().equalsIgnoreCase(day));
    }

    /**
     * Finds modules by module ID (partial match).
     *
     * @param id the module ID or partial ID to search for
     * @return a new ModuleList containing modules whose ID contains the search term
     */
    public ModuleList findById(String id) {
        return filter(module -> module.getId().toLowerCase().contains(id.toLowerCase()));
    }

    /**
     * Finds modules by module name (partial match).
     *
     * @param name the module name or partial name to search for
     * @return a new ModuleList containing modules whose name contains the search term
     */
    public ModuleList findByName(String name) {
        return filter(module -> module.getName().toLowerCase().contains(name.toLowerCase()));
    }
    public Module findClash(Module newModule) {
        for (Module existing : modules) {
            if (existing.getDay().equalsIgnoreCase(newModule.getDay())) {
                boolean overlaps = checkOverlap(existing.getStartTime(), existing.getEndTime(),
                        newModule.getStartTime(), newModule.getEndTime());
                if (overlaps) {
                    return existing;
                }
            }
        }
        return null;
    }

    private boolean checkOverlap(String start1, String end1, String start2, String end2) {
        try {
            java.time.LocalTime s1 = java.time.LocalTime.parse(start1);
            java.time.LocalTime e1 = java.time.LocalTime.parse(end1);
            java.time.LocalTime s2 = java.time.LocalTime.parse(start2);
            java.time.LocalTime e2 = java.time.LocalTime.parse(end2);

            return !e1.isBefore(s2) && !e2.isBefore(s1);
        } catch (Exception e) {
            return false;
        }
    }

    public void clear() {
        modules.clear();
    }
}
