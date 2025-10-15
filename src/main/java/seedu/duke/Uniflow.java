package seedu.duke;
import java.util.ArrayList;

/**
 * Uniflow is a personal task management chatbot that helps users manage their tasks.
 * It supports adding, listing, marking, deleting, and finding tasks.
 * Tasks can be of three types: ToDo, Deadline, or Event.
 */
public class Uniflow {
    private UI ui;

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
                c.execute(ui);
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
