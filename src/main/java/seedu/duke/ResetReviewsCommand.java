package seedu.duke;

public class ResetReviewsCommand extends Command {

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        try {
            Uniflow.getReviewManager().clearAll();

            ui.showMessage("All reviews have been cleared from memory.");
        } catch (Exception e) {
            ui.showError("Failed to clear in-memory reviews: " + e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
