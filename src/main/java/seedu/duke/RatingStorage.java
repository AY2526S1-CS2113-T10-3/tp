package seedu.duke;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingStorage {
    private static final String FILE_PATH = "data/ratings.txt";

    public Map<String, List<Integer>> load() {
        Map<String, List<Integer>> ratings = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return ratings;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String course = parts[0];
                    int score = Integer.parseInt(parts[2]);
                    ratings.computeIfAbsent(course, k -> new ArrayList<>()).add(score);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + FILE_PATH);
        }
        return ratings;
    }

    public void save(Map<String, List<Integer>> ratings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for  (Map.Entry<String, List<Integer>> entry : ratings.entrySet()) {
                String course = entry.getKey();
                for (Integer score: entry.getValue()) {
                    bw.write(course + "|" + score);
                    bw.newLine();
                }
            }
        } catch(IOException e) {
            System.out.println("Error writing file: " + FILE_PATH);
        }
    }
}
