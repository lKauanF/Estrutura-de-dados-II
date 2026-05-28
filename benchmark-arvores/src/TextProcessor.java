import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TextProcessor {
    public static List<String> readWordsFromFile(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

        String normalizedContent = normalizeContent(content);
        Set<String> stopwords = getPortugueseStopwords();

        List<String> words = new ArrayList<>();
        String[] tokens = normalizedContent.split("\\s+");

        for (String token : tokens) {
            if (!token.trim().isEmpty() && !stopwords.contains(token)) {
                words.add(token);
            }
        }

        return words;
    }

    private static String normalizeContent(String content) {
        String lowerContent = content.toLowerCase(Locale.ROOT);

        String withoutAccents = Normalizer
                .normalize(lowerContent, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return withoutAccents.replaceAll("[^a-z0-9]+", " ");
    }

    private static Set<String> getPortugueseStopwords() {
        Set<String> stopwords = new HashSet<>();

        String[] words = {
                "a", "ao", "aos", "aquela", "aquelas", "aquele", "aqueles", "aquilo",
                "as", "ate", "com", "como", "da", "das", "de", "dela", "delas",
                "dele", "deles", "depois", "do", "dos", "e", "ela", "elas", "ele",
                "eles", "em", "entre", "era", "eram", "essa", "essas", "esse",
                "esses", "esta", "estamos", "estao", "estar", "estas", "estava",
                "estavam", "este", "esteja", "estejam", "estejamos", "estes",
                "esteve", "estive", "estivemos", "estiver", "estivera", "estiveram",
                "estiverem", "estivermos", "estivesse", "estivessem", "estivessemos",
                "estiveste", "estivestes", "estou", "eu", "foi", "fomos", "for",
                "fora", "foram", "forem", "formos", "fosse", "fossem", "fossemos",
                "fui", "ha", "isso", "isto", "ja", "lhe", "lhes", "mais", "mas",
                "me", "mesmo", "meu", "meus", "minha", "minhas", "muito", "na",
                "nao", "nas", "nem", "no", "nos", "nossa", "nossas", "nosso",
                "nossos", "num", "numa", "o", "os", "ou", "para", "pela", "pelas",
                "pelo", "pelos", "por", "qual", "quando", "que", "quem", "sao",
                "se", "seja", "sejam", "sejamos", "sem", "ser", "sera", "serao",
                "serei", "seremos", "seria", "seriam", "seriamos", "seu", "seus",
                "so", "somos", "sou", "sua", "suas", "tambem", "te", "tem",
                "temos", "tenha", "tenham", "tenhamos", "tenho", "ter", "teu",
                "teus", "tinha", "tinham", "tinhamos", "tive", "tivemos", "tiver",
                "tivera", "tiveram", "tiverem", "tivermos", "tivesse", "tivessem",
                "tivessemos", "tu", "tua", "tuas", "um", "uma", "voce", "voces",
                "vos"
        };

        for (String word : words) {
            stopwords.add(word);
        }

        return stopwords;
    }
}