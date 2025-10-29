package seedu.duke;

public final class RatingStats {
    private int sum;
    private int count;

    public RatingStats() {}

    public RatingStats(int sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public void add(int score) {
        sum += score;
        count++;
    }

    public double average() {
        return count == 0 ? 0.0 :  (double) sum / count;
    }

    public int getCount() {
        return count;
    }

    public int getSum() {
        return sum;
    }
}
