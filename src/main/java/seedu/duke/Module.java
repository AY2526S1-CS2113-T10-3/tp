package seedu.duke;

public class Module {
    private final String id;
    private final String name;

    public Module(String id, String name) {
        this.id = id;
        this.name = name;
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
