package seedu.duke;

import java.util.HashMap;
import java.util.Map;

public class ScoreCommand extends Command {
    private static final String SCORE_QUERY_MODE = "-1";
    private final String courseID;
    private final String breakdown;

    public ScoreCommand(String courseID, String breakdown) {
        this.courseID = courseID;
        this.breakdown = breakdown;
    }

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
