package seedu.duke;

public class AddReviewCommand extends Command {
    private final String course;
    private final String user;
    private final String text;
    private final ReviewManager reviewManager;

    public AddReviewCommand(String course, String user, String text, ReviewManager reviewManager) {
        this.course = course;
        this.user = user;
        this.text = text;
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        reviewManager.addReview(course, user, text);
        ReviewFileManager.saveAllReviews(reviewManager);
        ui.showMessage("Review added for " + course + ".");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
