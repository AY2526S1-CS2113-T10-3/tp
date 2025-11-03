package seedu.duke;

public class ShowGradeCommand extends Command {
    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {
        if (courseRecord.isEmpty()) {
            ui.showMessage("Currently there are no course grades stored in the record!");
            return;
        }

        ui.showLine();
        System.out.println("Currently, you have " + courseRecord.getCourseRecord().size()
                + " saved course grades");
        System.out.println("Here are your saved course grade records:");
        ui.showLine();
        for (int i = 0; i < courseRecord.getCourseRecord().size(); i++) {
            Course c = courseRecord.getCourseRecord().get(i);
            System.out.println((i + 1) + ". " + c.toString());
        }
        ui.showLine();
    }
}
