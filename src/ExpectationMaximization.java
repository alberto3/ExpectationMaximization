import java.util.*;

public class ExpectationMaximization {
    private Map<Integer, List<Article>> clusters;
    private DevelopmentSet developmentSet;
    private int maxClusters;
    private Map<Article, double[]> Wti = new TreeMap<>();
    private Map<String, double[]> Pik = new TreeMap<>();

    double clustersProbability[];

    public void init(DevelopmentSet developmentSet, int maxClusters) {
        this.developmentSet = developmentSet;
        this.maxClusters = maxClusters;
        clustersProbability = new double[maxClusters];
        Wti = new HashMap<>();

        initClusters();
        initEM();

        // Todo:
        // 2. Compute Ai, Pik
    }

    private void initClusters() {
        final int[] index = {0};
        clusters = new HashMap<>();

        developmentSet.getArticles().forEach(article -> {
            int key = index[0]++ % maxClusters;

            if (!clusters.containsKey(key)) {
                clusters.put(key, new ArrayList<>());
            }
            clusters.get(key).add(article);
        });
    }

    // Set the initial alpha
    private void initEM() {
        for (int i = 0; i < maxClusters; i++) {
            for (Article currentArticle : clusters.get(i)) {
                double[] clusterProbabilityForArticle = new double[maxClusters];
                for (int j = 0; j < maxClusters; j++) {
                    clusterProbabilityForArticle[j] = (i == j ? 1.0 : 0.0);
                }
                Wti.put(currentArticle, clusterProbabilityForArticle);
            }
        }
        // alpha i = Wti / N
    }


    public void run(DevelopmentSet developmentSet) {
        initEM();

    }

    private void EStep(DevelopmentSet developmentSet) {
        for (Article currentArticle : developmentSet.getArticles()) {
//            double[] Zi = calcZi(developmentSet.getWordsOccurrences(), currentArticle);
        }

    }

    private void MStep(DevelopmentSet developmentSet) {

        double sumWti;
        for (int i = 0; i < maxClusters; i++) {
            sumWti = 0;
            for (Article currentArticle : developmentSet.getArticles()) {
//                double[] Zi = calcZi(developmentSet.getWordsOccurrences(), currentArticle);
            }
        }

    }

    private double[] calcZi(Map<String, Integer> WordsOccurrences, Article currentArticle) {
        double[] Zi = new double[maxClusters];

        for (int i = 0; i < maxClusters; i++) {
            double sumFrequency = 0;
            // Going over k words
            for (String word : currentArticle.getWordsOccurrences().keySet()) {
                sumFrequency += currentArticle.getWordOccurrences(word) * Math.log(Pik.get(word)[i]);  //natural log
            }
//            Zt[i] = Math.log(clustersProbability[i]) + sumFrequency;
        }
        return Zi;
    }
}
