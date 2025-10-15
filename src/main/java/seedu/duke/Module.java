package seedu.duke;

public class Module {
    private final String id;
    private final String name;
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String sessionType; // lecture, tutorial, lab, etc.

    public Module(String id, String name, String day, String startTime, String endTime, String sessionType) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sessionType = sessionType != null ? sessionType.toLowerCase() : "lecture";
    }

    // Constructor for backward compatibility (defaults to "lecture")
    public Module(String id, String name, String day, String startTime, String endTime) {
        this(id, name, day, startTime, endTime, "lecture");
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

    @Override
    public String toString() {
        return String.format("Module[ID=%s, Name=%s, Type=%s, Day=%s, Time=%s-%s]",
                id, name, sessionType, day, startTime, endTime);
    }
}
