package seedu.duke;

public class RateCommand extends Command {
    private final String courseCode;
    private final int score;
    private static final int RATING_QUERY_MODE = -1;

    public RateCommand(String courseCode, int score) {
        this.courseCode = courseCode;
        this.score = score;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord ) throws UniflowException {
        if (courseRecord == null) {
            throw new UniflowException("No course record available");
        }
        if (!courseRecord.hasCourse(courseCode)) {
            throw new UniflowException("Course not found");
        }
        if (score == RATING_QUERY_MODE) {
            RatingManager rm = Uniflow.getRatingManager();
            int count = rm.getCount(courseCode);
            double avg = rm.getAverage(courseCode);
            if (count == 0) {
                ui.showMessage("No ratings found for " + courseCode.toUpperCase());
            } else {
                ui.showMessage(courseCode + " Rating: " + avg + " (" + count + " ratings)");
            }
            return;
        }

        RatingManager rm = Uniflow.getRatingManager();
        rm.addRating(courseCode, score);

        ui.showMessage("Added Rating: " + score + " to " + courseCode);
    }
}
