import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExpectationMaximization {
	private Map<Integer, List<Article>> clusters;
	private DevelopmentSet developmentSet;
	private int maxClusters;
	Map<Article, double[]> Wti = new TreeMap<Article, double[]>();
	Map<String, double[]> Pik = new TreeMap<String, double[]>();
	
	double clustersProbability[];

	public void init(DevelopmentSet developmentSet, int maxClusters) {
		this.developmentSet = developmentSet;
		this.maxClusters = maxClusters;
		clustersProbability = new double[Ex3.NUM_OF_CLUSTERS];
		Wti = new HashMap<Article, double[]>();
		
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
		for (int i=0; i < Ex3.NUM_OF_CLUSTERS; i++){
			for (Article currentArticle : clusters.get(i)){
				double[] clusterProbabilityForArticle = new double[Ex3.NUM_OF_CLUSTERS];
				for(int j=0; j < Ex3.NUM_OF_CLUSTERS; j++){
					clusterProbabilityForArticle[j] = (i==j ? 1.0 : 0.0);
				}				
				Wti.put(currentArticle, clusterProbabilityForArticle);
			}
		}
		// alpha i = Wti / N
	}


	public void EMAlgorithmRun(DevelopmentSet developmentSet){
		initEM();

	}

	private void EStep (DevelopmentSet developmentSet){
		for (Article currentArticle : developmentSet.getArticles()){
			//    		double[] Zi = calcZi(developmentSet.getWordsOccurrences(), currentArticle);
		}

	}

	private void MStep (DevelopmentSet developmentSet){

		double sumWti;
		for (int i=0; i < Ex3.NUM_OF_CLUSTERS; i++){
			sumWti = 0;
			for (Article currentArticle : developmentSet.getArticles()){

				//    		double[] Zi = calcZi(developmentSet.getWordsOccurrences(), currentArticle);
			}
		}

	}

	    private double[] calcZi (Map<String, Integer> WordsOccurrences, Article currentArticle)
	    {
	    	double[] Zi = new double[Ex3.NUM_OF_CLUSTERS];
	    	
	    	for (int i = 0; i < Ex3.NUM_OF_CLUSTERS; i++)
			{	
				double sumFrequncy = 0;
				// Going over k words
				for (String word : currentArticle.getWordsOccurrences().keySet()){
					// 
					sumFrequncy += currentArticle.getWordOccurrences(word) * Math.log(Pik.get(word)[i]);  //natural log
				}
//				Zt[i] = Math.log(clustersProbability[i]) + sumFrequncy; 
			}		
	    	return Zi;
	    }
}
