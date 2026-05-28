public class BenchmarkResultado {
    private final String name;
    private final Metrics metrics;

    public BenchmarkResultado(String name, Metrics metrics) {
        this.name = name;
        this.metrics = metrics;
    }

    public String getName() {
        return name;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}