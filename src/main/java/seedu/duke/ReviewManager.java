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
     * Initializes the ReviewManager by loading stored reviews from file.
     */
    public ReviewManager() {
        storage = new ReviewStorage();
        reviews = storage.load();
    }

    /**
     * Adds a review for a specific course by a user.
     *
     * @param course the course code
     * @param user   the username of the reviewer
     * @param text   the review text
     */
    public void addReview(String course, String user, String text) {
        reviews.computeIfAbsent(course, k -> new ArrayList<>()).add(user + ": " + text);
        storage.save(reviews);
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
     * Edits a user's review for a specific course.
     *
     * @param course  the course code
     * @param user    the username of the reviewer
     * @param newText the new review text
     * @return true if the review was found and edited, false otherwise
     */
    public boolean editReview(String course, String user, String newText) {
        if (!reviews.containsKey(course)) {
            return false;
        }

        List<String> courseReviews = reviews.get(course);
        for (int i = 0; i < courseReviews.size(); i++) {
            String review = courseReviews.get(i);
            if (review.startsWith(user + ": ")) {
                courseReviews.set(i, user + ": " + newText);
                storage.save(reviews);
                return true;
            }
        }
        return false;
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
     * Deletes a user's review for a specific course.
     *
     * @param course the course code
     * @param user   the username
     * @return true if the review was found and deleted, false otherwise
     */
    public boolean deleteReview(String course, String user) {
        if (!reviews.containsKey(course)) {
            return false;
        }

        List<String> courseReviews = reviews.get(course);
        for (int i = 0; i < courseReviews.size(); i++) {
            String review = courseReviews.get(i);
            if (review.startsWith(user + ": ")) {
                courseReviews.remove(i);
                storage.save(reviews);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the set of all course IDs that have at least one review.
     */
    public Set<String> getAllCourseIds() {
        return reviews.keySet();
    }
}
