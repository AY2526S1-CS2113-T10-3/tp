package seedu.duke;

import java.util.HashMap;
import java.util.Map;

public class ScoreCommand extends Command {
    private final String moduleID;
    private final String breakdown;

    public ScoreCommand(String moduleId, String breakdown) {
        this.moduleID = moduleId;
        this.breakdown = breakdown;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        
        if (!modules.doesExist(moduleID)) {
            throw new UniflowException("Module does not exist");
        }
        if (breakdown == null || breakdown.trim().isEmpty()) {
            throw new UniflowException(
                "Please provide scores in name:value format, e.g; participation:10 exam:50..."
            );
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

        Module module = modules.getModuleByID(moduleID);
        module.setScoreBreakdown(map);

        ui.showMessage("Saved score breakdown for {" + moduleID + ":" + map + "}");
    }

}
