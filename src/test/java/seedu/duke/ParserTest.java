package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_byeCommand_returnsExitCommand() throws UniflowException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void parse_listCommand_returnsListCommand() throws UniflowException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void parse_gpaCommand_returnsComputeGpaCommand() throws UniflowException {
        Command command = Parser.parse("gpa");
        assertInstanceOf(ComputeGpaCommand.class, command);
    }

    @Test
    public void parse_projectGpaCommand_returnsProjectGpaCommand() throws UniflowException {
        Command command = Parser.parse("projectgpa");
        assertInstanceOf(ProjectGpaCommand.class, command);
    }

    @Test
    public void parse_showTimetableCommand_returnsShowTimetableCommand() throws UniflowException {
        Command command = Parser.parse("show timetable");
        assertInstanceOf(ShowTimetableCommand.class, command);
    }

    @Test
    public void parse_resetTimetableCommand_returnsResetTimetableCommand() throws UniflowException {
        Command command = Parser.parse("reset timetable");
        assertInstanceOf(ResetTimetableCommand.class, command);
    }

    @Test
    public void parse_insertCommand_validFormat_returnsInsertCommand() throws UniflowException {
        Command command = Parser.parse("insert i/CS2113 n/Software d/Monday f/14:00 t/16:00 s/lecture");
        assertInstanceOf(InsertCommand.class, command);
    }

    @Test
    public void parse_insertCommand_missingFields_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("insert i/CS2113 n/Software"));
    }

    @Test
    public void parse_deleteCommand_validFormat_returnsDeleteCommand() throws UniflowException {
        Command command = Parser.parse("delete i/CS2113");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void parse_deleteCommand_invalidFormat_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("delete CS2113"));
    }

    @Test
    public void parse_deleteCommand_missingId_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("delete i/"));
    }

    @Test
    public void parse_filterCommand_typeFilter_returnsFilterCommand() throws UniflowException {
        Command command = Parser.parse("filter type/lecture");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    public void parse_filterCommand_hasTutorial_returnsFilterCommand() throws UniflowException {
        Command command = Parser.parse("filter hastutorial");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    public void parse_filterCommand_noTutorial_returnsFilterCommand() throws UniflowException {
        Command command = Parser.parse("filter notutorial");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    public void parse_filterCommand_dayFilter_returnsFilterCommand() throws UniflowException {
        Command command = Parser.parse("filter day/Monday");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    public void parse_filterCommand_emptyValue_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("filter type/"));
    }

    @Test
    public void parse_filterCommand_noParameters_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("filter"));
    }

    @Test
    public void parse_addGradeCommand_validFormat_returnsAddGradeCommand() throws UniflowException {
        Command command = Parser.parse("addgrade c/CS2113 cr/4 g/A m/true");
        assertInstanceOf(AddGradeCommand.class, command);
    }

    @Test
    public void parse_addGradeCommand_missingFields_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("addgrade c/CS2113 cr/4"));
    }

    @Test
    public void parse_addTestGradeCommand_validFormat_returnsAddTestGradeCommand() throws UniflowException {
        Command command = Parser.parse("addtestgrade c/CS2113 cr/4 g/A m/true");
        assertInstanceOf(AddTestGradeCommand.class, command);
    }

    @Test
    public void parse_scoreCommand_validFormat_returnsScoreCommand() throws UniflowException {
        Command command = Parser.parse("score CS2113 exam:50 project:30");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    public void parse_scoreCommand_queryMode_returnsScoreCommand() throws UniflowException {
        Command command = Parser.parse("score CS2113 -1");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    public void parse_scoreCommand_missingId_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("score"));
    }

    @Test
    public void parse_rateCommand_withRating_returnsRateCommand() throws UniflowException {
        Command command = Parser.parse("rate CS2113 5");
        assertInstanceOf(RateCommand.class, command);
    }

    @Test
    public void parse_rateCommand_queryMode_returnsRateCommand() throws UniflowException {
        Command command = Parser.parse("rate CS2113");
        assertInstanceOf(RateCommand.class, command);
    }

    @Test
    public void parse_rateCommand_invalidRating_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("rate CS2113 6"));

        assertThrows(UniflowException.class, () -> Parser.parse("rate CS2113 0"));
    }

    @Test
    public void parse_reviewCommand_validFormat_returnsReviewCommand() throws UniflowException {
        Command command = Parser.parse("review CS2113");
        assertInstanceOf(ReviewCommand.class, command);
    }

    @Test
    public void parse_addReviewCommand_validFormat_returnsAddReviewCommand() throws UniflowException {
        Command command = Parser.parse("addreview c/CS2113 u/John r/Great course!");
        assertInstanceOf(AddReviewCommand.class, command);
    }

    @Test
    public void parse_addReviewCommand_missingFields_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("addreview c/CS2113 u/John"));
    }

    @Test
    public void parse_editReviewCommand_validFormat_returnsEditReviewCommand() throws UniflowException {
        Command command = Parser.parse("editreview c/CS2113 u/John r/Updated review");
        assertInstanceOf(EditReviewCommand.class, command);
    }

    @Test
    public void parse_deleteReviewCommand_validFormat_returnsDeleteReviewCommand() throws UniflowException {
        Command command = Parser.parse("deletereview c/CS2113 u/John");
        assertInstanceOf(DeleteReviewCommand.class, command);
    }

    @Test
    public void parse_emptyCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse(""));
    }

    @Test
    public void parse_nullCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse(null));
    }

    @Test
    public void parse_whitespaceCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("   "));
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("unknown command"));
    }
}
