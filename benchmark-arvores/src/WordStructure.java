import java.util.List;

public interface WordStructure {
    void insert(String word, Metrics metrics);

    List<WordRecord> getOrderedRecords();
}