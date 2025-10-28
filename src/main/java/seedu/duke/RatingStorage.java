package seedu.duke;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingStorage {
    private static final String FILE_PATH = "data/ratings.txt";

    public Map<String, RatingStats> load() {
        Map<String, RatingStats> ratings = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            return ratings;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 3) {
                    continue;
                }
                String module = parts[0].trim().toUpperCase();
                try {
                    int sum = Integer.parseInt(parts[1]);
                    int count = Integer.parseInt(parts[2]);
                    ratings.put(module, new RatingStats(sum, count));
                } catch (NumberFormatException e) {
                    //ignore
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + FILE_PATH);
        }
        return ratings;
    }

    public void save(Map<String, RatingStats> ratings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for  (Map.Entry<String, RatingStats> entry : ratings.entrySet()) {
                String course = entry.getKey();
                RatingStats stats = entry.getValue();
                bw.write(course + "|" + stats.getSum() + "|" + stats.getCount() + "\n");
            }
        } catch(IOException e) {
            System.out.println("Error writing file: " + FILE_PATH);
        }
    }
}
