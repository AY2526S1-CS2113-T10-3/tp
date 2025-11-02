package seedu.duke;

/**
 * Reloads all reviews from file into memory.
 */
public class LoadReviewsCommand extends Command {

    private final ReviewManager reviewManager;

    public LoadReviewsCommand(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        reviewManager.loadReviews();
        ui.showMessage("Reviews reloaded from file.");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
