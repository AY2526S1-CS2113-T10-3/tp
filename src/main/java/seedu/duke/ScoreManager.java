package seedu.duke;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private final ScoreStorage storage = new ScoreStorage();
    private final Map<String, Map<String, Integer>> scoresByModule;

    public ScoreManager() {
        Map<String, Map<String, Integer>> loaded = storage.load();
        this.scoresByModule = (loaded != null) ? loaded : new HashMap<>();
    }

    public void saveBreakdown(String moduleCode, Map<String, Integer> breakdown) {
        String key = normalize(moduleCode);
        Map<String, Integer> copy = new HashMap<>(breakdown);
        scoresByModule.put(key, copy);
        storage.save(scoresByModule);
    }

    public Map<String, Integer> getBreakdown(String moduleCode) {
        Map<String, Integer> m = scoresByModule.get(normalize(moduleCode));
        if (m == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(m);
    }

    public boolean hasBreakdown(String moduleCode) {
        return scoresByModule.containsKey(normalize(moduleCode))
                && !scoresByModule.get(normalize(moduleCode)).isEmpty();
    }

    private static String normalize(String code) {
        return code == null ? "" : code.trim().toUpperCase();
    }


    public void removeBreakdown(String code) {
        if (code == null) {
            return;
        }
        String key = code.toUpperCase().trim();
        scoresByModule.remove(key);
        storage.save(scoresByModule);
    }
}
