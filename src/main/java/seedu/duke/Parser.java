package seedu.duke;

import java.util.Arrays;

public class Parser {

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_INSERT = "insert";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SCORE = "score";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_ADDGRADE = "addgrade";
    private static final String COMMAND_GPA = "gpa";
    private static final String COMMAND_FILTER = "filter";
    private static final String COMMAND_SHOW_TIMETABLE = "show timetable";
    private static final String COMMAND_RESET_TIMETABLE = "reset timetable";
    private static final String COMMAND_REVIEW = "review";
    private static final String COMMAND_ADD_REVIEW = "addreview";
    private static final String COMMAND_RATE = "rate";
    private static final int RATING_QUERY_MODE = -1;


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
        if (trimmedCommand.startsWith(COMMAND_DELETE)) {
            return parseDeleteCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_LIST)) {
            return parseListCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_FILTER)) {
            return parseFilterCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_SCORE)) {
            return parseScoreCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_ADDGRADE)) {
            return parseAddGradeCommand(trimmedCommand);
        }
        if (trimmedCommand.equals(COMMAND_GPA)) {
            return new ComputeGpaCommand();
        }
        if (trimmedCommand.equalsIgnoreCase(COMMAND_SHOW_TIMETABLE)) {
            return new ShowTimetableCommand();
        }
        if (trimmedCommand.equalsIgnoreCase(COMMAND_RESET_TIMETABLE)) {
            return new ResetTimetableCommand();
        }
        if (trimmedCommand.startsWith(COMMAND_REVIEW)) {
            return parseReviewCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_ADD_REVIEW)) {
            return parseAddReviewCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_RATE)) {
            return parseRateCommand(trimmedCommand);
        }

        throw new UniflowException("Invalid command");
    }

    private static Command parseListCommand(String command) throws UniflowException {
        return new ListCommand();
    }

    private static Command parseAddGradeCommand(String command) throws UniflowException {
        String[] parts = command.substring(COMMAND_ADDGRADE.length()).trim().split(" ");
        String code = null;
        int credits = 0;
        String grade = null;

        for (String part : parts) {
            if (part.startsWith("c/")) {
                code = part.substring(2);
            } else if (part.startsWith("cr/")) {
                credits = Integer.parseInt(part.substring(3));
            } else if (part.startsWith("g/")) {
                grade = part.substring(2);
            }
        }

        if (code == null || grade == null || credits == 0) {
            throw new UniflowException(
                    "Please follow the format: addgrade c/COURSE_CODE cr/CREDITS g/GRADE");
        }

        return new AddGradeCommand(code, credits, grade);
    }

    private static Command parseInsertCommand(String command) throws UniflowException {
        try {
            String[] parts = command.substring(7).split(" ");
            String id = null;
            String moduleName = null;
            String day = null;
            String startTime = null;
            String endTime = null;
            String sessionType = null;

            for (String part : parts) {
                if (part.startsWith("i/")) {
                    id = part.substring(2);
                } else if (part.startsWith("n/")) {
                    moduleName = part.substring(2);
                } else if (part.startsWith("d/")) {
                    day = part.substring(2);
                } else if (part.startsWith("f/")) {
                    startTime = part.substring(2);
                } else if (part.startsWith("t/")) {
                    endTime = part.substring(2);
                } else if (part.startsWith("s/")) {
                    sessionType = part.substring(2);
                }
            }

            if (id == null || moduleName == null || day == null
                    || startTime == null || endTime == null) {
                throw new UniflowException("Missing fields in insert command.");
            }
            return new InsertCommand(id, moduleName, day, startTime, endTime, sessionType);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new UniflowException("Failed to parse insert command: " + e.getMessage());
        }
    }

    private static Command parseDeleteCommand(String command) throws UniflowException {
        try {
            // Remove "delete"
            String args = command.substring(6).trim();

            if (!args.startsWith("i/")) {
                throw new UniflowException("Invalid format. Please use: delete i/<module_id>");
            }

            String moduleId = args.substring(2).trim();
            if (moduleId.isEmpty()) {
                throw new UniflowException("Missing module ID. Example: delete i/CS2110");
            }
            return new DeleteCommand(moduleId);
        } catch (StringIndexOutOfBoundsException e) {
            throw new UniflowException("Invalid delete command syntax. Use: delete n/<module_id>");
        }
    }
    private static Command parseScoreCommand(String command) throws UniflowException {
        String remainder = command.substring(COMMAND_SCORE.length()).trim();

        if (remainder.isEmpty()) {
            throw new UniflowException("Usage: score <MODULE_ID> name1:val1, name2:val2..");
        }

        String[] parts = remainder.split("\\s+", 2); //splits on any amount of whitespace - only splits once
        System.out.println("Parts: " + Arrays.toString(parts));
        String id = parts[0].trim();
        if (id.isEmpty()) {
            throw new UniflowException("Invalid Module ID");
        }

        String breakdown = (parts.length > 1) ? parts[1].trim() : "";
        System.out.println("Breakdown: " + Arrays.toString(breakdown.toCharArray()));
        if (breakdown.isEmpty()) {
            throw new UniflowException("Please provide score breakdown in a name:value format");
        }

        return new ScoreCommand(id, breakdown);
    }

    private static Command parseFilterCommand(String command) throws UniflowException {
        String trimmedCommand = command.substring(6).trim();

        if (trimmedCommand.isEmpty()) {
            throw new UniflowException(
                    "Filter command requires parameters. Usage: "
                            + "filter type/VALUE, filter hastutorial, filter notutorial, "
                            + "filter day/VALUE, filter id/VALUE, filter name/VALUE");
        }

        if (trimmedCommand.equalsIgnoreCase("hastutorial")) {
            return new FilterCommand("hastutorial", null);
        }
        if (trimmedCommand.equalsIgnoreCase("notutorial")) {
            return new FilterCommand("notutorial", null);
        }

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

    private static Command parseReviewCommand(String command) throws UniflowException {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            throw new UniflowException("Usage: review COURSE_NAME");
        }
        return new ReviewCommand(parts[1].trim(), Uniflow.getReviewManager());
    }

    private static Command parseAddReviewCommand(String command) throws UniflowException {
        String[] parts = command.substring(COMMAND_ADD_REVIEW.length()).trim().split(" ");
        String course = null;
        String user = null;
        String text = null;

        for (String part : parts) {
            if (part.startsWith("c/")) {
                course = part.substring(2);
            } else if (part.startsWith("u/")) {
                user = part.substring(2);
            } else if (part.startsWith("r/")) {
                text = part.substring(2);
            }
        }

        if (course == null || user == null || text == null) {
            throw new UniflowException("Usage: addreview c/COURSE u/USER r/REVIEW");
        }

        return new AddReviewCommand(
                course,
                user,
                text,
                Uniflow.getReviewManager()
        );
    }

    private static Command parseRateCommand(String command) throws UniflowException {
        String args = command.substring(COMMAND_RATE.length()).trim();

        if (args.isEmpty()) {
            throw new UniflowException("Usage: rate <MODULE_CODE> [RATING]");
        }

        String[] parts = args.split("\\s+");
        String moduleCode = parts[0].trim().toUpperCase();

        if (parts.length == 1) {
            return new RateCommand(moduleCode, RATING_QUERY_MODE);
        }

        if (parts.length == 2) {
            int score;
            try {
                score = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                throw new UniflowException("Rating must be a number between 1 and 5.");
            }

            if (score < 1 || score > 5) {
                throw new UniflowException("Rating must be between 1 and 5.");
            }
            return new RateCommand(moduleCode, score);
        }
        throw new  UniflowException("Usage: rate <MODULE_CODE> [RATING]");
    }
}
