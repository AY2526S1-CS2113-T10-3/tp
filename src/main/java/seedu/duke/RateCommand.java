package seedu.duke;

public class RateCommand extends Command {
    private final String module;
    private final int score;

    public RateCommand(String module, int score) {
        this.module = module;
        this.score = score;
    }

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord ) throws UniflowException {
        RatingManager rm = Uniflow.getRatingManager();
        rm.addRating(module, score);

        ui.showMessage("Added Rating: " + score + " to " + module);
    }
}
