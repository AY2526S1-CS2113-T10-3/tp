package seedu.duke;

import java.util.ArrayList;

/**
 * An array list that is used to store the completed courses and respective grades.
 */
public class CourseRecord {
    private final ArrayList<Course> courses = new ArrayList<>();
    private final ArrayList<Course> majorCourses = new ArrayList<>();

    /**
     * Add a course into the course record.
     * Add the course into the major course record as well if it is a major required course.
     *
     * @param course The course the user is adding.
     */
    public void addCourse(Course course) {
        courses.add(course);
        if (course.isMajor()) {
            majorCourses.add(course);
        }
    }

    /**
     * Returns the total number of credits of all courses the user studied.
     */
    public int getTotalCredits() {
        int totalCredits = 0;
        for (Course course : courses) {
            totalCredits += course.getCredits();
        }
        return totalCredits;
    }

    /**
     * Returns the user's total grade points of all courses.
     */
    public double getTotalGradePoints() throws UniflowException {
        double totalGradePoints = 0;

        for (Course course : courses) {
            double gradePoint = convertGradePoint(course.getGrade());
            totalGradePoints += gradePoint * course.getCredits();
        }

        return totalGradePoints;
    }

    /**
     * Returns the total number of credits of major courses the user studied.
     */
    public int getMajorTotalCredits() {
        int totalMajorCredits = 0;
        for (Course course : majorCourses) {
            totalMajorCredits += course.getCredits();
        }
        return totalMajorCredits;
    }

    /**
     * Returns the user's total grade points of major courses.
     */
    public double getMajorTotalGradePoints() throws UniflowException {
        double totalMajorGradePoints = 0;

        for (Course course : majorCourses) {
            double gradePoint = convertGradePoint(course.getGrade());
            totalMajorGradePoints += gradePoint * course.getCredits();
        }

        return totalMajorGradePoints;
    }

    /**
     * Computes and returns the user's cumulative GPA.
     */
    public double computeGpa() throws UniflowException {
        if (courses.isEmpty()) {
            throw new UniflowException("No courses found in course record!");
        }
        return getTotalGradePoints() / getTotalCredits();
    }

    /**
     * Computes and returns the users' cumulative major gpa.
     */
    public double computeMajorGpa() throws UniflowException {
        if (majorCourses.isEmpty()) {
            //return 0 instead of throwing error because it is allowed to have 0 major course in record
            return 0;
        }
        return getMajorTotalGradePoints() / getMajorTotalCredits();
    }

    /**
     * Combined two course lists' information to calculate the gpa of the courses in the two lists
     *
     * @param tempRecord A temporary course list the user has created.
     */
    public double computeCombinedGpa(CourseRecord tempRecord) throws UniflowException {
        double combinedGradePoints = getTotalGradePoints() + tempRecord.getTotalGradePoints();
        int combinedCredits = getTotalCredits() + tempRecord.getTotalCredits();
        if (combinedCredits == 0) {
            throw new UniflowException("No courses found to compute GPA!");
        }
        return combinedGradePoints / combinedCredits;
    }

    /**
     * Combined two course lists' information to calculate the major course gpa of the courses in the two lists
     *
     * @param tempRecord A temporary course list the user has created.
     */
    public double computeCombinedMajorGpa(CourseRecord tempRecord) throws UniflowException {
        double combinedMajorGradePoints = getMajorTotalGradePoints() + tempRecord.getMajorTotalGradePoints();
        int combinedMajorCredits = getMajorTotalCredits() + tempRecord.getMajorTotalCredits();
        if (combinedMajorCredits == 0) {
            throw new UniflowException("No courses found to compute GPA!");
        }
        return combinedMajorGradePoints / combinedMajorCredits;
    }

    /**
     * Converts the letter grade to its respective grade point.
     */
    private double convertGradePoint(String grade) throws UniflowException {
        switch (grade) {
        case "A+":
        case "A":
            return 5.0;
        case "A-":
            return 4.5;
        case "B+":
            return 4.0;
        case "B":
            return 3.5;
        case "B-":
            return 3.0;
        case "C+":
            return 2.5;
        case "C":
            return 2.0;
        case "D+":
            return 1.5;
        case "D":
            return 1.0;
        case "F":
            return 0.0;
        default:
            throw new UniflowException("You have entered an invalid grade!");
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

    public Course getCourse(String code) {
        for (Course c : courses) {
            if (c.getCode() != null && c.getCode().trim().toUpperCase().equals(code.trim().toUpperCase())) {
                return c;
            }
        }
        return null;
    }

    public int getMajorSize() {
        return majorCourses.size();
    }
}
