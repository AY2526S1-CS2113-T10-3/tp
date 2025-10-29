package seedu.duke;

import java.util.ArrayList;

public class CourseRecord {
    private final ArrayList<Course> courses = new ArrayList<>();
    private final ArrayList<Course> majorCourses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
        if (course.getIsMajor()) {
            majorCourses.add(course);
        }
    }

    public ArrayList<Course> getCourseRecord() {
        return courses;
    }

    public ArrayList<Course> getMajorCourses() {
        return majorCourses;
    }

    public boolean isEmpty() {
        return courses.isEmpty();
    }

    public int getSize() {
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

    public int getMajorSize() {
        return majorCourses.size();
    }
}
