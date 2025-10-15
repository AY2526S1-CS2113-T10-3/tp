package seedu.duke;

public class Module {
    private final String id;
    private final String name;
    private final String day;
    private final String startTime;
    private final String endTime;

    public Module(String id, String name, String day, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Module[ID=%s, Name=%s]", id, name);
    }
}
