import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainInterface extends JFrame {
    private static final String DEFAULT_FILE_PATH = "entrada.txt";

    private final JLabel fileLabel;
    private final JComboBox<String> structureComboBox;
    private final JTextArea outputArea;
    private final TreePanel treePanel;

    private BinarySearchVector binarySearchVector;
    private BinarySearchTree binarySearchTree;
    private ArvoreAVL ArvoreAVL;

    public MainInterface() {
        setTitle("Benchmark de Pesquisa em Árvores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        fileLabel = new JLabel("Arquivo usado: " + DEFAULT_FILE_PATH);

        structureComboBox = new JComboBox<>(new String[]{
                "Árvore AVL",
                "Árvore Binária",
                "Pesquisa Binária"
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        treePanel = new TreePanel();

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton runButton = new JButton("Executar benchmark");
        JButton updateTreeButton = new JButton("Atualizar visualização");

        runButton.addActionListener(event -> runBenchmark());
        updateTreeButton.addActionListener(event -> updateTreeVisualization());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Estrutura para visualização:"));
        buttonPanel.add(structureComboBox);
        buttonPanel.add(runButton);
        buttonPanel.add(updateTreeButton);

        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(fileLabel, BorderLayout.CENTER);

        return panel;
    }

    private JSplitPane createMainPanel() {
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Resumo e frequências"));

        JScrollPane treeScrollPane = new JScrollPane(treePanel);
        treeScrollPane.setBorder(BorderFactory.createTitledBorder("Visualização da árvore"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, outputScrollPane, treeScrollPane);
        splitPane.setResizeWeight(0.45);

        return splitPane;
    }

    private void runBenchmark() {
        try {
            List<String> words = TextProcessor.readWordsFromFile(DEFAULT_FILE_PATH);

            if (words.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nenhuma palavra válida foi encontrada no arquivo.",
                        "Sem palavras",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            binarySearchVector = new BinarySearchVector();
            binarySearchTree = new BinarySearchTree();
            ArvoreAVL = new ArvoreAVL();

            BenchmarkResultado binaryResult = runStructureBenchmark("pesquisa binaria", binarySearchVector, words);
            BenchmarkResultado bstResult = runStructureBenchmark("arvore binaria", binarySearchTree, words);
            BenchmarkResultado avlResult = runStructureBenchmark("arvore avl", ArvoreAVL, words);

            List<BenchmarkResultado> results = new ArrayList<>();
            results.add(binaryResult);
            results.add(bstResult);
            results.add(avlResult);

            printResults(results);
            updateTreeVisualization();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao ler o arquivo \"" + DEFAULT_FILE_PATH + "\": " + exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private BenchmarkResultado runStructureBenchmark(String name, WordStructure structure, List<String> words) {
        Metrics metrics = new Metrics();

        long start = System.nanoTime();

        for (String word : words) {
            structure.insert(word, metrics);
        }

        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;
        metrics.setSeconds(seconds);

        return new BenchmarkResultado (name, metrics);
    }

    private void printResults(List<BenchmarkResultado> results) {
        results.sort(Comparator.comparingDouble(result -> result.getMetrics().getSeconds()));

        StringBuilder builder = new StringBuilder();

        builder.append("Resumo da execução\n");
        builder.append("\n\n");
        builder.append("Arquivo analisado: ").append(DEFAULT_FILE_PATH).append("\n\n");

        for (BenchmarkResultado result : results) {
            builder.append(result.getName()).append(":\n");
            builder.append("  ")
                    .append(result.getMetrics().getComparisons())
                    .append(" comparacoes\n");

            builder.append("  ")
                    .append(result.getMetrics().getAssignments())
                    .append(" atribuicoes\n");

            builder.append(String.format(
                    Locale.US,
                    "  %.6f segundos%n%n",
                    result.getMetrics().getSeconds()
            ));
        }

        builder.append("Frequencia das palavras\n");
        builder.append("=======================\n\n");

        List<WordRecord> records = getSelectedRecords();

        for (WordRecord record : records) {
            builder.append(record.getWord())
                    .append(": ")
                    .append(record.getFrequency())
                    .append("\n");
        }

        outputArea.setText(builder.toString());
        outputArea.setCaretPosition(0);
    }

    private List<WordRecord> getSelectedRecords() {
        String selectedStructure = (String) structureComboBox.getSelectedItem();

        if ("Árvore Binária".equals(selectedStructure)) {
            return binarySearchTree.getOrderedRecords();
        }

        if ("Árvore AVL".equals(selectedStructure)) {
            return ArvoreAVL.getOrderedRecords();
        }

        return binarySearchVector.getOrderedRecords();
    }

    private void updateTreeVisualization() {
        if (binarySearchTree == null || ArvoreAVL == null) {
            treePanel.setRoot(null, "Execute o benchmark para visualizar as árvores.");
            return;
        }

        String selectedStructure = (String) structureComboBox.getSelectedItem();

        if ("Árvore Binária".equals(selectedStructure)) {
            treePanel.setRoot(binarySearchTree.getRoot(), "Árvore Binária sem Balanceamento");
            return;
        }

        if ("Árvore AVL".equals(selectedStructure)) {
            treePanel.setRoot(ArvoreAVL.getRoot(), "Árvore AVL Balanceada");
            return;
        }

        treePanel.setRoot(null, "A pesquisa binária usa vetor ordenado, não possui árvore para desenhar.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainInterface mainGui = new MainInterface();
            mainGui.setVisible(true);
        });
    }
}

class TreePanel extends JPanel {
    private TreeNode root;
    private String message = "Execute o benchmark para visualizar as árvores.";

    public TreePanel() {
        setPreferredSize(new Dimension(1000, 700));
        setBackground(Color.WHITE);
    }

    public void setRoot(TreeNode root, String message) {
        this.root = root;
        this.message = message;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 16));
        graphics2D.drawString(message, 20, 30);

        if (root == null) {
            return;
        }

        int panelWidth = getWidth();

        drawTree(graphics2D, root, panelWidth / 2, 80, panelWidth / 4);
    }

    private void drawTree(Graphics2D graphics2D, TreeNode node, int x, int y, int horizontalGap) {
        if (node == null) {
            return;
        }

        int nextY = y + 80;
        int nextGap = Math.max(horizontalGap / 2, 35);

        if (node.getLeft() != null) {
            int childX = x - horizontalGap;
            drawLine(graphics2D, x, y, childX, nextY);
            drawTree(graphics2D, node.getLeft(), childX, nextY, nextGap);
        }

        if (node.getRight() != null) {
            int childX = x + horizontalGap;
            drawLine(graphics2D, x, y, childX, nextY);
            drawTree(graphics2D, node.getRight(), childX, nextY, nextGap);
        }

        drawNode(graphics2D, node, x, y);
    }

    private void drawLine(Graphics2D graphics2D, int x1, int y1, int x2, int y2) {
        graphics2D.setColor(new Color(120, 120, 120));
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawLine(x1, y1, x2, y2);
    }

    private void drawNode(Graphics2D graphics2D, TreeNode node, int x, int y) {
        int width = 90;
        int height = 42;

        int startX = x - width / 2;
        int startY = y - height / 2;

        graphics2D.setColor(new Color(230, 240, 255));
        graphics2D.fillRoundRect(startX, startY, width, height, 18, 18);

        graphics2D.setColor(new Color(40, 80, 140));
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawRoundRect(startX, startY, width, height, 18, 18);

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 12));

        String word = node.getRecord().getWord();

        if (word.length() > 9) {
            word = word.substring(0, 8) + ".";
        }

        String frequency = "x" + node.getRecord().getFrequency();

        graphics2D.drawString(word, startX + 10, startY + 18);
        graphics2D.drawString(frequency, startX + 10, startY + 34);
    }
}