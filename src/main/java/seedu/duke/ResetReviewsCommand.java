package seedu.duke;

public class ResetReviewsCommand extends Command {

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        ReviewFileManager.resetToDefault();
        ui.showMessage("All reviews have been reset to default.");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
