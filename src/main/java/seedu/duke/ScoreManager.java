package seedu.duke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreManager {
    private final ScoreStorage storage = new ScoreStorage();
    private Map<String, List<Integer>> moduleScores = new HashMap<>();

    public ScoreManager() {
        Map<String, List<Integer>> loaded = storage.load();
        this.moduleScores = (loaded != null) ? loaded : new HashMap<>();
    }

    /**
     * Saves (replaces) the score breakdown for the given module code.
     * Normalizes key to uppercase and persists to storage.
     *
     * @param moduleCode module identifier (e.g., CS2113)
     * @param breakdown  list of non-null integers (e.g., [50, 30, 20])
     * @throws IllegalArgumentException if moduleCode is blank or breakdown is null/contains nulls
     */
    public void addBreakdown(String moduleCode, List<Integer> breakdown) {
        if (moduleCode == null || moduleCode.trim().isEmpty()) {
            throw new IllegalArgumentException("moduleCode cannot be empty");
        }
        if (breakdown == null) {
            throw new IllegalArgumentException("breakdown cannot be null");
        }
        for (Integer v : breakdown) {
            if (v == null) {
                throw new IllegalArgumentException("breakdown cannot contain null values");
            }
        }

        String key = moduleCode.trim().toUpperCase();
        moduleScores.put(key, new ArrayList<>(breakdown));
        storage.save(moduleScores);
    }

    /**
     * Returns an immutable copy of the stored map.
     */
    public Map<String, List<Integer>> getModuleBreakdowns() {
        Map<String, List<Integer>> copy = new HashMap<>();
        for (Map.Entry<String, List<Integer>> e : moduleScores.entrySet()) {
            copy.put(e.getKey(), new ArrayList<>(e.getValue()));
        }
        return copy;
    }

    /**
     * Convenience getter for a single module breakdown (copy).
     */
    public List<Integer> getBreakdown(String moduleCode) {
        if (moduleCode == null) {
            return new ArrayList<>();
        }
        String key = moduleCode.trim().toUpperCase();
        List<Integer> list = moduleScores.get(key);
        return (list == null) ? new ArrayList<>() : new ArrayList<>(list);
    }
}
