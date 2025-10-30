package seedu.duke;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private final String code;
    private final int credits;
    private final String grade ;
    private final boolean isMajor;
    private final Map<String, Integer> scoreBreakdown;

    public Course(String code, int credits, String grade, boolean isMajor) {
        this.code = code;
        this.credits = credits;
        this.grade = grade;
        this.isMajor = isMajor;
        this.scoreBreakdown = new HashMap<>();
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

    public void setScoreBreakdown(Map<String, Integer> newBreakdown) {
        scoreBreakdown.clear();
        if (newBreakdown != null) {
            scoreBreakdown.putAll(newBreakdown);
        }
    }

    public Map<String, Integer> getScoreBreakdown() {
        return scoreBreakdown;
    }

    public boolean hasBreakdown() {
        return !scoreBreakdown.isEmpty();
    }

}
