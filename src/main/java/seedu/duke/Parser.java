package seedu.duke;

public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_INSERT = "insert";
    private static final String COMMAND_LIST = "list";

    public static Command parse(String fullCommand) throws UniflowException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new UniflowException("Command cannot be empty");
        }
        String trimmedCommand = fullCommand.trim();
        if (trimmedCommand.equals(COMMAND_BYE)) {
            return new ExitCommand();
        }
        if (trimmedCommand.startsWith(COMMAND_INSERT)) {
            return parseInsertCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_LIST)) {
            return parseListCommand();
        }

        throw new UniflowException("Invalid command");
    }

    private static Command parseInsertCommand(String command) throws UniflowException {
        try {
            String[] parts = command.substring(7).split(" "); // Remove "insert " prefix

            String id = null;
            String moduleName = null;
            String day = null;
            String startTime = null;
            String endTime = null;

            for (String part : parts) {
                if (part.startsWith(("i/"))) {
                    id = part.substring(2);
                } else if (part.startsWith(("n/"))) {
                    moduleName = part.substring(2);
                } else if (part.startsWith(("d/"))) {
                    day = part.substring(2);
                } else if (part.startsWith(("f/"))) {
                    startTime = part.substring(2);
                } else if (part.startsWith(("t/"))) {
                    endTime = part.substring(2);
                }
            }

            if (id == null || moduleName == null || day == null || startTime == null || endTime == null) {
                throw new UniflowException("Missing fields in insert command.");
            }
            return new InsertCommand(id, moduleName, day, startTime, endTime);
        } catch (Exception e) {
            throw new UniflowException("Failed to parse inert command: " + e.getMessage());
        }
    }
    private static Command parseListCommand() throws UniflowException {
        return new ListCommand();
    }
}
