package seedu.duke;

public class RateCommand extends Command {
    private static final int RATING_QUERY_MODE = -1;
    private final String code;
    private final int score;

    public RateCommand(String code, int score) {
        this.code = code;
        this.score = score;
    }

    @Override
    public void execute(UI ui, ModuleList moduleList, CourseRecord courseRecord ) throws UniflowException {
        if (courseRecord == null && moduleList == null) {
            throw new UniflowException("No course information available");
        }
        if (!courseRecord.hasCourse(code) && !(moduleList.doesExist(code))) {
            throw new UniflowException("Course not found");
        }
        if (score == RATING_QUERY_MODE) {
            RatingManager rm = Uniflow.getRatingManager();
            int count = rm.getCount(code);
            double avg = rm.getAverage(code);
            if (count == 0) {
                ui.showMessage("No ratings found for " + code.toUpperCase());
            } else {
                ui.showMessage(code + " Rating: " + avg + " (" + count + " rating(s))");
            }
            return;
        }

        RatingManager rm = Uniflow.getRatingManager();
        rm.addRating(code, score);

        ui.showMessage("Added Rating: " + score + " to " + code);
    }
}
