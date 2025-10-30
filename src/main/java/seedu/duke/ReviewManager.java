package seedu.duke;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewManager {
    private final Map<String, List<String>> reviews;
    private final ReviewStorage storage;

    public ReviewManager() {
        storage = new ReviewStorage();
        reviews = storage.load();
    }

    public void addReview(String course, String user, String text) {
        reviews.computeIfAbsent(course, k -> new ArrayList<>()).add(user + ": " + text);
        storage.save(reviews);
    }

    public List<String> getReviews(String course) {
        return reviews.get(course);
    }

    public boolean hasCourse(String course) {
        return reviews.containsKey(course);
    }

    /**
     * Edits a user's review for a specific course.
     *
     * @param course the course code
     * @param user the username
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
     * Checks if a user has already posted a review for a course.
     *
     * @param course the course code
     * @param user the username
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
}
