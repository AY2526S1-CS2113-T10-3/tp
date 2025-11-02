package seedu.duke;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Finds reviews written by a specific user.
 * Can search in one course or in all courses.
 */
public class FindReview extends Command {
    private final String user;
    private final String courseOrNull;
    private final ReviewManager reviewManager;

    /**
     * Creates a FindReview command with a username and optional course.
     *
     * @param user the username to search for
     * @param courseOrNull the course code to search in, or null for all
     * @param reviewManager the review manager used to access reviews
     */
    public FindReview(String user, String courseOrNull, ReviewManager reviewManager) {
        this.user = user;
        this.courseOrNull = courseOrNull;
        this.reviewManager = reviewManager;
    }

    /**
     * Creates a FindReview command with only a review manager.
     *
     * @param reviewManager the review manager used to access reviews
     */
    public FindReview(ReviewManager reviewManager) {
        this.user = "";
        this.courseOrNull = null;
        this.reviewManager = reviewManager;
    }

    /**
     * Executes the command to find reviews by the given user.
     * If a course is given, it searches only that course.
     * Otherwise, it searches all courses.
     *
     * @param ui the UI to display messages
     * @param modules the module list
     * @param courseRecord the course record
     * @throws UniflowException if an error occurs during execution
     */
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        String targetUser = user.trim();
        if (targetUser.isEmpty()) {
            ui.showMessage("Please provide a username. Usage: findreview u/USER [c/COURSE]");
            return;
        }

        List<String> results = new ArrayList<>();

        if (courseOrNull != null) {
            List<String> list = reviewManager.getReviews(courseOrNull);
            if (list != null) {
                for (String r : list) {
                    if (r.startsWith(targetUser + ": ")) {
                        results.add(courseOrNull + " - " + r);
                    }
                }
            }
        } else {
            Set<String> allCourses = reviewManager.getAllCourseIds();
            for (String c : allCourses) {
                List<String> list = reviewManager.getReviews(c);
                if (list == null) {
                    continue;
                }
                for (String r : list) {
                    if (r.startsWith(targetUser + ": ")) {
                        results.add(c + " - " + r);
                    }
                }
            }
        }

        if (results.isEmpty()) {
            ui.showMessage("No reviews found for user: " + targetUser
                    + (courseOrNull != null ? " in " + courseOrNull : ""));
            return;
        }

        ui.showMessage("Reviews by " + targetUser + (courseOrNull != null ? " in " + courseOrNull : "") + ":");
        for (String line : results) {
            System.out.println(" - " + line);
        }
    }

    /**
     * Returns false since this command does not exit the program.
     *
     * @return false to indicate the program should continue
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
