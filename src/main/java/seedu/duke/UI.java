package seedu.duke;
import java.util.Scanner;

/**
 * Handles all user interface operations for the Uniflow chatbot.
 * Provides methods for displaying messages, reading user input, and formatting output.
 */
public class UI {
    private static final String DIVIDER = "___________________________________________________________________________";

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
             _   _       _  __ _
            | | | |_ __ (_)/ _| | _____      __
            | | | | '_ \\| | |_| |/ _ \\ \\ /\\ / /
            | |_| | | | | |  _| | (_) \\ V  V /
             \\___/|_| |_|_|_| |_|\\___/ \\_/\\_/
            """;
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Uniflow");
        System.out.println("What can I do for you?");
        System.out.flush();
    }

    /**
     * Reads a command from the user input.
     *
     * @return the command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showModuleCodes(ModuleList modules) {
        System.out.println("_________________________________________________________");
        if (modules.isEmpty()) {
            System.out.println(" You don’t have any modules yet.");
        } else {
            System.out.println(" Your modules:");
            for (int i = 0; i < modules.getSize(); i++) {
                try {
                    Module module = modules.getModule(i);
                    System.out.println(" - " + module.getId());
                } catch (UniflowException e) {
                    System.out.println(" [Error loading module: " + e.getMessage() + "]");
                }
            }
        }
        System.out.println("_________________________________________________________");
    }


    /**
     * Displays filtered modules with a description of the filter applied.
     *
     * @param modules the filtered module list
     * @param filterDescription description of what filter was applied
     */
    public void showFilteredModules(ModuleList modules, String filterDescription) {
        System.out.println(DIVIDER);
        if (modules.isEmpty()) {
            System.out.println(" No modules found matching " + filterDescription + ".");
        } else {
            System.out.println(" Found " + modules.getSize() + " module(s) matching " + filterDescription + ":");
            for (int i = 0; i < modules.getSize(); i++) {
                try {
                    System.out.println(" " + (i + 1) + ". " + modules.getModule(i));
                } catch (UniflowException e) {
                    System.out.println(" " + (i + 1) + ". [Error loading Module: " + e.getMessage() + "]");
                }
            }
        }
        System.out.println(DIVIDER);
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
     * Displays a message indicating that there was an error loading Modules from file.
     */
    public void showLoadingError() {
        showMessage("Error loading Modules from file. Starting with an empty Module list.");
    }

    /**
     * Displays a message indicating that there was an error loading Grades from file.
     */
    public void showGradeLoadingError() {
        showMessage("Error loading Grades from file. Starting with an empty grade record.");
    }

    public void showClashWarning(Module newModule, Module existingModule) {
        showMessage("Warning: " + newModule.getId() + " clashes with " + existingModule.getId()
                + " on " + newModule.getDay() + " (" + newModule.getStartTime() + "–" + newModule.getEndTime() + ")");
    }

    /**
     * Closes the scanner and releases system resources.
     */
    public void close() {
        scanner.close();
    }
}
