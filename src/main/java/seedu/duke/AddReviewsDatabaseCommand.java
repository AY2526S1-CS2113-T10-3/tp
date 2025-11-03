package seedu.duke;

import java.util.List;
import java.util.Map;

/**
 * Command to permanently add all in-memory reviews to the database file,
 * without removing or overwriting any existing ones.
 */
public class AddReviewsDatabaseCommand extends Command {

    private final ReviewManager reviewManager;

    public AddReviewsDatabaseCommand(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        ui.showMessage("Synchronizing in-memory reviews with database...");

        ReviewStorage storage = new ReviewStorage();
        Map<String, List<String>> fileData = storage.load();
        Map<String, List<String>> memData = reviewManager.getAllReviews();

        int added = 0;

        for (Map.Entry<String, List<String>> entry : memData.entrySet()) {
            String course = entry.getKey();
            List<String> memReviews = entry.getValue();
            fileData.computeIfAbsent(course, k -> new java.util.ArrayList<>());

            for (String review : memReviews) {
                if (!fileData.get(course).contains(review)) {
                    fileData.get(course).add(review);
                    added++;
                }
            }
        }

        if (added > 0) {
            storage.save(fileData);
            ui.showMessage("Added " + added + " new review(s) to the database.");
        } else {
            ui.showMessage("No new reviews found to add.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
