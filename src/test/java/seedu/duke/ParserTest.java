package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    // Basic command parsing tests
    @Test
    void parse_emptyCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse(""));
    }

    @Test
    void parse_nullCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse(null));
    }

    @Test
    void parse_whitespaceOnlyCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("   "));
    }

    @Test
    void parse_unknownCommand_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("unknown command"));
    }

    @Test
    void parse_exitCommand_returnsExitCommand() throws UniflowException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void parse_exitCommandWithWhitespace_returnsExitCommand() throws UniflowException {
        Command command = Parser.parse("  bye  ");
        assertInstanceOf(ExitCommand.class, command);
    }

    // Insert command tests
    @Test
    void parse_insertCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/09:00 t/10:00 s/Tutorial");
        assertInstanceOf(InsertCommand.class, command);
    }

    @Test
    void parse_insertCommand_withoutSessionType() throws UniflowException {
        Command command = Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/09:00 t/10:00");
        assertInstanceOf(InsertCommand.class, command);
    }

    @Test
    void parse_insertCommand_missingId_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert n/Software Engineering d/Monday f/09:00 t/10:00"));
    }

    @Test
    void parse_insertCommand_missingName_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 d/Monday f/09:00 t/10:00"));
    }

    @Test
    void parse_insertCommand_missingDay_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering f/09:00 t/10:00"));
    }

    @Test
    void parse_insertCommand_missingStartTime_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering d/Monday t/10:00"));
    }

    @Test
    void parse_insertCommand_missingEndTime_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/09:00"));
    }

    @Test
    void parse_insertCommand_invalidDay_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering d/Funday f/09:00 t/10:00"));
    }

    @Test
    void parse_insertCommand_invalidTimeFormat_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/9:00 t/10:00"));
    }

    @Test
    void parse_insertCommand_endBeforeStart_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/10:00 t/09:00"));
    }

    @Test
    void parse_insertCommand_sameStartAndEnd_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("insert i/CS2113 n/Software Engineering d/Monday f/10:00 t/10:00"));
    }

    // Delete command tests
    @Test
    void parse_deleteCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("delete index/1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void parse_deleteCommand_missingIndex_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("delete"));
    }

    @Test
    void parse_deleteCommand_invalidFormat_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("delete CS2113"));
    }

    @Test
    void parse_deleteCommand_nonNumericIndex_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("delete index/abc"));
    }

    // List command test
    @Test
    void parse_listCommand_returnsListCommand() throws UniflowException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    // Filter command tests
    @Test
    void parse_filterByDay_validFormat() throws UniflowException {
        Command command = Parser.parse("filter day/Monday");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parse_filterById_validFormat() throws UniflowException {
        Command command = Parser.parse("filter id/CS2113");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parse_filterByName_validFormat() throws UniflowException {
        Command command = Parser.parse("filter name/Software");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parse_filterByType_validFormat() throws UniflowException {
        Command command = Parser.parse("filter type/tutorial");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parse_filterHasTutorial_validFormat() throws UniflowException {
        Command command = Parser.parse("filter hastutorial");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parse_filterNoTutorial_validFormat() throws UniflowException {
        Command command = Parser.parse("filter notutorial");
        assertInstanceOf(FilterCommand.class, command);
    }

    @Test
    void parse_filterCommand_emptyValue_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("filter day/"));
    }

    @Test
    void parse_filterCommand_missingParameters_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("filter"));
    }

    // Score command tests
    @Test
    void parseScoreCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("score CS2113 exam:50 project:30");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    void parseScoreCommand_singleComponent() throws UniflowException {
        Command command = Parser.parse("score CS2113 exam:100");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    void parseScoreCommand_queryMode() throws UniflowException {
        Command command = Parser.parse("score CS2113 -1");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    void parseScoreCommand_queryModeNoBreakdown() throws UniflowException {
        Command command = Parser.parse("score CS2113");
        assertInstanceOf(ScoreCommand.class, command);
    }

    @Test
    void parseScoreCommand_missingId_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("score"));
    }

    @Test
    void parseScoreCommand_invalidFormat_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("score CS2113 exam50"));
    }

    // Grade command tests
    @Test
    void parse_addGradeCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("addgrade c/CS2113 cr/4 g/A m/true");
        assertInstanceOf(AddGradeCommand.class, command);
    }

    @Test
    void parse_addGradeCommand_missingCode_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade cr/4 g/A m/true"));
    }

    @Test
    void parse_addGradeCommand_missingCredits_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 g/A m/true"));
    }

    @Test
    void parse_addGradeCommand_missingGrade_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/4 m/true"));
    }

    @Test
    void parse_addGradeCommand_missingMajor_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/4 g/A"));
    }

    @Test
    void parse_addGradeCommand_invalidGrade_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/4 g/Z m/true"));
    }

    @Test
    void parse_addGradeCommand_invalidCredits_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/abc g/A m/true"));
    }

    @Test
    void parse_addGradeCommand_negativeCredits_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/-4 g/A m/true"));
    }

    @Test
    void parse_addGradeCommand_invalidMajor_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/4 g/A m/invalid"));
    }

    @Test
    void parse_addTestGradeCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("addtestgrade c/CS2113 cr/4 g/A m/true");
        assertInstanceOf(AddTestGradeCommand.class, command);
    }

    @Test
    void parse_gpaCommand_returnsComputeGpaCommand() throws UniflowException {
        Command command = Parser.parse("gpa");
        assertInstanceOf(ComputeGpaCommand.class, command);
    }

    @Test
    void parse_projectGpaCommand_returnsProjectGpaCommand() throws UniflowException {
        Command command = Parser.parse("projectgpa");
        assertInstanceOf(ProjectGpaCommand.class, command);
    }

    @Test
    void parse_showGradeCommand_returnsShowGradeCommand() throws UniflowException {
        Command command = Parser.parse("showgrade");
        assertInstanceOf(ShowGradeCommand.class, command);
    }

    @Test
    void parse_showTestGradeCommand_returnsShowTestGradeCommand() throws UniflowException {
        Command command = Parser.parse("showtestgrade");
        assertInstanceOf(ShowTestGradeCommand.class, command);
    }

    @Test
    void parse_removeGradeCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("removegrade 1");
        assertInstanceOf(RemoveGradeCommand.class, command);
    }

    @Test
    void parse_removeGradeCommand_missingIndex_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("removegrade"));
    }

    @Test
    void parse_removeGradeCommand_invalidIndex_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("removegrade abc"));
    }

    @Test
    void parse_removeTestGradeCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("removetestgrade 1");
        assertInstanceOf(RemoveTestGradeCommand.class, command);
    }

    // Timetable command tests
    @Test
    void parse_showTimetableCommand_returnsShowTimetableCommand() throws UniflowException {
        Command command = Parser.parse("show timetable");
        assertInstanceOf(ShowTimetableCommand.class, command);
    }

    @Test
    void parse_resetTimetableCommand_returnsResetTimetableCommand() throws UniflowException {
        Command command = Parser.parse("reset timetable");
        assertInstanceOf(ResetTimetableCommand.class, command);
    }

    // Review command tests
    @Test
    void parseReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("review CS2113");
        assertInstanceOf(ReviewCommand.class, command);
    }

    @Test
    void parseReviewCommand_missingCourse_throwsException() {
        assertThrows(UniflowException.class, () -> Parser.parse("review"));
    }

    @Test
    void parseAddReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("addreview c/CS2113 u/John r/Great module!");
        assertInstanceOf(AddReviewCommand.class, command);
    }

    @Test
    void parseAddReviewCommand_missingCourse_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addreview u/John r/Great!"));
    }

    @Test
    void parseAddReviewCommand_missingUser_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addreview c/CS2113 r/Great!"));
    }

    @Test
    void parseAddReviewCommand_missingReview_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addreview c/CS2113 u/John"));
    }

    @Test
    void parseEditReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("editreview c/CS2113 u/John r/Updated review");
        assertInstanceOf(EditReviewCommand.class, command);
    }

    @Test
    void parseEditReviewCommand_withIndex() throws UniflowException {
        Command command = Parser.parse("editreview c/CS2113 u/John r/Updated i/1");
        assertInstanceOf(EditReviewCommand.class, command);
    }

    @Test
    void parseEditReviewCommand_missingFields_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("editreview c/CS2113"));
    }

    @Test
    void parseDeleteReviewCommand_validFormat() throws UniflowException {
        Command command = Parser.parse("deletereview c/CS2113 u/John");
        assertInstanceOf(DeleteReviewCommand.class, command);
    }

    @Test
    void parseDeleteReviewCommand_withIndex() throws UniflowException {
        Command command = Parser.parse("deletereview c/CS2113 u/John i/1");
        assertInstanceOf(DeleteReviewCommand.class, command);
    }

    @Test
    void parseFindReviewCommand_byUser() throws UniflowException {
        Command command = Parser.parse("findreview u/John");
        assertInstanceOf(FindReview.class, command);
    }

    @Test
    void parseFindReviewCommand_byCourse() throws UniflowException {
        Command command = Parser.parse("findreview c/CS2113");
        assertInstanceOf(FindReview.class, command);
    }

    @Test
    void parseFindReviewCommand_byBoth() throws UniflowException {
        Command command = Parser.parse("findreview c/CS2113 u/John");
        assertInstanceOf(FindReview.class, command);
    }

    // Rating command tests
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
    void parseRateCommand_invalidRatingTooLow() {
        assertThrows(UniflowException.class, () -> Parser.parse("rate CS2113 0"));
    }

    @Test
    void parseRateCommand_invalidRatingTooHigh() {
        assertThrows(UniflowException.class, () -> Parser.parse("rate CS2113 6"));
    }

    @Test
    void parseRateCommand_invalidRatingNonNumeric() {
        assertThrows(UniflowException.class, () -> Parser.parse("rate CS2113 abc"));
    }

    @Test
    void parseRateCommand_missingModule() {
        assertThrows(UniflowException.class, () -> Parser.parse("rate"));
    }

    // Special review commands
    @Test
    void parseLoadReviewsCommand_returnsCorrectCommand() throws UniflowException {
        Command command = Parser.parse("load reviews");
        assertInstanceOf(LoadReviewsCommand.class, command);
    }

    @Test
    void parseResetReviewsCommand_returnsCorrectCommand() throws UniflowException {
        Command command = Parser.parse("reset all reviews");
        assertInstanceOf(ResetReviewsCommand.class, command);
    }

    @Test
    void parseCountReviewsCommand_byUser() throws UniflowException {
        Command command = Parser.parse("amount reviews u/John");
        assertInstanceOf(CountReviewsCommand.class, command);
    }

    @Test
    void parseCountReviewsCommand_byCourse() throws UniflowException {
        Command command = Parser.parse("amount reviews c/CS2113");
        assertInstanceOf(CountReviewsCommand.class, command);
    }

    @Test
    void parseAddReviewsDatabaseCommand_returnsCorrectCommand() throws UniflowException {
        Command command = Parser.parse("add reviews database");
        assertInstanceOf(AddReviewsDatabaseCommand.class, command);
    }

    // Case sensitivity tests
    @Test
    void parse_commandsAreCaseSensitive() {
        assertThrows(UniflowException.class, () -> Parser.parse("BYE"));
        assertThrows(UniflowException.class, () -> Parser.parse("LIST"));
        assertThrows(UniflowException.class, () -> Parser.parse("GPA"));
    }

    // Edge cases
    @Test
    void parse_commandWithExtraSpaces_handlesCorrectly() throws UniflowException {
        Command command = Parser.parse("   list   ");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void parse_insertCommandWithExtraSpaces_handlesCorrectly() throws UniflowException {
        Command command = Parser.parse("insert   i/CS2113   n/SE   d/Monday   f/09:00   t/10:00");
        assertInstanceOf(InsertCommand.class, command);
    }
}
