package seedu.duke;

import java.util.List;
import java.util.Set;

public class CountReviewsCommand extends Command {
    private final String user;
    private final String course;
    private final ReviewManager reviewManager;

    public CountReviewsCommand(String input, ReviewManager reviewManager) throws UniflowException {
        this.reviewManager = reviewManager;
        input = input.trim();

        String tempUser = null;
        String tempCourse = null;

        for (String part : input.split("\\s+")) {
            if (part.startsWith("u/")) {
                tempUser = part.substring(2).trim();
            } else if (part.startsWith("c/")) {
                tempCourse = part.substring(2).trim();
            }
        }

        if ((tempUser == null || tempUser.isEmpty()) && (tempCourse == null || tempCourse.isEmpty())) {
            throw new UniflowException("Usage: amount reviews u/USER or amount reviews c/COURSE");
        }

        this.user = tempUser;
        this.course = tempCourse;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) {
        int count = 0;

        if (user != null) {
            Set<String> allCourses = reviewManager.getAllCourseIds();
            for (String c : allCourses) {
                List<String> reviews = reviewManager.getReviews(c);
                if (reviews == null) {
                    continue;
                };
                for (String r : reviews) {
                    if (r.startsWith(user + ": ")) {
                        count++;
                    }
                }
            }
            ui.showMessage("User " + user + " has " + count + " review(s).");
            return;
        }

        if (course != null) {
            List<String> reviews = reviewManager.getReviews(course);
            count = (reviews != null) ? reviews.size() : 0;
            ui.showMessage("Course " + course + " has " + count + " review(s).");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
