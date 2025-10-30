package seedu.duke;

public class Course {
    private final String code;
    private final int credits;
    private final String grade ;
    private final boolean isMajor;


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

    public String toString() {
        String major = isMajor ? " (Major course)" : "";
        return code + " (" + credits + " credits" + ", Grade: " + grade + ")" + major;
    }

    public String toStorage() {
        return code + " | " + credits +  " | " + grade +  " | " + (isMajor ? 1 : 0);
    }

}
