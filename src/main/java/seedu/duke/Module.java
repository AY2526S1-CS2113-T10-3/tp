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
        assert id != null && !id.trim().isEmpty() : "Module ID cannot be null or empty";
        assert name != null && !name.trim().isEmpty() : "Module name cannot be null or empty";
        assert day != null && !day.trim().isEmpty() : "Day cannot be null or empty";
        assert startTime != null && !startTime.trim().isEmpty() : "Start time cannot be null or empty";
        assert endTime != null && !endTime.trim().isEmpty() : "End time cannot be null or empty";

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

    public void setScoreBreakdown(Map<String, Integer> newBreakdown) {
        scoreBreakdown.clear();
        if (newBreakdown != null) {
            scoreBreakdown.putAll(newBreakdown);
        }
    }

    public Map<String, Integer> getScoreBreakdown() {
        return new HashMap<>(scoreBreakdown);
    }

    public boolean hasBreakdown() {
        return !scoreBreakdown.isEmpty();

    public String toStorage() {
        return id + " | " +  name + " | " + day + " | " + startTime + " | " + endTime + " | " +  sessionType;
    }

    @Override
    public String toString() {
        return String.format("Module[ID=%s, Name=%s, Type=%s, Day=%s, Time=%s-%s]",
                id, name, sessionType, day, startTime, endTime);
    }
}
