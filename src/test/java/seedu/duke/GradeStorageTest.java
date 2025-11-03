package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GradeStorageTest {

    private static final String TEST_FILE_PATH = "data/grades.txt";
    private GradeStorage storage;
    private CourseRecord record;

    @BeforeEach
    void setUp() {
        storage = new GradeStorage();
        record = new CourseRecord();
        deleteTestFile();
    }

    @AfterEach
    void tearDown() {
        deleteTestFile();
    }

    private void deleteTestFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveGradeRecord_createsFile() {
        record.addCourse(new Course("CS2113", 4, "A", true));
        storage.saveGradeRecord(record);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    void saveAndLoadGradeRecord_preservesData() {
        record.addCourse(new Course("CS2113", 4, "A", true));
        record.addCourse(new Course("CS2040", 4, "B+", false));

        storage.saveGradeRecord(record);
        CourseRecord loaded = storage.loadGradeRecord();

        assertEquals(2, loaded.getSize());
        assertTrue(loaded.hasCourse("CS2113"));
        assertTrue(loaded.hasCourse("CS2040"));
    }

    @Test
    void loadGradeRecord_nonExistentFile_returnsEmptyRecord() {
        CourseRecord loaded = storage.loadGradeRecord();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void saveGradeRecord_emptyRecord_createsEmptyFile() {
        storage.saveGradeRecord(record);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    void saveAndLoadGradeRecord_preservesAllFields() {
        Course original = new Course("CS2113", 4, "A-", true);
        record.addCourse(original);

        storage.saveGradeRecord(record);
        CourseRecord loaded = storage.loadGradeRecord();

        Course loadedCourse = loaded.getCourse("CS2113");
        assertEquals(original.getCode(), loadedCourse.getCode());
        assertEquals(original.getCredits(), loadedCourse.getCredits());
        assertEquals(original.getGrade(), loadedCourse.getGrade());
        assertEquals(original.isMajor(), loadedCourse.isMajor());
    }

    @Test
    void saveAndLoadGradeRecord_majorAndNonMajor_distinguishes() {
        record.addCourse(new Course("CS2113", 4, "A", true));
        record.addCourse(new Course("GER1000", 4, "B", false));

        storage.saveGradeRecord(record);
        CourseRecord loaded = storage.loadGradeRecord();

        Course majorCourse = loaded.getCourse("CS2113");
        Course nonMajorCourse = loaded.getCourse("GER1000");

        assertTrue(majorCourse.isMajor());
        assertFalse(nonMajorCourse.isMajor());
    }
}
