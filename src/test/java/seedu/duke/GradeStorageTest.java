package seedu.duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GradeStorageTest {

    private static final String TEST_FILE_PATH = "data/grades.txt";
    private GradeStorage gradeStorage;
    private CourseRecord courseRecord;

    @BeforeEach
    void setUp() {
        gradeStorage = new GradeStorage();
        courseRecord = new CourseRecord();
        cleanupTestFiles();
    }

    @AfterEach
    void tearDown() {
        cleanupTestFiles();
    }

    private void cleanupTestFiles() {
        try {
            Path path = Paths.get(TEST_FILE_PATH);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    @Test
    void saveGradeRecordEmptyRecordCreatesEmptyFile() {
        // Save empty record
        gradeStorage.saveGradeRecord(courseRecord);

        // Check if file exists
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "File should be created even for empty record");
    }

    @Test
    void saveAndLoadGradeRecordPreservesAllFields() {
        // Create a course with all fields
        Course course = new Course("CS2113", 4, "A", true);
        courseRecord.addCourse(course);

        // Save the record
        gradeStorage.saveGradeRecord(courseRecord);

        // Load the record
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();

        // Verify the record is not null
        assertNotNull(loadedRecord, "Loaded record should not be null");

        // Verify the course list is not empty
        assertFalse(loadedRecord.getCourseRecord().isEmpty(), "Course list should not be empty");

        // Get the loaded course
        Course loadedCourse = loadedRecord.getCourseRecord().get(0);
        assertNotNull(loadedCourse, "Loaded course should not be null");

        // Verify all fields
        assertEquals("CS2113", loadedCourse.getCode(), "Course code should match");
        assertEquals(4, loadedCourse.getCredits(), "Credits should match");
        assertEquals("A", loadedCourse.getGrade(), "Grade should match");
        assertTrue(loadedCourse.isMajor(), "Major status should match");
    }

    @Test
    void saveAndLoadGradeRecordMultipleCourses() {
        // Add multiple courses
        courseRecord.addCourse(new Course("CS2113", 4, "A", true));
        courseRecord.addCourse(new Course("CS2101", 4, "B+", true));
        courseRecord.addCourse(new Course("GEH1001", 4, "A-", false));

        // Save the record
        gradeStorage.saveGradeRecord(courseRecord);

        // Load the record
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();

        assertNotNull(loadedRecord);
        assertEquals(3, loadedRecord.getCourseRecord().size(), "Should have 3 courses");

        // Verify first course
        Course course1 = loadedRecord.getCourseRecord().get(0);
        assertEquals("CS2113", course1.getCode());
        assertEquals(4, course1.getCredits());
        assertEquals("A", course1.getGrade());
        assertTrue(course1.isMajor());

        // Verify second course
        Course course2 = loadedRecord.getCourseRecord().get(1);
        assertEquals("CS2101", course2.getCode());
        assertEquals("B+", course2.getGrade());

        // Verify third course
        Course course3 = loadedRecord.getCourseRecord().get(2);
        assertEquals("GEH1001", course3.getCode());
        assertFalse(course3.isMajor());
    }

    @Test
    void loadGradeRecordNonExistentFileReturnsEmpty() {
        // Ensure file doesn't exist
        cleanupTestFiles();

        // Load from non-existent file (will create empty file)
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();

        assertNotNull(loadedRecord, "Should return empty record, not null");
        assertTrue(loadedRecord.getCourseRecord().isEmpty(), "Record should be empty");
    }

    @Test
    void saveGradeRecordOverwritesExistingFile() {
        // Save first record
        courseRecord.addCourse(new Course("CS2113", 4, "A", true));
        gradeStorage.saveGradeRecord(courseRecord);

        // Create new record and save
        CourseRecord newRecord = new CourseRecord();
        newRecord.addCourse(new Course("CS2101", 4, "B", true));
        gradeStorage.saveGradeRecord(newRecord);

        // Load and verify
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();
        assertEquals(1, loadedRecord.getCourseRecord().size());
        assertEquals("CS2101", loadedRecord.getCourseRecord().get(0).getCode());
    }

    @Test
    void saveGradeRecordHandlesSpecialCharacters() {
        // Add course with special characters (if allowed)
        Course course = new Course("CS2113T", 4, "A+", true);
        courseRecord.addCourse(course);

        gradeStorage.saveGradeRecord(courseRecord);
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();

        assertNotNull(loadedRecord);
        assertEquals("CS2113T", loadedRecord.getCourseRecord().get(0).getCode());
        assertEquals("A+", loadedRecord.getCourseRecord().get(0).getGrade());
    }

    @Test
    void saveGradeRecordHandlesDifferentGrades() {
        // Test different grade formats
        courseRecord.addCourse(new Course("CS2113", 4, "A+", true));
        courseRecord.addCourse(new Course("CS2101", 4, "B-", true));
        courseRecord.addCourse(new Course("GEH1001", 4, "C", false));
        courseRecord.addCourse(new Course("MA1521", 4, "S", false)); // S/U grade

        gradeStorage.saveGradeRecord(courseRecord);
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();

        assertEquals(4, loadedRecord.getCourseRecord().size());
        assertEquals("A+", loadedRecord.getCourseRecord().get(0).getGrade());
        assertEquals("B-", loadedRecord.getCourseRecord().get(1).getGrade());
        assertEquals("C", loadedRecord.getCourseRecord().get(2).getGrade());
        assertEquals("S", loadedRecord.getCourseRecord().get(3).getGrade());
    }

    @Test
    void saveGradeRecordHandlesMajorAndNonMajor() {
        courseRecord.addCourse(new Course("CS2113", 4, "A", true));
        courseRecord.addCourse(new Course("GEH1001", 4, "B", false));

        gradeStorage.saveGradeRecord(courseRecord);
        CourseRecord loadedRecord = gradeStorage.loadGradeRecord();

        assertTrue(loadedRecord.getCourseRecord().get(0).isMajor());
        assertFalse(loadedRecord.getCourseRecord().get(1).isMajor());
    }
}
