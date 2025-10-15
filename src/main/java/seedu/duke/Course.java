package seedu.duke;

public class Course {
    private final String code;
    private final int credits;
    private final String grade ;

    public Course(String code, int credits, String grade) {
        this.code = code;
        this.credits = credits;
        this.grade = grade;
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

    public String toString() {
        return code + " (" + credits + " credits" + ", Grade: " + grade + ")";
    }

}
