package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class UniflowTest {

    @TempDir
    Path tempDir;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("bye\n".getBytes());
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outputStream.reset();
    }

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    void constructor_validFilePath_createsUniflow() {
        assertDoesNotThrow(() -> new Uniflow("test_data"));
    }

    @Test
    void getReviewManager_returnsNonNull() {
        assertNotNull(Uniflow.getReviewManager());
    }

    @Test
    void getRatingManager_returnsNonNull() {
        assertNotNull(Uniflow.getRatingManager());
    }

    @Test
    void getTempRecord_returnsNonNull() {
        assertNotNull(Uniflow.getTempRecord());
    }

    @Test
    void getScoreManager_returnsNonNull() {
        assertNotNull(Uniflow.getScoreManager());
    }

    @Test
    void getTempRecord_returnsSameInstance() {
        CourseRecord first = Uniflow.getTempRecord();
        CourseRecord second = Uniflow.getTempRecord();
        assertSame(first, second);
    }

    @Test
    void run_exitCommand_terminatesGracefully() {
        String input = "bye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Uniflow uniflow = new Uniflow("test_data");
        assertDoesNotThrow(uniflow::run);
    }

    @Test
    void run_invalidCommand_showsError() {
        String input = "invalid\nbye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Uniflow uniflow = new Uniflow("test_data");
        uniflow.run();

        String output = outputStream.toString();
        assertTrue(output.toLowerCase().contains("invalid") ||
                output.toLowerCase().contains("error"));
    }
}
