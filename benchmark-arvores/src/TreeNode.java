public class TreeNode {
    private final WordRecord record;
    private TreeNode left;
    private TreeNode right;
    private int height;

    public TreeNode(String word) {
        this.record = new WordRecord(word, 1);
        this.height = 1;
    }

    public WordRecord getRecord() {
        return record;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}