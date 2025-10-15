package seedu.duke;
import java.util.ArrayList;

/**
 * Uniflow is a personal task management chatbot that helps users manage their tasks.
 * It supports adding, listing, marking, deleting, and finding tasks.
 * Tasks can be of three types: ToDo, Deadline, or Event.
 */
public class Uniflow {
    private UI ui;
    private ModuleList modules;

    /**
     * Constructs a new Uniflow chatbot instance.
     *
     * @param filePath the path to the file where tasks are stored
     */

    private static ArrayList<String> timetable = new ArrayList<>();

    public static ArrayList<String> getTimetable() {
        return timetable;
    }

    public Uniflow(String filePath) {
        ui = new UI();
        modules = new ModuleList();
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
                String fullCommand = ui.readCommand().trim();
                if (fullCommand.isEmpty()) {
                    continue; // pomijamy puste linie, nie pokazujemy błędu
                }
                Command c = Parser.parse(fullCommand);
<<<<<<< HEAD
                c.execute(ui);
=======
                // c.execute(tasks, ui, storage);
                c.execute(ui, modules);
>>>>>>> 33c15f92a5174ab04794e7d05b7ae1f4343b3720
                isExit = c.isExit();
            } catch (UniflowException e) {
                ui.showError(e.getMessage());
            }
        }
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
