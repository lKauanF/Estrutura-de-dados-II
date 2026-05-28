import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL implements WordStructure {
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

        updateHeight(node);

        int balance = getBalance(node);

        if (balance > 1) {
            metrics.incrementComparisons();

            if (word.compareTo(node.getLeft().getRecord().getWord()) < 0) {
                return rotateRight(node, metrics);
            }

            node.setLeft(rotateLeft(node.getLeft(), metrics));
            metrics.incrementAssignments();

            return rotateRight(node, metrics);
        }

        if (balance < -1) {
            metrics.incrementComparisons();

            if (word.compareTo(node.getRight().getRecord().getWord()) > 0) {
                return rotateLeft(node, metrics);
            }

            node.setRight(rotateRight(node.getRight(), metrics));
            metrics.incrementAssignments();

            return rotateLeft(node, metrics);
        }

        return node;
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        return node.getHeight();
    }

    private int getBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }

        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    private void updateHeight(TreeNode node) {
        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());

        node.setHeight(1 + Math.max(leftHeight, rightHeight));
    }

    private TreeNode rotateRight(TreeNode node, Metrics metrics) {
        TreeNode newRoot = node.getLeft();
        TreeNode movedSubtree = newRoot.getRight();

        newRoot.setRight(node);
        node.setLeft(movedSubtree);

        updateHeight(node);
        updateHeight(newRoot);

        metrics.addAssignments(4);

        return newRoot;
    }

    private TreeNode rotateLeft(TreeNode node, Metrics metrics) {
        TreeNode newRoot = node.getRight();
        TreeNode movedSubtree = newRoot.getLeft();

        newRoot.setLeft(node);
        node.setRight(movedSubtree);

        updateHeight(node);
        updateHeight(newRoot);

        metrics.addAssignments(4);

        return newRoot;
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