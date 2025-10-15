package seedu.duke;

public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_INSERT = "insert";
    private static final String COMMAND_SCORE = "score";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_FILTER = "filter";

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
        if (trimmedCommand.startsWith(COMMAND_FILTER)) {
            return parseFilterCommand(trimmedCommand);
        }

        if (trimmedCommand.startsWith(COMMAND_SCORE)) {
            return parseScoreCommand(trimmedCommand);
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
            String sessionType = null;

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
                } else if (part.startsWith(("s/"))) {
                    sessionType = part.substring(2);
                }
            }

            if (id == null || moduleName == null || day == null || startTime == null || endTime == null) {
                throw new UniflowException("Missing fields in insert command.");
            }
            return new InsertCommand(id, moduleName, day, startTime, endTime, sessionType);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new UniflowException("Failed to parse insert command: " + e.getMessage());
        }
    }

    private static Command parseScoreCommand(String command) throws UniflowException {
        String args = command.substring(COMMAND_SCORE.length()).trim();
        //score 10/10 15/20 ... denominators must add up to 100
        if (args.isEmpty()) {
            throw new UniflowException("Usage: score x1/y1 x2/y2 ...");
        }
        return new ScoreCommand(args);
    }

    private static Command parseListCommand() throws UniflowException {
        return new ListCommand();
    }

    private static Command parseFilterCommand(String command) throws UniflowException {
        String trimmedCommand = command.substring(6).trim(); // Remove "filter" prefix

        if (trimmedCommand.isEmpty()) {
            throw new UniflowException("Filter command requires parameters. " +
                    "Usage: filter type/VALUE, filter hastutorial, filter notutorial, " +
                    "filter day/VALUE, filter id/VALUE, filter name/VALUE");
        }

        // Check for boolean filters first
        if (trimmedCommand.equalsIgnoreCase("hastutorial")) {
            return new FilterCommand("hastutorial", null);
        }
        if (trimmedCommand.equalsIgnoreCase("notutorial")) {
            return new FilterCommand("notutorial", null);
        }

        // Parse filters with values
        String[] parts = trimmedCommand.split("/", 2);
        if (parts.length != 2) {
            throw new UniflowException("Invalid filter format. Use: filter FILTERTYPE/VALUE");
        }

        String filterType = parts[0].trim();
        String filterValue = parts[1].trim();

        if (filterValue.isEmpty()) {
            throw new UniflowException("Filter value cannot be empty.");
        }

        return new FilterCommand(filterType, filterValue);
    }
}