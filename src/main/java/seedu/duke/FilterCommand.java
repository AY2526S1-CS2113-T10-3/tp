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
}
