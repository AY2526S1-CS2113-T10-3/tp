package seedu.duke;

import java.util.List;
import java.util.Map;

/**
 * Handles checking and prompting the user to save unsaved reviews before exiting.
 */
public class ExitSaveManager {

    private final UI ui;
    private final ReviewManager reviewManager;

    public ExitSaveManager(UI ui, ReviewManager reviewManager) {
        this.ui = ui;
        this.reviewManager = reviewManager;
    }

    /**
     * Before exit, checks if in-memory reviews differ from the file.
     * If asks the user whether to save them permanently.
     */
    public void promptSaveBeforeExit() {
        ReviewStorage storage = new ReviewStorage();
        Map<String, List<String>> fileData = storage.load();
        Map<String, List<String>> memData = reviewManager.getAllReviews();

        LoadReviewsCommand helper = new LoadReviewsCommand(reviewManager);

        try {
            java.lang.reflect.Method detectMethod = LoadReviewsCommand.class
                    .getDeclaredMethod("detectUnsavedReviews", Map.class, Map.class);
            detectMethod.setAccessible(true);
            boolean hasNew = (boolean) detectMethod.invoke(helper, fileData, memData);

            if (!hasNew) {
                return;
            }

            ui.showMessage("You have unsaved reviews in memory. Do you want to save them before exiting? (yes/no)");
            String response = ui.readCommand().trim().toLowerCase();
            if (!response.equals("yes")) {
                ui.showMessage("Skipped saving reviews.");
                return;
            }

            java.lang.reflect.Method mergeMethod = LoadReviewsCommand.class
                    .getDeclaredMethod("mergeAndSaveReviews", Map.class, Map.class, ReviewStorage.class);
            mergeMethod.setAccessible(true);
            int added = (int) mergeMethod.invoke(helper, fileData, memData, storage);

            if (added > 0) {
                ui.showMessage("Saved " + added + " new review(s) before exit.");
            } else {
                ui.showMessage("No new reviews to save.");
            }

        } catch (Exception e) {
            ui.showMessage("Error during exit save: " + e.getMessage());
        }
    }
}
