package seedu.duke;

public class Uniflow {

    private static CourseRecord courseRecord;
    private static final CourseRecord tempRecord = new CourseRecord();
    private static ModuleStorage moduleStorage = new ModuleStorage();
    private static final GradeStorage gradeStorage = new GradeStorage();
    private static final ScoreManager scoreManager = new ScoreManager();
    private static ReviewManager reviewManager;
    private static final RatingManager ratingManager = new RatingManager();

    private UI ui;
    private ModuleList modules;

    public Uniflow(String filePath) {
        ui = new UI();
        modules = new ModuleList();

        new ReviewCleaner().cleanInvalidReviews();

        reviewManager = new ReviewManager();
        ReviewSyncManager reviewSync = new ReviewSyncManager(reviewManager);
        reviewSync.setupAutoSync();

        try {
            courseRecord = gradeStorage.loadGradeRecord();
            modules = moduleStorage.loadModules();
        } catch (Exception e) {
            ui.showGradeLoadingError();
            courseRecord = new CourseRecord();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, modules, courseRecord);

                if (c instanceof AddGradeCommand) {
                    gradeStorage.saveGradeRecord(courseRecord);
                }

                if (c instanceof InsertCommand) {
                    moduleStorage.saveModules(modules);
                }

                isExit = c.isExit();
            } catch (UniflowException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static ReviewManager getReviewManager() {
        return reviewManager;
    }

    public static RatingManager getRatingManager() {
        return ratingManager;
    }

    public static CourseRecord getTempRecord() {
        return tempRecord;
    }

    public static ScoreManager getScoreManager() {
        return scoreManager;
    }

    public static void main(String[] args) {
        new Uniflow("testing").run();
    }
}
