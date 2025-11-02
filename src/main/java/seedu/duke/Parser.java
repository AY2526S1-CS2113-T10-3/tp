package seedu.duke;
import java.util.Set;
public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_INSERT = "insert";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SCORE = "score";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_ADD_GRADE = "addgrade";
    private static final String COMMAND_ADD_TEST_GRADE = "addtestgrade";
    private static final String COMMAND_GPA = "gpa";
    private static final String COMMAND_PROJECT_GPA = "projectgpa";
    private static final String COMMAND_FILTER = "filter";
    private static final String COMMAND_SHOW_TIMETABLE = "show timetable";
    private static final String COMMAND_RESET_TIMETABLE = "reset timetable";
    private static final String COMMAND_REVIEW = "review";
    private static final String COMMAND_ADD_REVIEW = "addreview";
    private static final String COMMAND_EDIT_REVIEW = "editreview";
    private static final String COMMAND_DELETE_REVIEW = "deletereview";
    private static final String COMMAND_RATE = "rate";
    private static final int RATING_QUERY_MODE = -1;
    private static final String SCORE_QUERY_MODE = "-1";
    private static final String PREFIX_CODE = "c/";
    private static final String PREFIX_CREDITS = "cr/";
    private static final String PREFIX_GRADE = "g/";
    private static final String PREFIX_MAJOR = "m/";


    private static final Set<String> VALID_DAYS = Set.of(
            "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"
    );

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
        if (trimmedCommand.startsWith(COMMAND_DELETE_REVIEW)) {
            return parseDeleteReviewCommand(trimmedCommand);
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
        if (trimmedCommand.startsWith(COMMAND_ADD_GRADE)) {
            return parseAddGradeCommand(trimmedCommand, true);
        }
        if (trimmedCommand.startsWith(COMMAND_ADD_TEST_GRADE)) {
            return parseAddGradeCommand(trimmedCommand, false);
        }
        if (trimmedCommand.equals(COMMAND_GPA)) {
            return new ComputeGpaCommand();
        }
        if (trimmedCommand.equals(COMMAND_PROJECT_GPA)) {
            return new ProjectGpaCommand();
        }
        if (trimmedCommand.equalsIgnoreCase(COMMAND_SHOW_TIMETABLE)) {
            return new ShowTimetableCommand();
        }
        if (trimmedCommand.equalsIgnoreCase(COMMAND_RESET_TIMETABLE)) {
            return new ResetTimetableCommand();
        }
        if (trimmedCommand.startsWith(COMMAND_EDIT_REVIEW)) {
            return parseEditReviewCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_ADD_REVIEW)) {
            return parseAddReviewCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_REVIEW)) {
            return parseReviewCommand(trimmedCommand);
        }
        if (trimmedCommand.startsWith(COMMAND_RATE)) {
            return parseRateCommand(trimmedCommand);
        }

        throw new UniflowException("Invalid command");
    }

    private static Command parseListCommand(String command) throws UniflowException {
        return new ListCommand();
    }

    private static Command parseAddGradeCommand(String command, boolean save) throws UniflowException {
        String[] parts;
        if (save) {
            parts = command.substring(COMMAND_ADD_GRADE.length()).trim().split(" ");
        } else {
            parts = command.substring(COMMAND_ADD_TEST_GRADE.length()).trim().split(" ");
        }

        if (parts.length != 4) {
            if (save) {
                throw new UniflowException("Please follow the format: addgrade c/COURSE_CODE"
                        + " cr/CREDITS g/GRADE m/ISMAJOR");
            } else {
                throw new UniflowException("Please follow the format: addtestgrade c/COURSE_CODE"
                        + " cr/CREDITS g/GRADE m/ISMAJOR");
            }
        }

        String code = null;
        int credits = -1;
        String grade = null;
        boolean isMajor = false;

        for (String part : parts) {
            if (part.startsWith(PREFIX_CODE)) {
                code = part.substring(PREFIX_CODE.length()).trim();
                if (code.isEmpty()) {
                    throw new UniflowException("Invalid input! At least one of the fields is empty.");
                }
            } else if (part.startsWith(PREFIX_CREDITS)) {
                try {
                    String creditsStr = part.substring(PREFIX_CREDITS.length()).trim();
                    if (creditsStr.isEmpty()) {
                        throw new UniflowException("Invalid input! At least one of the fields is empty.");
                    }
                    credits = Integer.parseInt(creditsStr);
                    if (credits <= 0) {
                        throw new UniflowException("Please enter a positive integer for credits.");
                    }
                } catch (NumberFormatException e) {
                    throw new UniflowException("Please enter a valid integer for credits.");
                }
            } else if (part.startsWith(PREFIX_GRADE)) {
                grade = part.substring(PREFIX_GRADE.length()).trim().toUpperCase();
                if (grade.isEmpty()) {
                    throw new UniflowException("Invalid input! At least one of the fields is empty.");
                }
                if (!isValidGrade(grade)) {
                    throw new UniflowException("Please enter a valid grade.");
                }
            } else if (part.startsWith(PREFIX_MAJOR)) { //for major course indication
                String isMajorStr = part.substring(PREFIX_MAJOR.length()).trim().toLowerCase();
                if (isMajorStr.isEmpty()) {
                    throw new UniflowException("Invalid input! At least one of the fields is empty.");
                }
                //handling the case where input other than true or false is entered
                //avoiding invalid strings to be parsed as false
                if (!isMajorStr.equals("true") && !isMajorStr.equals("false")) {
                    throw new UniflowException("Please enter 'true' or 'false' for major course indication");
                }
                isMajor = Boolean.parseBoolean(isMajorStr);
            } else  {
                throw new UniflowException("You have entered invalid components.");
            }
        }

        //checks if any components is missing
        if (code == null || grade == null || credits == -1) {
            if (save) {
                throw new UniflowException("Please follow the format: addgrade c/COURSE_CODE"
                        + " cr/CREDITS g/GRADE m/ISMAJOR");
            } else {
                throw new UniflowException("Please follow the format: addtestgrade c/COURSE_CODE"
                        + " cr/CREDITS g/GRADE m/ISMAJOR");
            }
        }

        if (save) {
            return new AddGradeCommand(code, credits, grade, isMajor);
        } else {
            return new AddTestGradeCommand(code, credits, grade, isMajor);
        }
    }

    private static boolean isValidGrade(String grade) {
        String[] validGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "D+", "D", "F"};
        for (String g : validGrades) {
            if (g.equals(grade)) {
                //input grade is equal to one of the valid grades
                return true;
            }
        }
        return false;
    }

    private static boolean isValidDay(String day) {
        return VALID_DAYS.contains(day.toUpperCase());
    }

    private static boolean isValidTime(String time) {
        // Expect format HH:MM (24-hour)
        if (time == null || !time.matches("\\d{2}:\\d{2}")) {
            return false;
        }
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }
    private static String getValue(String args, String prefix) {
        int start = args.indexOf(prefix);
        if (start == -1) {
            return null;
        }
        start += prefix.length();

        // Find next prefix (any of the known ones)
        int end = args.length();
        String[] prefixes = {"i/", "n/", "d/", "f/", "t/", "s/"};
        for (String p : prefixes) {
            if (p.equals(prefix)) {
                continue;
            }
            int idx = args.indexOf(p, start);
            if (idx != -1 && idx < end) {
                end = idx;
            }
        }

        return args.substring(start, end).trim();
    }

    private static Command parseInsertCommand(String command) throws UniflowException {
        try {
            if (command.length() <= COMMAND_INSERT.length()) {
                throw new UniflowException("Usage: insert i/<ID> n/<Name> d/<Day> f/<Start> t/<End> [s/<Type>]");
            }

            String args = command.substring(7).trim();
            String id = getValue(args, "i/");
            String moduleName = getValue(args, "n/");
            String day = getValue(args, "d/");
            String startTime = getValue(args, "f/");
            String endTime = getValue(args, "t/");
            String sessionType = getValue(args, "s/");

            if (id == null || moduleName == null || day == null || startTime == null || endTime == null) {
                throw new UniflowException("Missing fields in insert command.");
            }

            // Validate day
            if (!isValidDay(day)) {
                throw new UniflowException("Invalid day: " + day +
                        ". Expected one of MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY.");
            }

            // Validate time formats
            if (!isValidTime(startTime)) {
                throw new UniflowException("Invalid start time format: " + startTime +
                        ". Expected HH:MM in 24-hour format (e.g., 09:30).");
            }
            if (!isValidTime(endTime)) {
                throw new UniflowException("Invalid end time format: " + endTime +
                        ". Expected HH:MM in 24-hour format (e.g., 11:00).");
            }

            // Validate chronological order
            if (startTime.compareTo(endTime) >= 0) {
                throw new UniflowException("Start time must be earlier than end time.");
            }

            return new InsertCommand(id, moduleName, day, startTime, endTime, sessionType);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new UniflowException("Failed to parse insert command: " + e.getMessage());
        }
    }

    private static Command parseDeleteCommand(String command) throws UniflowException {
        try {
            if (command.length() <= COMMAND_DELETE.length()) {
                throw new UniflowException("Usage: delete index/<module_index>");
            }
            // Remove "delete"
            String args = command.substring(6).trim();

            if (!args.startsWith("index/")) {
                throw new UniflowException("Invalid format. Please use: delete index/<module_index>");
            }

            String moduleIdxStr = args.substring(6).trim();
            if (moduleIdxStr.isEmpty()) {
                throw new UniflowException("Missing module ID. Example: delete index/2");
            }

            int moduleIndex;
            try {
                moduleIndex = Integer.parseInt(moduleIdxStr);
            } catch (NumberFormatException e) {
                throw new UniflowException("Invalid module index: " + moduleIdxStr +
                        ". Please enter a valid number, e.g. delete index/2");
            }

            return new DeleteCommand(moduleIndex);
        } catch (StringIndexOutOfBoundsException e) {
            throw new UniflowException("Invalid delete command syntax. Use: delete n/<module_id>");
        }
    }

    private static Command parseScoreCommand(String command) throws UniflowException {
        String remainder = command.substring(COMMAND_SCORE.length()).trim();

        if (remainder.isEmpty()) {
            throw new UniflowException("Usage: score <MODULE_ID> name1:val1 name2:val2 ...");
        }

        String[] parts = remainder.split("\\s+", 2);
        String id = parts[0].trim();
        if (id.isEmpty()) {
            throw new UniflowException("Invalid module ID");
        }

        String normId = id.toUpperCase();

        if (parts.length == 1) {
            return new ScoreCommand(normId, SCORE_QUERY_MODE);
        }

        String breakdown = parts[1].trim();
        if (breakdown.isEmpty()) {
            throw new UniflowException("Please provide score breakdown in name:value format");
        }

        if (SCORE_QUERY_MODE.equals(breakdown)) {
            return new ScoreCommand(normId, SCORE_QUERY_MODE);
        }

        String normalized = breakdown.replace(',', ' ')
                .replaceAll("\\s+", " ")
                .trim();

        if (!normalized.contains(":")) {
            throw new UniflowException("Invalid format. Use name:value pairs, e.g., exam:50 project:30");
        }

        return new ScoreCommand(normId, normalized);
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
        String input = command.substring(COMMAND_ADD_REVIEW.length()).trim();

        String course = extractParameter(input, "c/");
        String user = extractParameter(input, "u/");
        String text = extractParameter(input, "r/");

        if (course == null || user == null || text == null) {
            throw new UniflowException("Usage: addreview c/COURSE u/USER r/REVIEW");
        }

        return new AddReviewCommand(course, user, text, Uniflow.getReviewManager());
    }

    private static Command parseEditReviewCommand(String command) throws UniflowException {
        String input = command.substring(COMMAND_EDIT_REVIEW.length()).trim();

        String course = extractParameter(input, "c/");
        String user = extractParameter(input, "u/");
        String newText = extractParameter(input, "r/");

        if (course == null || user == null || newText == null) {
            throw new UniflowException("Usage: editreview c/COURSE u/USER r/NEW_REVIEW");
        }

        return new EditReviewCommand(course, user, newText, Uniflow.getReviewManager());
    }

    private static Command parseDeleteReviewCommand(String command) throws UniflowException {
        String input = command.substring(COMMAND_DELETE_REVIEW.length()).trim();

        String course = extractParameter(input, "c/");
        String user = extractParameter(input, "u/");

        if (course == null || user == null) {
            throw new UniflowException("Usage: deletereview c/COURSE u/USER");
        }

        return new DeleteReviewCommand(course, user, Uniflow.getReviewManager());
    }

    private static String extractParameter(String input, String prefix) {
        int startIdx = input.indexOf(prefix);
        if (startIdx == -1) {
            return null;
        }

        startIdx += prefix.length(); // Move past the prefix

        // Find the next prefix (c/, u/, or r/)
        int endIdx = input.length();
        int nextC = input.indexOf(" c/", startIdx);
        int nextU = input.indexOf(" u/", startIdx);
        int nextR = input.indexOf(" r/", startIdx);

        if (nextC != -1 && nextC < endIdx) {
            endIdx = nextC;
        }
        if (nextU != -1 && nextU < endIdx) {
            endIdx = nextU;
        }
        if (nextR != -1 && nextR < endIdx) {
            endIdx = nextR;
        }

        return input.substring(startIdx, endIdx).trim();
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
