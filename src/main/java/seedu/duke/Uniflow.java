package seedu.duke;

public class Uniflow {
    private static CourseRecord courseRecord;
    private static ReviewManager reviewManager;

    private UI ui;
    private ModuleList modules;

    public Uniflow(String filePath) {
        ui = new UI();
        modules = new ModuleList();
        courseRecord = new CourseRecord();
        reviewManager = new ReviewManager();
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, modules);
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

    public static CourseRecord getCourseRecord() {
        return courseRecord;
    }

    public static void main(String[] args) {
        new Uniflow("testing").run();
    }
}
