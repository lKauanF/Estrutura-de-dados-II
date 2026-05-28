import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree implements WordStructure {
    private TreeNode root;

    @Override
    public void insert(String word, Metrics metrics) {
        root = insertNode(root, word, metrics);
    }

    private TreeNode insertNode(TreeNode node, String word, Metrics metrics) {
        if (node == null) {
            metrics.incrementAssignments();
            return new TreeNode(word);
        }

        metrics.incrementComparisons();

        if (word.equals(node.getRecord().getWord())) {
            node.getRecord().incrementFrequency();
            metrics.incrementAssignments();
            return node;
        }

        metrics.incrementComparisons();

        if (word.compareTo(node.getRecord().getWord()) < 0) {
            node.setLeft(insertNode(node.getLeft(), word, metrics));
            metrics.incrementAssignments();
        } else {
            node.setRight(insertNode(node.getRight(), word, metrics));
            metrics.incrementAssignments();
        }

        return node;
    }

    public TreeNode getRoot() {
        return root;
    }

    @Override
    public List<WordRecord> getOrderedRecords() {
        List<WordRecord> records = new ArrayList<>();
        inOrder(root, records);
        return records;
    }

    private void inOrder(TreeNode node, List<WordRecord> records) {
        if (node == null) {
            return;
        }

        inOrder(node.getLeft(), records);
        records.add(node.getRecord());
        inOrder(node.getRight(), records);
    }
}