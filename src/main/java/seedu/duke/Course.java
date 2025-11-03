package seedu.duke;

/**
 * Represents a completed course with course code, credits, users' grade, and indication of major required course.
 */
public class Course {
    private final String code;
    private final int credits;
    private final String grade ;
    private final boolean isMajor;

    /**
     * Creates a Course object with the course details input by users.
     *
     * @param code The course code.
     * @param credits The number of credits for the course.
     * @param grade The grade the users attained/might attain for the course.
     * @param isMajor True if the course is a major course, false if not.
     */
    public Course(String code, int credits, String grade, boolean isMajor) {
        this.code = code;
        this.credits = credits;
        this.grade = grade;
        this.isMajor = isMajor;
    }

    public String getCode() {
        return code;
    }

    public int getCredits() {
        return credits;
    }

    public String getGrade() {
        return grade;
    }

    public boolean isMajor() {
        return isMajor;
    }

    /**
     * Returns the string representation of the course for display.
     * @return String representation of the course
     */
    public String toString() {
        String major = isMajor ? " (Major course)" : "";
        return code + " (" + credits + " credits" + ", Grade: " + grade + ")" + major;
    }

    /**
     * Returns the string representation of the course that is stored in the saved record.
     * @return String representation of the course for storage
     */
    public String toStorage() {
        return code + " | " + credits +  " | " + grade +  " | " + (isMajor ? 1 : 0);
    }

}
