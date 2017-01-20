import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ExpectationMaximization {
    private Map<Integer, List<Article>> clusters;
    private DevelopmentSet developmentSet;
    private int maxClusters;


    public void init(DevelopmentSet developmentSet, int maxClusters) {
        this.developmentSet = developmentSet;
        this.maxClusters = maxClusters;

        initClusters();
        // Todo:
        // 2. Compute Ai, Pik
    }

    private void initClusters() {
        clusters = new HashMap<>();

        developmentSet.getArticles().forEach(new Consumer<Article>() {
            private int index = 0;

            @Override
            public void accept(Article article) {
                int key = index % maxClusters;

                if (!clusters.containsKey(key)) {
                    clusters.put(key, new ArrayList<>());
                }
                clusters.get(key).add(article);
            }
        });
    }
}
