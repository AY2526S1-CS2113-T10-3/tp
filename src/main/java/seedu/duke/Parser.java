package seedu.duke;

public class Parser {
    private static final String COMMAND_INSERT = "insert";

    public static Command parse(String fullCommand) throws UniflowException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new UniflowException("Command cannot be empty");
        }
        String trimmedCommand = fullCommand.trim();
        if (trimmedCommand.startsWith(COMMAND_INSERT)) {
            return parseInsertCommand(trimmedCommand);
        }
        return null;
    }

    private static Command parseInsertCommand(String command) throws UniflowException {
        try {
            String[] parts = command.substring(7).split(" "); // Remove "insert " prefix

            String moduleName = null;
            String day = null;
            String startTime = null;
            String endTime = null;

            for (String part : parts) {
                if (part.startsWith(("n/"))) {
                    moduleName = part.substring(2);
                } else if (part.startsWith(("d/"))) {
                    day = part.substring(2);
                } else if (part.startsWith(("f/"))) {
                    startTime = part.substring(2);
                } else if (part.startsWith(("t/"))) {
                    endTime = part.substring(2);
                }
            }

            if (moduleName == null || day == null || startTime == null || endTime == null) {
                throw new UniflowException("Missing fields in insert command.");
            }
            return new InsertCommand(moduleName, day, startTime, endTime);
        } catch (Exception e) {
            throw new UniflowException("Failed to parse inert command: " + e.getMessage());
        }
    }
}
