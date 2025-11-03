package seedu.duke;

import java.util.ArrayList;
import java.util.List;

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

        // Get all reviews from this user for this course
        List<String> userReviews = getUserReviews(course, user);

        if (userReviews.isEmpty()) {
            throw new UniflowException("No reviews found for user " + user + " in course " + course + ".");
        }

        if (userReviews.size() == 1) {
            // Only one review, edit it directly
            boolean success = reviewManager.editReview(course, user, newText, 0);
            if (success) {
                ui.showMessage("Review updated for " + course + ".");
            } else {
                throw new UniflowException("Failed to edit review for " + course + ".");
            }
        } else {
            // Multiple reviews, let user choose
            ui.showMessage("You have multiple reviews for " + course + ". Please choose which one to edit:");
            for (int i = 0; i < userReviews.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + userReviews.get(i));
            }

            ui.showMessage("Enter the number of the review to edit:");
            String input = ui.readCommand().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > userReviews.size()) {
                    throw new UniflowException("Invalid choice. Please enter a number between 1 and " + userReviews.size());
                }

                boolean success = reviewManager.editReview(course, user, newText, choice - 1);
                if (success) {
                    ui.showMessage("Review updated for " + course + ".");
                } else {
                    throw new UniflowException("Failed to edit review for " + course + ".");
                }
            } catch (NumberFormatException e) {
                throw new UniflowException("Invalid input. Please enter a valid number.");
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
