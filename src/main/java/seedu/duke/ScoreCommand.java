package seedu.duke;

import java.util.HashMap;
import java.util.Map;

/**
 * Command to add or view a score breakdown for a module.
 * Usage:
 *   score <MODULE_ID>              -> view existing breakdown
 *   score <MODULE_ID> k1:v1 k2:v2  -> set/replace breakdown
 */
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
        if (courseID == null || courseID.trim().isEmpty()) {
            throw new UniflowException("Invalid module ID");
        }

        if (!modules.doesExist(courseID)) {
            throw new UniflowException("Module does not exist");
        }
        Module module = modules.getModuleByID(courseID);
        if (module == null) {
            throw new UniflowException("Module does not exist");
        }

        if (breakdown == null || breakdown.trim().isEmpty()) {
            throw new UniflowException(
                    "Please provide scores in name:value format, e.g; participation:10 exam:50..."
            );
        }

        if (SCORE_QUERY_MODE.equals(breakdown)) {
            if (module.hasBreakdown()) {
                ui.showMessage("Score breakdown for " + courseID + ": " + module.getScoreBreakdown());
            } else {
                ui.showMessage("Score not found for " + courseID);
            }
            return;
        }

        Map<String, Integer> map = new HashMap<>();
        String[] pairs = breakdown.trim().split("\\s+");
        for (String pair : pairs) {
            if (pair.isEmpty()) {
                continue;
            }

            String[] parts = pair.split(":", 2);
            if (parts.length != 2) {
                throw new UniflowException(
                        "Invalid format. Use name:value pairs separated by spaces, e.g., exam:50 project:30"
                );
            }

            String name = parts[0].trim();
            String valueStr = parts[1].trim();

            if (name.isEmpty() || valueStr.isEmpty()) {
                throw new UniflowException("Invalid format. Component name and value must be non-empty (e.g., exam:50).");
            }

            final int value;
            try {
                value = Integer.parseInt(valueStr);
            } catch (NumberFormatException e) {
                throw new UniflowException("Please provide numeric values. Example: exam:50 participation:10");
            }

            if (value < 0) {
                throw new UniflowException("Value must be a positive integer");
            }

            map.put(name, value);
        }

        module.setScoreBreakdown(map);
        ui.showMessage("Saved score breakdown for {" + courseID + ":" + map + "}");
    }
}
