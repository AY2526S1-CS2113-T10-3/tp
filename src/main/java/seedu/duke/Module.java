package seedu.duke;

import java.util.HashMap;
import java.util.Map;

public class Module {
    private final String id;
    private final String name;
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String sessionType; // lecture, tutorial, lab, etc.
    private final Map<String, Integer> scoreBreakdown;

    public Module(String id, String name, String day, String startTime, String endTime, String sessionType) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sessionType = sessionType != null ? sessionType.toLowerCase() : "lecture";
        this.scoreBreakdown = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSessionType() {
        return sessionType;
    }

    public boolean hasTutorial() {
        return sessionType.equalsIgnoreCase("tutorial");
    }

    public Map<String, Integer> getScoreBreakdown() {
        return scoreBreakdown;
    }

    public void setScoreBreakdown(Map<String, Integer> newBreakdown) {
        scoreBreakdown.clear();
        if (newBreakdown != null) {
            scoreBreakdown.putAll(newBreakdown);
        }
    }

    public boolean hasBreakdown() {
        return !scoreBreakdown.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Module[ID=%s, Name=%s, Type=%s, Day=%s, Time=%s-%s]",
                id, name, sessionType, day, startTime, endTime);
    }
}
