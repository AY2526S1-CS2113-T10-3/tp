package seedu.duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * It is used to test whether exceptions are thrown when there are invalid user inputs.
 */
public class ParserAddGradeTest {

    @Test
    public void parseAddGrade_missingField_throwsException() {
        assertThrows(UniflowException.class, () ->
                // missing credits component here
                Parser.parse("addgrade c/CS2113 g/A m/true"));
    }

    @Test
    public void parseAddGrade_emptyField_throwsException() {
        assertThrows(UniflowException.class, () ->
                // credits component is empty here
                Parser.parse("addgrade c/CS2113 cr/ g/A m/true"));
    }

    @Test
    public void parseAddGrade_zeroCredits_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/0 g/A m/true"));
    }

    @Test
    public void parseAddGrade_negativeCredits_throwsException() {
        assertThrows(UniflowException.class, () ->
                //test "addtestgrade" as well
                Parser.parse("addtestgrade c/CS2113 cr/-5 g/A m/true"));
    }

    @Test
    public void parseAddGrade_invalidGrade_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/5 g/Z+ m/false"));
    }

    @Test
    public void parseAddGrade_invalidMajorInput_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addgrade c/CS2113 cr/5 g/A m/test"));
    }

    @Test
    public void parseAddGrade_invalidInputType_throwsException() {
        assertThrows(UniflowException.class, () ->
                Parser.parse("addtestgrade c/CS2113 cr/1.1 g/A m/true"));
    }

}

