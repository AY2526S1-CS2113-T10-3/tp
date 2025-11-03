package seedu.duke;

import java.util.List;
import java.util.Map;

public class LoadReviewsCommand extends Command {

    private final ReviewManager reviewManager;

    public LoadReviewsCommand(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        ui.showMessage("Reloading all reviews from file...");

        ReviewStorage storage = new ReviewStorage();
        Map<String, List<String>> fileData = storage.load();
        Map<String, List<String>> memData = reviewManager.getAllReviews();
        if (detectUnsavedReviews(fileData, memData)) {
            boolean confirmed = askToAddReviews(ui);
            if (confirmed) {
                int added = mergeAndSaveReviews(fileData, memData, storage);
                ui.showMessage("Added " + added + " new review(s) to the database.");
            } else {
                ui.showMessage("Skipped adding new reviews to database.");
            }
        }

        reviewManager.loadReviews();
        ui.showMessage("All reviews successfully reloaded from file. (Memory now matches file)");
    }
    /**
     * Detects if there are reviews in memory that are not present in the file.
     */
    private boolean detectUnsavedReviews(Map<String, List<String>> fileData, Map<String, List<String>> memData) {
        for (Map.Entry<String, List<String>> entry : memData.entrySet()) {
            String course = entry.getKey();
            List<String> memReviews = entry.getValue();
            List<String> fileReviews = fileData.get(course);

            if (fileReviews == null) {
                return true;
            }

            for (String review : memReviews) {
                if (!fileReviews.contains(review)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Asks the user whether to add missing reviews to the file.
     */
    private boolean askToAddReviews(UI ui) {
        ui.showMessage("There are unsaved reviews in memory. Do you want to add them to the database? (yes/no)");
        String response = ui.readCommand().trim().toLowerCase();
        return response.equals("yes");
    }

    /**
     * Merges and saves missing reviews from memory into the file.
     */
    private int mergeAndSaveReviews(Map<String, List<String>> fileData,
                                    Map<String, List<String>> memData,
                                    ReviewStorage storage) {
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
        }

        return added;
    }
}
