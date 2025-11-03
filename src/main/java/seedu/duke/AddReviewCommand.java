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
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        // Validate inputs
        if (course == null || course.trim().isEmpty()) {
            throw new UniflowException("Course code cannot be empty.");
        }
        if (user == null || user.trim().isEmpty()) {
            throw new UniflowException("User name cannot be empty.");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new UniflowException("Review text cannot be empty.");
        }

        reviewManager.addReview(course, user, text);
        ui.showMessage("Review added for " + course + ".");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
