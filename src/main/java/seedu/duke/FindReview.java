package seedu.duke;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FindReview extends Command {
    private final String user;
    private final String course;
    private final ReviewManager reviewManager;

    public FindReview(String input, ReviewManager reviewManager) throws UniflowException {
        this.reviewManager = reviewManager;
        input = input.trim();

        String tempUser = null;
        String tempCourse = null;

        for (String part : input.split("\\s+")) {
            if (part.startsWith("u/")) {
                tempUser = part.replaceFirst("u/", "").trim();
            } else if (part.startsWith("c/")) {
                tempCourse = part.replaceFirst("c/", "").trim();
            }
        }

        if ((tempUser == null || tempUser.isEmpty()) && (tempCourse == null || tempCourse.isEmpty())) {
            throw new UniflowException("Usage: findreview u/USER or findreview c/COURSE or both.");
        }

        this.user = tempUser;
        this.course = tempCourse;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        List<String> results = new ArrayList<>();

        if (user == null) {
            List<String> courseReviews = reviewManager.getReviews(course);
            if (courseReviews != null) {
                for (String r : courseReviews) {
                    results.add(course + " - " + r);
                }
            }
        } else if (course == null) {
            Set<String> allCourses = reviewManager.getAllCourseIds();
            for (String c : allCourses) {
                List<String> list = reviewManager.getReviews(c);
                if (list == null) continue;
                for (String r : list) {
                    if (r.startsWith(user + ": ")) {
                        results.add(c + " - " + r);
                    }
                }
            }
        } else {
            List<String> list = reviewManager.getReviews(course);
            if (list != null) {
                for (String r : list) {
                    if (r.startsWith(user + ": ")) {
                        results.add(course + " - " + r);
                    }
                }
            }
        }

        if (results.isEmpty()) {
            ui.showMessage("No reviews found for "
                    + (user != null ? "user " + user : "")
                    + (course != null ? " in " + course : ""));
            return;
        }

        String header;
        if (user != null && course != null) {
            header = "Reviews by " + user + " in " + course + ":";
        } else if (user != null) {
            header = "Reviews by " + user + ":";
        } else {
            header = "Reviews for " + course + ":";
        }

        ui.showMessage(header);
        for (String line : results) {
            System.out.println(" - " + line);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}