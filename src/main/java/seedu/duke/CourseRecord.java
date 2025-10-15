package seedu.duke;

import java.util.ArrayList;

public class CourseRecord {
    private final ArrayList<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public ArrayList<Course> getCourseRecord() {
        return courses;
    }

    public boolean isEmpty() {
        return courses.isEmpty();
    }
}
