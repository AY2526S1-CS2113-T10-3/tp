package seedu.duke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreStorage {
    private static final String FILE_PATH = "data/breakdowns.txt";

    /**
     * Loads score breakdowns from disk.
     * File format: MODULE|v1,v2,v3 (e.g., CS2113|50,30,20)
     * - MODULE is normalized to UPPERCASE.
     * - If a module appears multiple times, values are appended.
     */
    public Map<String, List<Integer>> load() {
        Map<String, List<Integer>> scores = new HashMap<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            return scores;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split("\\|", 2);
                if (parts.length != 2) {
                    continue;
                }

                String module = parts[0].trim().toUpperCase();
                String listPart = parts[1].trim();

                List<Integer> parsed = new ArrayList<>();
                if (!listPart.isEmpty()) {
                    String[] tokens = listPart.split(",");
                    for (String tok : tokens) {
                        String v = tok.trim();
                        if (v.isEmpty()) {
                            continue;
                        }
                        try {
                            parsed.add(Integer.parseInt(v));
                        } catch (NumberFormatException ignore) {
                            //skip invalid integers
                        }
                    }
                }

                if (parsed.isEmpty()) {
                    // Nothing to add for this line
                    continue;
                }

                List<Integer> existing = scores.get(module);
                if (existing == null) {
                    scores.put(module, parsed);
                } else {
                    existing.addAll(parsed);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + FILE_PATH);
        }

        return scores;
    }

    /**
     * Saves score breakdowns to disk, overwriting the file.
     * Each entry is written as: MODULE|v1,v2,v3
     */
    public void save(Map<String, List<Integer>> scores) {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, List<Integer>> entry : scores.entrySet()) {
                String module = entry.getKey() == null
                        ? ""
                        : entry.getKey().trim().toUpperCase();

                if (module.isEmpty()) {
                    continue;
                }

                List<Integer> values = entry.getValue();
                if (values == null || values.isEmpty()) {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < values.size(); i++) {
                    Integer v = values.get(i);
                    if (v == null) {
                        continue;
                    }
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(v.intValue());
                }

                if (sb.length() == 0) {
                    continue;
                }

                bw.write(module + "|" + sb);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + FILE_PATH);
        }
    }
}
