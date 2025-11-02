package seedu.duke;

public class ReviewSyncManager {
    private final ReviewManager reviewManager;

    public ReviewSyncManager(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    public void saveAndClean() {
        try {
            reviewManager.flush();
            new ReviewCleaner().cleanInvalidReviews();
            System.out.println("Reviews synced and cleaned.");
        } catch (Exception e) {
            System.out.println("Warning: failed to save reviews: " + e.getMessage());
        }
    }

    public void setupAutoSync() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveAndClean));
    }
}
