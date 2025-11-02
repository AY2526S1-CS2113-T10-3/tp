package seedu.duke;

public class EditReviewCommand extends Command {
    private final String course;
    private final String user;
    private final String newText;
    private final ReviewManager reviewManager;

    public EditReviewCommand(String course, String user, String newText, ReviewManager reviewManager) {
        this.course = course;
        this.user = user;
        this.newText = newText;
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (!reviewManager.hasUserReview(course, user)) {
            throw new UniflowException("You have not posted a review for " + course + " yet.");
        }

        boolean success = reviewManager.editReview(course, user, newText);
        if (success) {
            ui.showMessage("Review updated for " + course + ".");
        } else {
            throw new UniflowException("Failed to edit review for " + course + ".");
        }
    }
}
