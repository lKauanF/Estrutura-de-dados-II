import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        String filePath = args[0];
        String selectedStructure = args[1];

        try {
            List<String> words = TextProcessor.readWordsFromFile(filePath);

            if (words.isEmpty()) {
                System.out.println("Nenhuma palavra valida encontrada no arquivo.");
                return;
            }

            BinarySearchVector binarySearchVector = new BinarySearchVector();
            BinarySearchTree binarySearchTree = new BinarySearchTree();
            ArvoreAVL avlTree = new ArvoreAVL();

            BenchmarkResultado binaryResult = runBenchmark("pesquisa binaria", binarySearchVector, words);
            BenchmarkResultado bstResult = runBenchmark("arvore binaria", binarySearchTree, words);
            BenchmarkResultado avlResult = runBenchmark("arvore avl", avlTree, words);

            List<BenchmarkResultado> results = new ArrayList<>();
            results.add(binaryResult);
            results.add(bstResult);
            results.add(avlResult);

            printBenchmarkSummary(results);
            printSelectedFrequencies(selectedStructure, binarySearchVector, binarySearchTree, avlTree);
        } catch (IOException exception) {
            System.out.println("Erro ao ler o arquivo: " + exception.getMessage());
        }
    }

    private static BenchmarkResultado runBenchmark(String name, WordStructure structure, List<String> words) {
        Metrics metrics = new Metrics();

        long start = System.nanoTime();

        for (String word : words) {
            structure.insert(word, metrics);
        }

        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;
        metrics.setSeconds(seconds);

        return new BenchmarkResultado(name, metrics);
    }

    private static void printSelectedFrequencies(
            String selectedStructure,
            BinarySearchVector binarySearchVector,
            BinarySearchTree binarySearchTree,
            ArvoreAVL avlTree
    ) {
        if (selectedStructure.equalsIgnoreCase("binary")) {
            printFrequencies(binarySearchVector.getOrderedRecords());
            return;
        }

        if (selectedStructure.equalsIgnoreCase("bst")) {
            printFrequencies(binarySearchTree.getOrderedRecords());
            return;
        }

        if (selectedStructure.equalsIgnoreCase("avl")) {
            printFrequencies(avlTree.getOrderedRecords());
            return;
        }

        System.out.println("Estrutura invalida: " + selectedStructure);
        System.out.println();
        printUsage();
    }

    private static void printBenchmarkSummary(List<BenchmarkResultado> results) {
        results.sort(Comparator.comparingDouble(result -> result.getMetrics().getSeconds()));

        for (BenchmarkResultado result : results) {
            System.out.println(result.getName() + ":");
            System.out.println("  " + result.getMetrics().getComparisons() + " comparacoes");
            System.out.println("  " + result.getMetrics().getAssignments() + " atribuicoes");
            System.out.printf(Locale.US, "  %.6f segundos%n%n", result.getMetrics().getSeconds());
        }
    }

    private static void printFrequencies(List<WordRecord> records) {
        System.out.println("frequencia das palavras:");

        for (WordRecord record : records) {
            System.out.println("  " + record.getWord() + ": " + record.getFrequency());
        }
    }

    private static void printUsage() {
        System.out.println("Uso:");
        System.out.println("  java -cp src Main <arquivo.txt> <estrutura>");
        System.out.println();
        System.out.println("Estruturas disponiveis:");
        System.out.println("  binary");
        System.out.println("  bst");
        System.out.println("  avl");
        System.out.println();
        System.out.println("Exemplo:");
        System.out.println("  java -cp src Main entrada.txt avl");
    }
}