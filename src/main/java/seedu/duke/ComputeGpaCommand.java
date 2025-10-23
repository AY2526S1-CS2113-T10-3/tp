package seedu.duke;

public class ComputeGpaCommand extends Command {

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (courseRecord.isEmpty()) {
            ui.showError("No courses were added in the record!");
            return;
        }

        double totalGradePoints = 0;
        int totalCredits = 0;
        double gpa = 0;
        double totalMajorCourseGradePoints = 0;
        int totalMajorCourseCredits = 0;
        double majorGpa = 0;

        //compute gpa for all courses
        for (Course course : courseRecord.getCourseRecord()) {
            double gradePoint = convertGradePoint(course.getGrade());
            totalGradePoints += gradePoint * course.getCredits();
            totalCredits += course.getCredits();
        }

        gpa = totalGradePoints / totalCredits;

        //compute major gpa for major courses
        for (Course course : courseRecord.getMajorCourses()) {
            double gradePoint = convertGradePoint(course.getGrade());
            totalMajorCourseGradePoints += gradePoint * course.getCredits();
            totalMajorCourseCredits += course.getCredits();
        }

        majorGpa = totalMajorCourseGradePoints / totalMajorCourseCredits;

        ui.showMessage("You have studied " + courseRecord.getSize() + " courses.\n"
                + " Your total grade points are " + totalGradePoints + "\n"
                + " Number of credits you have studied: " + totalCredits + "\n"
                + String.format(" Your GPA is: %.2f", gpa) +"\n"
                + " You have studied " + courseRecord.getMajorSize() + " major courses.\n"
                + " Your total major course grade points are " + totalMajorCourseGradePoints + "\n"
                + " Number of credits you have studied: " + totalMajorCourseCredits + "\n"
                + String.format(" Your major GPA is: %.2f", majorGpa));
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
}
