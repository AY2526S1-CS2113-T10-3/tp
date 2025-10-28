package seedu.duke;

import java.util.HashMap;
import java.util.Map;

public class RatingManager {
    private final Map<String, RatingStats> moduleRating = new HashMap<>();

    public void addRating(String moduleCode, int score) {
        moduleRating.computeIfAbsent(moduleCode.toLowerCase(), c -> new RatingStats()).add(score);
    }

    public int getCount(String moduleCode) {
        RatingStats stat = moduleRating.get(moduleCode.toLowerCase());
        return stat == null ? 0 : stat.getCount();
    }

    public Double getAverage(String moduleCode) {
        RatingStats stat = moduleRating.get(moduleCode.toLowerCase());
        return stat == null ? 0.0 : stat.average();
    }

    public Map<String, RatingStats> getModuleRating() {
        return moduleRating;
    }
}

