package seedu.duke;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Deals with the loading and saving of grade information based on the course grade record file.
 */
public class GradeStorage {
    private static final String FILE_PATH = "data/grades.txt";

    /**
     * Update and save the newest grade information in the course grade record file.
     *
     * @param record The newest course grade record we will save in our file.
     */
    public void saveGradeRecord(CourseRecord record) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Course c : record.getCourseRecord()) {
                fw.write(c.toStorage() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error occurs when saving: " + e.getMessage());
        }
    }

    /**
     * Load the currently stored grade record from the task list file.
     */
    public CourseRecord loadGradeRecord() {
        CourseRecord record = new CourseRecord();
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();

            //create a new folder if it doesn't exist yet
            if (!parentDir.exists()) {
                parentDir.mkdirs();
                System.out.println("A new folder for grade record has been created.");
            }
            //create a new file if it doesn't exist yet
            if (file.createNewFile()) {
                System.out.println("Grade record file has been created.");
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                //for each course, gets the respective components that are separated by " | "
                //code, credits, grade, isMajor
                String[] parts = line.split(" \\| ");
                String code = parts[0];
                int credit = Integer.parseInt(parts[1]);
                String grade = parts[2];
                //in storage, major courses are marked with 1
                boolean isMajor = parts[3].equals("1");

                record.addCourse(new Course(code, credit, grade, isMajor));
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error occurs when loading: " + e.getMessage());
        }
        return record;
    }

}
