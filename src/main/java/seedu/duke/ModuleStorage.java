package seedu.duke;

import java.io.FileWriter;
import java.io.IOException;

public class ModuleStorage {
    private static final String FILE_PATH = "data/modules.txt";

    public void saveModules(ModuleList modules) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Module m : modules.getAllModules()) {
                fw.write(m.toStorage() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error occurs when saving: " + e.getMessage());
        }
    }


}
