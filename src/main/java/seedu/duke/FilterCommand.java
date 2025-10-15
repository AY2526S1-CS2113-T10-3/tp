package seedu.duke;

public class FilterCommand extends Command {
    private final String filterType;
    private final String filterValue;

    /**
     * Creates a filter command with the specified type and value.
     *
     * @param filterType the type of filter (e.g., "type", "day", "id", "name", "hastutorial", "notutorial")
     * @param filterValue the value to filter by (null for boolean filters like hastutorial)
     */
    public FilterCommand(String filterType, String filterValue) {
        this.filterType = filterType.toLowerCase();
        this.filterValue = filterValue;
    }

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        ModuleList filteredModules;

        switch (filterType) {
        case "type":
            if (filterValue == null || filterValue.trim().isEmpty()) {
                throw new UniflowException("Session type cannot be empty.");
            }
            filteredModules = modules.findBySessionType(filterValue);
            ui.showFilteredModules(filteredModules, "session type '" + filterValue + "'");
            break;

        case "hastutorial":
            filteredModules = modules.findWithTutorials();
            ui.showFilteredModules(filteredModules, "modules with tutorial sessions");
            break;

        case "notutorial":
            filteredModules = modules.findWithoutTutorials();
            ui.showFilteredModules(filteredModules, "modules without tutorial sessions");
            break;

        case "day":
            if (filterValue == null || filterValue.trim().isEmpty()) {
                throw new UniflowException("Day cannot be empty.");
            }
            filteredModules = modules.findByDay(filterValue);
            ui.showFilteredModules(filteredModules, "day '" + filterValue + "'");
            break;

        case "id":
            if (filterValue == null || filterValue.trim().isEmpty()) {
                throw new UniflowException("Module ID cannot be empty.");
            }
            filteredModules = modules.findById(filterValue);
            ui.showFilteredModules(filteredModules, "ID containing '" + filterValue + "'");
            break;

        case "name":
            if (filterValue == null || filterValue.trim().isEmpty()) {
                throw new UniflowException("Module name cannot be empty.");
            }
            filteredModules = modules.findByName(filterValue);
            ui.showFilteredModules(filteredModules, "name containing '" + filterValue + "'");
            break;

        default:
            throw new UniflowException("Unknown filter type: " + filterType);
        }
    }
}
