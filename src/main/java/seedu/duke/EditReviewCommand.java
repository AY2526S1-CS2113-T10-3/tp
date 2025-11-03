package seedu.duke;

import java.util.ArrayList;
import java.util.List;

public class EditReviewCommand extends Command {
    private final String course;
    private final String user;
    private final String newText;
    private final Integer reviewIndex;
    private final ReviewManager reviewManager;

    public EditReviewCommand(String course, String user, String newText, Integer reviewIndex,
                             ReviewManager reviewManager) {
        this.course = course;
        this.user = user;
        this.newText = newText;
        this.reviewIndex = reviewIndex;
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (!reviewManager.hasUserReview(course, user)) {
            throw new UniflowException("You have not posted a review for " + course + " yet.");
        }

        // Get all reviews from this user for this course
        List<String> userReviews = getUserReviews(course, user);

        if (userReviews.isEmpty()) {
            throw new UniflowException("No reviews found for user " + user + " in course " + course + ".");
        }

        if (userReviews.size() == 1) {
            // Only one review, edit it directly
            boolean success = reviewManager.editReview(course, user, newText, 0);
            if (success) {
                reviewManager.saveReviews(); // Save after editing
                ui.showMessage("Review updated for " + course + ".");
            } else {
                throw new UniflowException("Failed to edit review for " + course + ".");
            }
        } else {
            // Multiple reviews
            if (reviewIndex == null) {
                // No index provided, show all reviews and ask user to retry with index
                StringBuilder message = new StringBuilder();
                message.append("You have multiple reviews for ").append(course).append(":\n");
                for (int i = 0; i < userReviews.size(); i++) {
                    message.append(" ").append(i + 1).append(". ").append(userReviews.get(i)).append("\n");
                }
                message.append(" Please specify which review to edit using: editreview c/")
                        .append(course).append(" u/").append(user)
                        .append(" r/NEW_TEXT i/INDEX");
                ui.showMessage(message.toString());
            } else {
                // Index provided, validate and edit
                if (reviewIndex < 1 || reviewIndex > userReviews.size()) {
                    throw new UniflowException("Invalid index. Please enter a number between 1 and "
                            + userReviews.size());
                }

                boolean success = reviewManager.editReview(course, user, newText, reviewIndex - 1);
                if (success) {
                    reviewManager.saveReviews(); // Save after editing
                    ui.showMessage("Review updated for " + course + ".");
                } else {
                    throw new UniflowException("Failed to edit review for " + course + ".");
                }
            }
        }
    }

    private List<String> getUserReviews(String course, String user) {
        List<String> allReviews = reviewManager.getReviews(course);
        List<String> userReviews = new ArrayList<>();

        if (allReviews != null) {
            for (String review : allReviews) {
                if (review.startsWith(user + ": ")) {
                    userReviews.add(review.substring(user.length() + 2));
                }
            }
        }

        return userReviews;
    }
}
