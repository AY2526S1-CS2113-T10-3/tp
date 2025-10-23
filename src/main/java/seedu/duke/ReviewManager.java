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
}
