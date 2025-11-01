package seedu.duke;

import java.util.HashMap;
import java.util.Map;

public class RatingManager {
    private final RatingStorage storage = new  RatingStorage();
    private final Map<String, RatingStats> moduleRatings;

    public RatingManager() {
        Map<String, RatingStats> loaded = storage.load();
        this.moduleRatings = (loaded != null) ? loaded : new HashMap<>();
    }

    public void addRating(String moduleCode, int score) {
        String key = moduleCode.trim().toUpperCase();
        moduleRatings.computeIfAbsent(key, c -> new RatingStats()).add(score);
        storage.save(moduleRatings);
    }

    public int getCount(String moduleCode) {
        RatingStats stat = moduleRatings.get(moduleCode.trim().toUpperCase());
        return stat == null ? 0 : stat.getCount();
    }

    public Double getAverage(String moduleCode) {
        RatingStats stat = moduleRatings.get(moduleCode.trim().toUpperCase());
        return stat == null ? 0.0 : stat.average();
    }

    public Map<String, RatingStats> getModuleRatings() {
        return moduleRatings;
    }
}

