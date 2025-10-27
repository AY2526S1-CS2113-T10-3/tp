package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.contains;

class DeleteCommandTest {

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
    void execute_deletesModuleSuccessfully() throws UniflowException {
        // Arrange
        String moduleId = "CS1010";
        Module mockModule = new Module(moduleId, "Programming", "Monday", "10:00", "12:00", "Lecture");

        when(mockModuleList.deleteModuleById(moduleId)).thenReturn(mockModule);
        when(mockModuleList.getSize()).thenReturn(2);

        DeleteCommand cmd = new DeleteCommand(moduleId);

        // Act
        cmd.execute(mockUI, mockModuleList, mockCourseRecord);

        // Assert
        verify(mockModuleList).deleteModuleById(moduleId);
        verify(mockUI).showMessage(contains("Noted. I've removed this module:"));
        verify(mockUI).showMessage(contains(moduleId));
        verify(mockUI).showMessage(contains("2 module(s) left"));
    }

    @Test
    void isExit_returnsFalse() {
        DeleteCommand cmd = new DeleteCommand("CS2040");
        assert(!cmd.isExit());
    }
}
