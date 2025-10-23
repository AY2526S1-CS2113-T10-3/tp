package seedu.duke;

import java.util.List;

public class ReviewCommand extends Command {
    private final String course;
    private final ReviewManager reviewManager;

    public ReviewCommand(String course, ReviewManager reviewManager) {
        this.course = course;
        this.reviewManager = reviewManager;
    }

    @Override
    public void execute(UI ui, ModuleList modules) {
        if (!reviewManager.hasCourse(course)) {
            ui.showMessage("No reviews found for " + course + ".");
            return;
        }
        List<String> list = reviewManager.getReviews(course);
        if (list == null || list.isEmpty()) {
            ui.showMessage("No reviews yet for " + course + ".");
            return;
        }
        ui.showMessage("Reviews for " + course + ":");
        for (String r : list) {
            System.out.println(" - " + r);
        }
    }
}
