package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModuleListTest {
    private ModuleList moduleList;
    private Module testModule1;
    private Module testModule2;
    private Module tutorialModule;

    @BeforeEach
    public void setUp() {
        moduleList = new ModuleList();
        testModule1 = new Module("CS2113", "Software Engineering", "Monday", "14:00", "16:00", "lecture");
        testModule2 = new Module("CS2040", "Data Structures", "Tuesday", "10:00", "12:00", "lecture");
        tutorialModule = new Module("CS2030", "OOP", "Wednesday", "09:00", "10:00", "tutorial");
    }

    @Test
    public void addModule_validModule_increasesSize() throws UniflowException{
        assertEquals(0, moduleList.getSize());
        moduleList.addModule(testModule1);
        assertEquals(1, moduleList.getSize());
        assertFalse(moduleList.isEmpty());
    }

    @Test
    public void addModule_nullModule_throwsException() {
        assertThrows(UniflowException.class, () -> moduleList.addModule(null));
    }

    @Test
    public void getModule_validIndex_returnsModule() throws UniflowException {
        moduleList.addModule(testModule1);
        Module retrieved = moduleList.getModule(0);
        assertEquals(testModule1, retrieved);
    }

    @Test
    public void getModule_invalidIndex_throwsException() throws UniflowException{
        moduleList.addModule(testModule1);
        assertThrows(UniflowException.class, () -> moduleList.getModule(5));
    }

    @Test
    public void getModule_negativeIndex_throwsException() {
        assertThrows(UniflowException.class, () -> moduleList.getModule(-1));
    }

    @Test
    public void getModuleByID_existingModule_returnsModule() throws UniflowException {
        moduleList.addModule(testModule1);
        Module retrieved = moduleList.getModuleByID("CS2113");
        assertEquals(testModule1, retrieved);
    }

    @Test
    public void getModuleByID_nonExistingModule_throwsException() throws UniflowException{
        moduleList.addModule(testModule1);
        assertThrows(UniflowException.class, () -> moduleList.getModuleByID("CS9999"));
    }

    @Test
    public void doesExist_existingModule_returnsTrue() throws UniflowException {
        moduleList.addModule(testModule1);
        assertTrue(moduleList.doesExist("CS2113"));
    }

    @Test
    public void doesExist_nonExistingModule_returnsFalse() throws UniflowException {
        moduleList.addModule(testModule1);
        assertFalse(moduleList.doesExist("CS9999"));
    }

    @Test
    public void deleteModuleById_existingModule_removesModule() throws UniflowException {
        moduleList.addModule(testModule1);
        moduleList.addModule(testModule2);

        Module deleted = moduleList.deleteModuleById("CS2113");

        assertEquals(testModule1, deleted);
        assertEquals(1, moduleList.getSize());
        assertFalse(moduleList.doesExist("CS2113"));
    }

    @Test
    public void deleteModuleById_caseInsensitive_removesModule() throws UniflowException {
        moduleList.addModule(testModule1);
        Module deleted = moduleList.deleteModuleById("cs2113");
        assertEquals(testModule1, deleted);
    }

    @Test
    public void deleteModuleById_nonExistingModule_throwsException() {
        assertThrows(UniflowException.class, () -> moduleList.deleteModuleById("CS9999"));
    }

    @Test
    public void findBySessionType_existingType_returnsFilteredList() throws UniflowException {
        moduleList.addModule(testModule1);
        moduleList.addModule(tutorialModule);

        ModuleList filtered = moduleList.findBySessionType("tutorial");
        assertEquals(1, filtered.getSize());
    }

    @Test
    public void findByDay_existingDay_returnsFilteredList() throws UniflowException {
        moduleList.addModule(testModule1);
        moduleList.addModule(testModule2);

        ModuleList filtered = moduleList.findByDay("Monday");
        assertEquals(1, filtered.getSize());
    }

    @Test
    public void findByDay_caseInsensitive_returnsFilteredList() throws UniflowException {
        moduleList.addModule(testModule1);
        ModuleList filtered = moduleList.findByDay("monday");
        assertEquals(1, filtered.getSize());
    }

    @Test
    public void findById_partialMatch_returnsFilteredList() throws UniflowException {
        moduleList.addModule(testModule1);
        moduleList.addModule(testModule2);

        ModuleList filtered = moduleList.findById("CS2");
        assertEquals(2, filtered.getSize());
    }

    @Test
    public void findByName_partialMatch_returnsFilteredList() throws UniflowException {
        moduleList.addModule(testModule1);
        moduleList.addModule(testModule2);

        ModuleList filtered = moduleList.findByName("Software");
        assertEquals(1, filtered.getSize());
    }

    @Test
    public void findClash_overlappingTime_returnsClashingModule() throws UniflowException {
        moduleList.addModule(testModule1); // Monday 14:00-16:00
        Module clash = new Module("CS2110", "Programming", "Monday", "15:00", "17:00", "lecture");

        Module clashFound = moduleList.findClash(clash);
        assertNotNull(clashFound);
        assertEquals(testModule1, clashFound);
    }

    @Test
    public void findClash_sameStartTime_returnsClashingModule() throws UniflowException {
        moduleList.addModule(testModule1); // Monday 14:00-16:00
        Module clash = new Module("CS2110", "Programming", "Monday", "14:00", "15:00", "lecture");

        Module clashFound = moduleList.findClash(clash);
        assertNotNull(clashFound);
    }

    @Test
    public void findClash_differentDay_returnsNull() throws UniflowException {
        moduleList.addModule(testModule1); // Monday 14:00-16:00
        Module noClash = new Module("CS2110", "Programming", "Tuesday", "14:00", "16:00", "lecture");

        Module clashFound = moduleList.findClash(noClash);
        assertNull(clashFound);
    }

    @Test
    public void findClash_noOverlap_returnsNull() throws UniflowException {
        moduleList.addModule(testModule1); // Monday 14:00-16:00
        Module noClash = new Module("CS2110", "Programming", "Monday", "16:00", "18:00", "lecture");

        Module clashFound = moduleList.findClash(noClash);
        assertNull(clashFound);
    }

    @Test
    public void clear_nonEmptyList_becomesEmpty() throws UniflowException {
        moduleList.addModule(testModule1);
        moduleList.addModule(testModule2);

        moduleList.clear();

        assertTrue(moduleList.isEmpty());
        assertEquals(0, moduleList.getSize());
    }

    @Test
    public void getAllModules_returnsNewList() throws UniflowException {
        moduleList.addModule(testModule1);
        var modules = moduleList.getAllModules();

        // Modifying returned list should not affect original
        modules.clear();
        assertEquals(1, moduleList.getSize());
    }
}
