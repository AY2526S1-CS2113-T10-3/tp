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

    public int  getSize() {
        return courses.size();
    }

    public boolean hasCourse(String code) {
        if (code == null) {
            return false;
        }
        String target = code.trim().toUpperCase();
        for (Course c : courses) {
            if (c.getCode() != null && c.getCode().trim().toUpperCase().equals(target)) {
                return true;
            }
        }
        return false;
    }
}
