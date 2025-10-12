package seedu.duke;
import java.util.Scanner;

/**
 * Handles all user interface operations for the Uniflow chatbot.
 * Provides methods for displaying messages, reading user input, and formatting output.
 */
public class UI {
    private static final String DIVIDER = "____________________________________________________________";

    private final Scanner scanner;

    /**
     * Constructs a new UI instance with a Scanner for reading user input.
     */
    public UI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and Uniflow logo to the user.
     */
    public void showWelcome() {
        String logo = """
             __     __ _   _ _ _                      
             \\ \\   / /| | | | | | ___  ___  ___  ___ 
              \\ \\ / / | |_| | | |/ _ \\/ __|/ _ \\/ __|
               \\ V /  |  _  | | |  __/\\__ \\  __/\\__ \\
                \\_/   |_| |_|_|_|\\___||___/\\___||___/
            """;
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Uniflow");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads a command from the user input.
     *
     * @return the command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbye() {
        showMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a message with divider lines.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(DIVIDER);
        System.out.println(" " + message);
        System.out.println(DIVIDER);
    }

    /**
     * Displays an error message with divider lines.
     *
     * @param error the error message to display
     */
    public void showError(String error) {
        System.out.println(DIVIDER);
        System.out.println(" " + error);
        System.out.println(DIVIDER);
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays a message indicating that there was an error loading tasks from file.
     */
    public void showLoadingError() {
        showMessage("Error loading tasks from file. Starting with an empty task list.");
    }
    
    /**
     * Closes the scanner and releases system resources.
     */
    public void close() {
        scanner.close();
    }
}
