package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ParserTest {

    @Test
    void parse_unknownCommand() {
        assertThrows(UniflowException.class, () -> {
            Parser.parse("unknown command");
        });
    }

    @Test
    void parse_exitCommand() throws UniflowException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void parse_insertCommand() throws UniflowException {
        Command command = Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/09:00 t/10:00 s/Tutorial");
        assertInstanceOf(InsertCommand.class, command);
    }

    @Test
    void parse_deleteCommand() throws UniflowException {
        Command command = Parser.parse("delete i/CS2113");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parse_listCommand() throws UniflowException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_filterCommand() throws UniflowException {
        Command command = Parser.parse("filter day/Monday");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parseScoreCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("score CS2113 exam:50 project:30");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    void parseScoreCommand_queryMode() throws UniflowException {
        Command command = Parser.parse("score CS2113 -1");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    void parseScoreCommand_missingId() {
        assertThrows(UniflowException.class, () -> {
            Parser.parse("score");
        });
    }

    @Test
    void parse_addGradeCommand() throws UniflowException {
        Command command = Parser.parse("addgrade c/CS2113 cr/4 g/A m/true");
        assertInstanceOf(AddGradeCommand.class, command);
    }

    @Test
    void parse_addTestGradeCommand() throws UniflowException {
        Command command = Parser.parse("addtestgrade c/CS2113 cr/4 g/A m/true");
        assertInstanceOf(AddTestGradeCommand.class, command);
    }

    @Test
    void parse_gpaCommand() throws UniflowException {
        Command command = Parser.parse("gpa");
        assertInstanceOf(ComputeGpaCommand.class, command);
    }

    @Test
    void parse_projectGpaCommand() throws UniflowException {
        Command command = Parser.parse("projectgpa");
        assertInstanceOf(ProjectGpaCommand.class, command);
    }

    @Test
    void parse_showTimetableCommand() throws UniflowException {
        Command command = Parser.parse("show timetable");
        assertInstanceOf(ShowTimetableCommand.class, command);
    }

    @Test
    void parse_resetTimetableCommand() throws UniflowException {
        Command command = Parser.parse("reset timetable");
        assertInstanceOf(ResetTimetableCommand.class, command);
    }

    @Test
    void parseRateCommand_withRating() throws UniflowException {
        Command command = Parser.parse("rate CS2113 4");
        assertInstanceOf(RateCommand.class, command);
    }

    @Test
    void parseRateCommand_queryMode() throws UniflowException {
        Command command = Parser.parse("rate CS2113");
        assertInstanceOf(RateCommand.class, command);
    }

    @Test
    void parseRateCommand_invalidRating() {
        assertThrows(UniflowException.class, () -> {
            Parser.parse("rate CS2113 10");
        });
    }

    @Test
    void parseReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("review CS2113");
        assertInstanceOf(ReviewCommand.class, command);
    }

    @Test
    void parseAddReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("addreview c/CS2113 u/John r/Great module!");
        assertInstanceOf(AddReviewCommand.class, command);
    }

    @Test
    void parseAddReviewCommand_missingFields() {
        assertThrows(UniflowException.class, () -> {
            Parser.parse("addreview c/CS2113");
        });
    }

    @Test
    void parseEditReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("editreview c/CS2113 u/John r/Updated review");
        assertInstanceOf(EditReviewCommand.class, command);
    }

    @Test
    void parseDeleteReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("deletereview c/CS2113 u/John");
        assertInstanceOf(DeleteReviewCommand.class, command);
    }
}
