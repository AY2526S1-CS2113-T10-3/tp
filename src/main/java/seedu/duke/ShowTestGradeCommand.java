package seedu.duke;

public class ShowTestGradeCommand extends Command {
    private final CourseRecord tempRecord = Uniflow.getTempRecord();

    @Override
    public void execute(UI ui, ModuleList modules, CourseRecord courseRecord) throws UniflowException {

        if (tempRecord.isEmpty()) {
            ui.showMessage("Currently there is no predicted grade stored in the temporary record!");
            return;
        }

        ui.showLine();
        System.out.println("Currently, there are " + tempRecord.getCourseRecord().size()
                + " predicted course grades in the temporary record.");
        System.out.println("Here is the temporary record:");
        ui.showLine();
        for (int i = 0; i < tempRecord.getCourseRecord().size(); i++) {
            Course c = tempRecord.getCourseRecord().get(i);
            System.out.println((i + 1) + ". " + c.toString());
        }
        ui.showLine();
    }
}
