package seedu.duke;


/**
 * Uniflow is a personal task management chatbot that helps users manage their tasks.
 * It supports adding, listing, marking, deleting, and finding tasks.
 * Tasks can be of three types: ToDo, Deadline, or Event.
 */

public class Uniflow {
    private static CourseRecord courseRecord;
    private static ReviewManager reviewManager;
    private static final RatingManager ratingManager = new  RatingManager();

    private UI ui;
    private ModuleList modules;

    /**
     * Constructs a new Uniflow chatbot instance.
     *
     * @param filePath the path to the file where tasks are stored
     */

    public Uniflow(String filePath) {
        ui = new UI();
        modules = new ModuleList();
        courseRecord = new CourseRecord();
        reviewManager = new ReviewManager();
    }
    /**
     * Runs the main chatbot loop, processing user commands until exit.
     * Displays welcome message and handles all user interactions.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                // c.execute(tasks, ui, storage);
                c.execute(ui, modules, courseRecord);
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

    public static CourseRecord getCourseRecord() {
        return courseRecord;
    }
    /**
     * Main entry point for the Uniflow chatbot application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Uniflow("testing").run();
    }
}
