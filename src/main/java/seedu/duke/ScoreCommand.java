package seedu.duke;

import java.util.HashMap;
import java.util.Map;

/**
 * Command to add or view a score breakdown for a module.
 * Usage:
 *   score MODULE_ID              -> view existing breakdown
 *   score MODULE_ID k1:v1 k2:v2  -> set/replace breakdown
 */
public class ScoreCommand extends Command {
    private static final String SCORE_QUERY_MODE = "-1";

    private final String courseID;
    private final String breakdown;

    public ScoreCommand(String courseID, String breakdown) {
        this.courseID = courseID == null ? null : courseID.trim().toUpperCase();
        this.breakdown = breakdown;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (courseID == null || courseID.isEmpty()) {
            throw new UniflowException("Invalid Module ID");
        }
        if (!modules.doesExist(courseID)) {
            throw new UniflowException("Module does not exist");
        }

        final Module module = modules.getModuleByID(courseID);
        if (module == null) {
            throw new UniflowException("Module does not exist");
        }

        if (breakdown == null || breakdown.trim().isEmpty()) {
            throw new UniflowException(
                    "Please provide scores in name:value format, e.g., participation:10 exam:50..."
            );
        }

        final ScoreManager sm = Uniflow.getScoreManager();

        if (SCORE_QUERY_MODE.equals(breakdown)) {
            if (module.hasBreakdown()) {
                ui.showMessage("Score breakdown for " + courseID + ": " + module.getScoreBreakdown());
                return;
            }
            if (sm.hasBreakdown(courseID)) {
                Map<String, Integer> persisted = sm.getBreakdown(courseID);
                module.setScoreBreakdown(new HashMap<>(persisted));
                ui.showMessage("Score breakdown for " + courseID + ": " + persisted);
            } else {
                ui.showMessage("Score not found for " + courseID);
            }
            return;
        }

        Map<String, Integer> map = parseBreakdown(breakdown);

        module.setScoreBreakdown(map);
        sm.saveBreakdown(courseID, map);

        ui.showMessage("Saved score breakdown for {" + courseID + ":" + map + "}");
    }

    private static Map<String, Integer> parseBreakdown(String breakdown) throws UniflowException {
        Map<String, Integer> map = new HashMap<>();

        String normalized = breakdown.trim().replace(',', ' ').replaceAll("\\s+", " ");
        if (normalized.isEmpty()) {
            throw new UniflowException("Please provide score breakdown in a name:value format");
        }

        String[] pairs = normalized.split("\\s+");
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
            int value = parseValue(parts[1].trim(), name);
            map.put(name, value);
        }
        return map;
    }

    private static int parseValue(String valueStr, String name) throws UniflowException {
        if (name == null || name.isEmpty() || valueStr == null || valueStr.isEmpty()) {
            throw new UniflowException(
                    "Invalid format. Component name and value must be non-empty (e.g., exam:50)."
            );
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
        return value;
    }
}
