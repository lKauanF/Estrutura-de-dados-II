public class Metrics {
    private long comparisons;
    private long assignments;
    private double seconds;

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementAssignments() {
        assignments++;
    }

    public void addAssignments(long value) {
        assignments += value;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAssignments() {
        return assignments;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
}