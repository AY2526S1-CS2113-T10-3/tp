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

        for (Course course : courseRecord.getCourseRecord()) {
            double gradePoint = convertGradePoint(course.getGrade());
            totalGradePoints += gradePoint * course.getCredits();
            totalCredits += course.getCredits();
        }

        double gpa = totalGradePoints / totalCredits;
        ui.showMessage("You have studied " + courseRecord.getSize() + " courses.\n"
                + "Your total grade points are " + totalGradePoints + "\n"
                + "Number of credits you have studied: " + totalCredits + "\n"
                + String.format("Your GPA is: %.2f", gpa));
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
