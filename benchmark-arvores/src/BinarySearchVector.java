import java.util.ArrayList;
import java.util.List;

public class BinarySearchVector implements WordStructure {
    private final List<WordRecord> records = new ArrayList<>();

    @Override
    public void insert(String word, Metrics metrics) {
        int left = 0;
        int right = records.size() - 1;
        int position;
        boolean found = false;

        while (left <= right) {
            int middle = left + (right - left) / 2;

            metrics.incrementComparisons();

            if (records.get(middle).getWord().equals(word)) {
                records.get(middle).incrementFrequency();
                metrics.incrementAssignments();
                found = true;
                break;
            }

            metrics.incrementComparisons();

            if (records.get(middle).getWord().compareTo(word) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        if (!found) {
            position = left;

            records.add(null);
            metrics.incrementAssignments();

            for (int i = records.size() - 1; i > position; i--) {
                records.set(i, records.get(i - 1));
                metrics.incrementAssignments();
            }

            records.set(position, new WordRecord(word, 1));
            metrics.incrementAssignments();
        }
    }

    @Override
    public List<WordRecord> getOrderedRecords() {
        return new ArrayList<>(records);
    }
}