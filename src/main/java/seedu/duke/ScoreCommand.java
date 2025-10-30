package seedu.duke;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a command that allows users to record or view
 * score breakdowns for a specific course.
 * <p>
 * The command accepts input in the format:
 * {@code score <COURSE_ID> exam:50 project:30 participation:20}
 * and stores the component scores as a map within the corresponding course.
 * </p>
 * <p>
 * If the breakdown input equals {@code -1}, the command retrieves and
 * displays the existing score breakdown for the specified course instead.
 * </p>
 */
public class ScoreCommand extends Command {
    private static final String SCORE_QUERY_MODE = "-1";
    private final String courseID;
    private final String breakdown;

    /**
     * Constructs a {@code ScoreCommand} with the specified course ID and score breakdown.
     *
     * @param courseID The ID of the course to add or retrieve scores for.
     * @param breakdown The score breakdown string (e.g. "exam:50 project:30").
     */
    public ScoreCommand(String courseID, String breakdown) {
        this.courseID = courseID;
        this.breakdown = breakdown;
    }

    /**
     * Executes the score command.
     * <p>
     * This method either:
     * <ul>
     *     <li>Displays the current score breakdown for a course (if in query mode), or</li>
     *     <li>Parses the provided breakdown string, validates it, and stores the scores.</li>
     * </ul>
     * </p>
     *
     * @param ui The user interface used to display messages.
     * @param modules The list of modules (not used directly by this command).
     * @param courseRecord The record containing all completed courses.
     * @throws UniflowException If the course does not exist, input is invalid,
     *                          or breakdown values are non-numeric or negative.
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {

        if (!courseRecord.hasCourse(courseID)) {
            throw new UniflowException("Module does not exist");
        }
        Course course =  courseRecord.getCourse(courseID);

        if (breakdown == null || breakdown.trim().isEmpty()) {
            throw new UniflowException(
                "Please provide scores in name:value format, e.g; participation:10 exam:50..."
            );
        }

        //query mode
        if (breakdown.equals(SCORE_QUERY_MODE)) {
            if (course.hasBreakdown()) {
                Map<String, Integer> scores = course.getScoreBreakdown();
                ui.showMessage("Score breakdown for " +  courseID + ": " + scores);
            } else {
                ui.showMessage("Score not found for " +  courseID);
            }
            return;
        }

        Map<String, Integer> map = new HashMap<>();
        String[] pairs = breakdown.split(" ");
        for (String pair: pairs) {
            String name = pair.split(":")[0]; //"participation"
            String valueStr = pair.split(":")[1]; //"20"

            int value;
            try {
                value = Integer.parseInt(valueStr);
            } catch (NumberFormatException e) {
                throw new UniflowException(
                    "Please provide breakdown in the following format: exam:50 participation:10 ..."
                );
            }
            if (value < 0) {
                throw new UniflowException("Value must be a positive integer");
            }
            map.put(name, value);
        }
        course.setScoreBreakdown(map);
        ui.showMessage("Saved score breakdown for {" + courseID + ":" + map + "}");
    }

}
