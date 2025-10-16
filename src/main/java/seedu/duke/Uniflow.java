package seedu.duke;

/**
 * Uniflow is a personal task management chatbot that helps users manage their tasks.
 * It supports adding, listing, marking, deleting, and finding tasks.
 * Tasks can be of three types: ToDo, Deadline, or Event.
 */
public class Uniflow {
    private static CourseRecord courseRecord;
    
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
        // Initialization of storage or other components can go here
        // try {
        //     // code to load tasks
        // } catch (UniflowException e) {
        //     ui.showLoadingError();
        // }
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
                ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                // c.execute(tasks, ui, storage);
                c.execute(ui, modules);
                isExit = c.isExit();
            } catch (UniflowException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
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
        // new Uniflow("data/tasks.txt").run();
        new Uniflow("testing").run();
    }
}
