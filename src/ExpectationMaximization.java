import java.util.*;
import java.util.stream.Stream;

public class ExpectationMaximization {
    private final static double TESTED_LAMBDA = 0.01; // check
    private final static double EPSILON_THRESHOLD = 0.00000001;
    private final static double K = 10;
    private final static double EM_THRESHOLD = 1; // check

    private Map<Integer, List<Article>> clusters;
    private DevelopmentSet developmentSet;
    private int numClusters;
    private Map<Article, Double[]> Wti;
    private Map<Article, Double[]> Zti;
    private Map<Article, Double> Mt;
    private Map<String, Double[]> Pik;
    private double clustersProbability[]; //alpha(i)

    public void init(DevelopmentSet developmentSet, int numClusters) {
        this.Wti = new HashMap<>();
        this.Zti = new HashMap<>();
        this.Mt = new HashMap<>();
        this.Pik = new HashMap<>();
        this.developmentSet = developmentSet;
        this.numClusters = numClusters;
        this.clustersProbability = new double[numClusters];

        initClusters();
        initEM();
        MStep();
    }

    public void run() {
        double likelihood = 0;
        double lastLikelihood = likelihood - EM_THRESHOLD - 1;
        // if in some round
        // we find that the Likelihood decrease - it means that we have a bug in our implementation or
        // that we are smoothing too aggressively.

        // Run EM algorithm until convergence
        while (likelihood - lastLikelihood > EM_THRESHOLD) {
            EStep();
            MStep();
            lastLikelihood = likelihood;
            likelihood = calcLikelihood();
        }
    }

    private void initClusters() {
        final int[] index = {0};
        clusters = new HashMap<>();

        developmentSet.getArticles().forEach(article -> {
            int key = index[0]++ % numClusters;

            if (!clusters.containsKey(key)) {
                clusters.put(key, new ArrayList<>());
            }
            clusters.get(key).add(article);
        });
    }

    // Set the initial Wti
    private void initEM() {
        for (int i = 0; i < numClusters; i++) {
            for (Article currentArticle : clusters.get(i)) {
                Double[] clusterProbabilityForArticle = new Double[numClusters];
                for (int j = 0; j < numClusters; j++) {
                    clusterProbabilityForArticle[j] = (i == j ? 1.0 : 0.0);
                }
                Wti.put(currentArticle, clusterProbabilityForArticle);
            }
        }
    }

    private void EStep() {
        for (Article currentArticle : developmentSet.getArticles()) {
            calcWti(currentArticle);
        }
    }

    // Calculate article probabilities for each cluster
    // Approximate Wti (4)
    private void calcWti(Article currentArticle) {
        Double sumZi = 0.0;
        Double[] Zi = calcZi(currentArticle);
        Double[] clusterProbabilityForArticle = new Double[numClusters];
        Double m = calcMaxZi(Stream.of(Zi).mapToDouble(Double::doubleValue).toArray());

        for (int i = 0; i < numClusters; i++) {
            if (Zi[i] - m < -1 * K) {
                clusterProbabilityForArticle[i] = 0.0;
            } else {
                double eZiMinusM = Math.exp(Zi[i] - m);

                clusterProbabilityForArticle[i] = eZiMinusM;
                sumZi += eZiMinusM;
            }
        }

        for (int i = 0; i < numClusters; i++) {
            clusterProbabilityForArticle[i] /= sumZi;
        }

        Wti.put(currentArticle, clusterProbabilityForArticle);
        Zti.put(currentArticle, Zi);
        Mt.put(currentArticle, m);
    }

    // Calculate the Z value for each article in each cluster
    private Double[] calcZi(Article article) {
        Double[] result = new Double[numClusters];

        for (int i = 0; i < numClusters; i++) {
            double sumFrequency = 0;

            // Going over k words and calculate the Z value for each article in each cluster
            for (String word : article.getWordsOccurrences().keySet()) {
                sumFrequency += article.getWordOccurrences(word) * Math.log(Pik.get(word)[i]);
            }

            result[i] = Math.log(clustersProbability[i]) + sumFrequency;
        }

        return result;
    }

    private Double calcMaxZi(double[] Zi) {
        return Arrays.stream(Zi).max().getAsDouble();
    }

    private void MStep() {
        calcPik();
        calcAlpha();
        smoothAlpha();
    }

    private void calcPik() {
        double sumWti;
        double wordsOccurrencesInArticles;
        Double[] lidstoneP = new Double[numClusters];
        double[] wordsInClusters = new double[numClusters];

        // Calculate Pik (dividend)
        for (int i = 0; i < numClusters; i++) {
            sumWti = 0;
            for (Article currentArticle : developmentSet.getArticles()) {
                sumWti += this.Wti.get(currentArticle)[i] * currentArticle.getNumberOfWords();
            }
            wordsInClusters[i] = sumWti;
        }

        // Calculate Pik (divisor)
        // Calculate the Lidstone probability for each word to be in each topic by its Occurrences in all articles
        for (String word : developmentSet.getWordsOccurrences().keySet()) {
            for (int i = 0; i < numClusters; i++) {
                wordsOccurrencesInArticles = 0;
                for (Article currentArticle : developmentSet.getArticles()) {
                    if (currentArticle.getWordOccurrences(word) > 0 && this.Wti.get(currentArticle)[i] > 0) {
                        wordsOccurrencesInArticles += this.Wti.get(currentArticle)[i] * currentArticle.getWordOccurrences(word);
                    }
                }
                lidstoneP[i] = calcLidstonePortability(wordsOccurrencesInArticles, wordsInClusters[i]);
            }
            this.Pik.put(word, lidstoneP);
        }
    }

    private double calcLidstonePortability(double wordsOccurrencesInArticles, double wordsInCluster) {
        return (wordsOccurrencesInArticles + TESTED_LAMBDA) / (wordsInCluster + TESTED_LAMBDA * this.developmentSet.getWordsOccurrences().size());
    }

    // Calculate alpha(i)
    private void calcAlpha() {
        double currentClusterProbability;
        for (int i = 0; i < numClusters; i++) {
            currentClusterProbability = 0;
            for (Article currentArticle : developmentSet.getArticles()) {
                currentClusterProbability += this.Wti.get(currentArticle)[i];
            }
            this.clustersProbability[i] = currentClusterProbability / developmentSet.getArticles().size();
        }
    }

    private void smoothAlpha() {
        double sumAlpha = 0;

        // Fix alpha(i) to the epsilon threshold
        for (int i = 0; i < numClusters; i++) {
            this.clustersProbability[i] = (clustersProbability[i] > EPSILON_THRESHOLD ? clustersProbability[i] : EPSILON_THRESHOLD);
        }

        // Find total clusters probability
        for (int i = 0; i < numClusters; i++) {
            sumAlpha += this.clustersProbability[i];
        }

        // Find the probability to be in each cluster
        for (int i = 0; i < numClusters; i++) {
            this.clustersProbability[i] /= sumAlpha;
        }
    }

    private double calcLikelihood() {
        double likelihood = 0;
        double sumZt;
        double m;

        for (Article currentArticle : Mt.keySet()) {
            sumZt = 0;
            m = Mt.get(currentArticle);
            if (Zti.get(currentArticle) != null) {
                for (double Zti : Zti.get(currentArticle)) {
                    if (-1 * K <= Zti - m) {
                        sumZt += Math.exp(Zti - m);
                    }
                }
            }
            likelihood += m + Math.log(sumZt);
        }
        return likelihood;
    }
}
