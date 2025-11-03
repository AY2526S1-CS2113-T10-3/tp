package seedu.duke;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handles adding, editing, deleting, and retrieving course reviews.
 * Reviews are stored and persisted through the ReviewStorage class.
 */
public class ReviewManager {

    private final Map<String, List<String>> reviews;
    private final ReviewStorage storage;

    /**
     * Initializes the ReviewManager by loading stored reviews from file or storing data on RAM-only mode.
     */
    public ReviewManager() {
        storage = new ReviewStorage();
        Map<String, List<String>> loaded;

        try {
            loaded = storage.load();
        } catch (Exception e) {
            System.out.println("Warning: Review file could not be loaded. Running in RAM-only mode.");
            loaded = new java.util.HashMap<>();
        }

        reviews = loaded;
    }


    /**
     * Adds a review for a specific course by a user.
     * Allows multiple reviews from the same user.
     *
     * @param course the course code
     * @param user   the username of the reviewer
     * @param text   the review text
     */
    public void addReview(String course, String user, String text) {
        reviews.computeIfAbsent(course, k -> new ArrayList<>()).add(user + ": " + text);
    }

    /**
     * Retrieves all reviews for a specific course.
     *
     * @param course the course code
     * @return a list of reviews or an empty list if none exist
     */
    public List<String> getReviews(String course) {
        return reviews.getOrDefault(course, new ArrayList<>());
    }

    /**
     * Checks if a course exists in the review list.
     *
     * @param course the course code
     * @return true if the course exists, false otherwise
     */
    public boolean hasCourse(String course) {
        return reviews.containsKey(course);
    }

    /**
     * Edits a specific review from a user for a specific course.
     *
     * @param course  the course code
     * @param user    the username of the reviewer
     * @param newText the new review text
     * @param index   the index of the review to edit (0-based)
     * @return true if the review was found and edited, false otherwise
     */
    public boolean editReview(String course, String user, String newText, int index) {
        if (!reviews.containsKey(course)) {
            return false;
        }

        List<String> courseReviews = reviews.get(course);
        List<Integer> userReviewIndices = new ArrayList<>();

        // Find all reviews from this user
        for (int i = 0; i < courseReviews.size(); i++) {
            if (courseReviews.get(i).startsWith(user + ": ")) {
                userReviewIndices.add(i);
            }
        }

        if (index < 0 || index >= userReviewIndices.size()) {
            return false;
        }

        int actualIndex = userReviewIndices.get(index);
        courseReviews.set(actualIndex, user + ": " + newText);
        return true;
    }

    /**
     * Checks if a user has already posted a review for a specific course.
     *
     * @param course the course code
     * @param user   the username
     * @return true if the user has a review for the course, false otherwise
     */
    public boolean hasUserReview(String course, String user) {
        if (!reviews.containsKey(course)) {
            return false;
        }

        List<String> courseReviews = reviews.get(course);
        for (String review : courseReviews) {
            if (review.startsWith(user + ": ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a specific review from a user for a specific course.
     *
     * @param course the course code
     * @param user   the username
     * @param index  the index of the review to delete (0-based)
     * @return true if the review was found and deleted, false otherwise
     */
    public boolean deleteReview(String course, String user, int index) {
        if (!reviews.containsKey(course)) {
            return false;
        }

        List<String> courseReviews = reviews.get(course);
        List<Integer> userReviewIndices = new ArrayList<>();

        // Find all reviews from this user
        for (int i = 0; i < courseReviews.size(); i++) {
            if (courseReviews.get(i).startsWith(user + ": ")) {
                userReviewIndices.add(i);
            }
        }

        if (index < 0 || index >= userReviewIndices.size()) {
            return false;
        }

        int actualIndex = userReviewIndices.get(index);
        courseReviews.remove(actualIndex);

        // Remove course entry if no reviews left
        if (courseReviews.isEmpty()) {
            reviews.remove(course);
        }

        return true;
    }

    /**
     * Returns the set of all course IDs that have at least one review.
     */
    public Set<String> getAllCourseIds() {
        return reviews.keySet();
    }

    /**
     * Reloads all reviews from the storage file into memory.
     * This can be used to refresh data if the file was modified externally.
     */
    public void loadReviews() {
        Map<String, List<String>> reloaded = storage.load();
        reviews.clear();
        reviews.putAll(reloaded);
    }

    /**
     * Clean all reviews locally from RAM memory.
     */
    public void clearAll() {
        reviews.clear();
    }

    /**
     * Synchronizes the in-memory reviews with those in the file,
     * adding only missing entries from the file without removing anything.
     */
    public boolean mergeWithFile() {
        Map<String, List<String>> fileData = storage.load();
        int added = 0;

        for (Map.Entry<String, List<String>> entry : fileData.entrySet()) {
            String course = entry.getKey();
            List<String> fileReviews = entry.getValue();

            reviews.computeIfAbsent(course, k -> new ArrayList<>());

            for (String review : fileReviews) {
                if (!reviews.get(course).contains(review)) {
                    reviews.get(course).add(review);
                    added++;
                }
            }
        }

        return added > 0;
    }

    /**
     * Returns all reviews currently stored in memory.
     */
    public Map<String, List<String>> getAllReviews() {
        return reviews;
    }
}
