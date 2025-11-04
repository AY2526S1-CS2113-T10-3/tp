package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.any;

class InsertCommandTest {

    private UI mockUI;
    private ModuleList mockModuleList;
    private CourseRecord mockCourseRecord;

    @BeforeEach
    void setUp() {
        mockUI = mock(UI.class);
        mockModuleList = mock(ModuleList.class);
        mockCourseRecord = mock(CourseRecord.class);
    }

    @Test
    void execute_addsModuleWhenNoClash() throws UniflowException {
        // Arrange
        InsertCommand cmd = new InsertCommand("CS1010", "Programming", "Monday", "10:00", "12:00", "Lecture");

        when(mockModuleList.findClash(any())).thenReturn(null); // no clash

        // Act
        cmd.execute(mockUI, mockModuleList, mockCourseRecord);

        // Assert
        verify(mockModuleList).addModule(any(Module.class)); // ensures addModule was called
        verify(mockUI).showMessage(contains("Got it!")); // ensures success message was printed
    }

    @Test
    void execute_showsWarningAndAddsWhenUserSaysYes() throws UniflowException {
        // Arrange
        Module clashModule = new Module("CS2040", "Data Structures", "Monday", "10:00", "12:00", "Lecture");
        InsertCommand cmd = new InsertCommand("CS2030", "OOP", "Monday", "10:00", "12:00", "Lecture");

        when(mockModuleList.findClash(any())).thenReturn(clashModule);
        when(mockUI.readCommand()).thenReturn("yes");

        // Act
        cmd.execute(mockUI, mockModuleList, mockCourseRecord);

        // Assert
        verify(mockUI).showMessage(contains("clashes with"));
        verify(mockModuleList).addModule(any(Module.class));
    }

    @Test
    void execute_showsWarningAndCancelsWhenUserSaysNo() throws UniflowException {
        // Arrange
        Module clashModule = new Module("CS2040", "Data Structures", "Monday", "10:00", "12:00", "Lecture");
        InsertCommand cmd = new InsertCommand("CS2030", "OOP", "Monday", "10:00", "12:00", "Lecture");

        when(mockModuleList.findClash(any())).thenReturn(clashModule);
        when(mockUI.readCommand()).thenReturn("no");

        // Act
        cmd.execute(mockUI, mockModuleList, mockCourseRecord);

        // Assert
        verify(mockUI).showMessage(contains("Module not added."));
        verify(mockModuleList, never()).addModule(any());
    }

    @Test
    void isExit_returnsFalse() {
        InsertCommand cmd = new InsertCommand("CS1010", "Programming", "Monday", "10:00", "12:00", "Lecture");
        assert(!cmd.isExit());
    }
}
