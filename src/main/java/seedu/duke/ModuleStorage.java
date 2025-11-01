package seedu.duke;

import java.io.*;
import java.util.Scanner;

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

    public ModuleList loadModules() {
        ModuleList loadedModules = new ModuleList();
        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();

            //create a new folder if it doesn't exist yet
            if (!parentDir.exists()) {
                parentDir.mkdirs();
                System.out.println("A new folder for grade record has been created.");
            }
            //create a new file if it doesn't exist yet
            if (file.createNewFile()) {
                //System.out.println("Grade record file has been created.");
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(" \\| ");
                String ID = parts[0].trim();
                String name = parts[1].trim();
                String day = parts[2].trim();
                String startTime = parts[3].trim();
                String endTime = parts[4].trim();
                String sessionType = parts[5] != null ? parts[5].toLowerCase() : "lecture";

                loadedModules.addModule(new Module(ID, name, day, startTime, endTime, sessionType));

            }
            scanner.close();
        } catch (IOException | UniflowException e) {
            System.out.println("Error occurs when loading: " + e.getMessage());
        }
        return loadedModules;
    }


}
