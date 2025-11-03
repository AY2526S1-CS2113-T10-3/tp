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
    void parseInsertCommandMissingId() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert n/CS2113 d/Monday st/1000 et/1200");
        });
    }

    @Test
    void parseInsertCommandMissingName() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 d/Monday st/1000 et/1200");
        });
    }

    @Test
    void parseInsertCommandMissingDay() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering st/1000 et/1200");
        });
    }

    @Test
    void parseInsertCommandMissingStartTime() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering d/Monday et/1200");
        });
    }

    @Test
    void parseInsertCommandMissingEndTime() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering d/Monday st/1000");
        });
    }

    @Test
    void parseInsertCommandInvalidDay() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering d/Funday st/1000 et/1200");
        });
    }

    @Test
    void parseInsertCommandInvalidTimeFormat() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering d/Monday st/10:00 et/12:00");
        });
    }

    @Test
    void parseInsertCommandEndBeforeStart() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering d/Monday st/1200 et/1000");
        });
    }

    @Test
    void parseInsertCommandSameStartAndEnd() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("insert i/CS2113 n/Software Engineering d/Monday st/1000 et/1000");
        });
    }

    @Test
    void parseDeleteCommandMissingIndex() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("delete");
        });
    }

    @Test
    void parseDeleteCommandInvalidFormat() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("delete abc");
        });
    }

    @Test
    void parseDeleteCommandNonNumericIndex() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("delete abc");
        });
    }

    @Test
    void parseFilterCommandEmptyValue() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("filter n/");
        });
    }

    @Test
    void parseFilterCommandMissingParameters() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("filter");
        });
    }

    @Test
    void parseAddGradeCommandMissingCode() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade mc/4 g/A m/yes");
        });
    }

    @Test
    void parseAddGradeCommandMissingCredits() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 g/A m/yes");
        });
    }

    @Test
    void parseAddGradeCommandMissingGrade() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 mc/4 m/yes");
        });
    }

    @Test
    void parseAddGradeCommandMissingMajor() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 mc/4 g/A");
        });
    }

    @Test
    void parseAddGradeCommandInvalidGrade() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 mc/4 g/Z m/yes");
        });
    }

    @Test
    void parseAddGradeCommandInvalidCredits() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 mc/abc g/A m/yes");
        });
    }

    @Test
    void parseAddGradeCommandNegativeCredits() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 mc/-4 g/A m/yes");
        });
    }

    @Test
    void parseAddGradeCommandInvalidMajor() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("addgrade c/CS2113 mc/4 g/A m/invalid");
        });
    }

    @Test
    void parseRemoveGradeCommandMissingIndex() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("removegrade");
        });
    }

    @Test
    void parseRemoveGradeCommandInvalidIndex() {
        Parser parser = new Parser();
        assertThrows(Exception.class, () -> {
            parser.parse("removegrade abc");
        });
    }
}
