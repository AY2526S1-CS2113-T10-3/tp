package seedu.duke;

public class DeleteReviewCommand extends Command {
    private final String course;
    private final String user;
    private final ReviewManager reviewManager;

    public DeleteReviewCommand(String course, String user, ReviewManager reviewManager) {
        this.course = course;
        this.user = user;
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (!reviewManager.hasUserReview(course, user)) {
            throw new UniflowException("You have not posted a review for " + course + " yet.");
        }

        boolean success = reviewManager.deleteReview(course, user);
        if (success) {
            ui.showMessage("Review deleted for " + course + ".");
        } else {
            throw new UniflowException("Failed to delete review for " + course + ".");
        }
    }
}
