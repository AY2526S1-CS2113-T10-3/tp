package seedu.duke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public class ScoreStorage {
    private static final String FILE_PATH = "data/scores.txt";

    public Map<String, Map<String, Integer>> load() {
        Map<String, Map<String, Integer>> all = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            return all;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length != 2) {
                    continue;
                }
                String module = parts[0].trim().toUpperCase();
                String payload = parts[1].trim();
                Map<String, Integer> breakdown = parsePayload(payload);
                if (!breakdown.isEmpty()) {
                    all.put(module, breakdown);
                }
            }
        } catch (IOException ignore) {
            System.out.println("Error reading file: " + FILE_PATH);
        }
        return all;
    }

    public void save(Map<String, Map<String, Integer>> all) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Map<String, Integer>> e : all.entrySet()) {
                String module = e.getKey().toUpperCase();
                String payload = toPayload(e.getValue());
                bw.write(module + "|" + payload);
                bw.newLine();
            }
        } catch (IOException ignore) {
            System.out.println("Error writing file: " + FILE_PATH);
        }
    }

    private static Map<String, Integer> parsePayload(String payload) {
        Map<String, Integer> map = new LinkedHashMap<>();
        if (payload.isEmpty()) {
            return map;
        }
        String[] pairs = payload.split(",");
        for (String p : pairs) {
            String s = p.trim();
            if (s.isEmpty()) {
                continue;
            }
            String[] kv = s.split(":", 2);
            if (kv.length != 2) {
                continue;
            }
            String key = kv[0].trim();
            String valStr = kv[1].trim();
            try {
                int val = Integer.parseInt(valStr);
                map.put(key, val);
            } catch (NumberFormatException ignore) {
                //skip
            }
        }
        return map;
    }

    private static String toPayload(Map<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append(e.getKey()).append(":").append(e.getValue());
        }
        return sb.toString();
    }
}
