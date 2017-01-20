import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevelopmentSet {
	private List<Article> articles;
	public Map<String, Integer> wordsOccurrences = new HashMap<String, Integer>();

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Map<String, Integer> getWordsOccurrences() {
		return wordsOccurrences;
	}
	
	public void countWordsOccurrences(){
		for (Article currentArticle : articles){
			for (Map.Entry<String, Integer> articleWordOccurrences: currentArticle.getWordsOccurrences().entrySet())
			{
				updateWordOccurrences(articleWordOccurrences);
			}
		}
	}


	public void filterRareWords (){
		Map<String, Integer> filteresWordsOccurrences = new HashMap<String, Integer>();
		List<String> rareWords = new ArrayList<String>();

		for (String word: wordsOccurrences.keySet())
		{
			if (wordsOccurrences.get(word) > 3)
			{
				filteresWordsOccurrences.put(word, wordsOccurrences.get(word));
			}
			else
			{
				rareWords.add(word);
			}
		}

		// Delete rare words from each article words occurrences
		for (Article currentArticle : articles)
		{
			currentArticle.removeRareWords(rareWords);
		}

		wordsOccurrences = filteresWordsOccurrences;
	}


	private void updateWordOccurrences (Map.Entry<String, Integer> articleWordOccurrences){
		wordsOccurrences.put(articleWordOccurrences.getKey(), getWordOccurrences(articleWordOccurrences, articleWordOccurrences.getKey()) + articleWordOccurrences.getValue());
	}

	public int getWordOccurrences (Map.Entry<String, Integer> articleWordOccurrences, String word){
		return wordsOccurrences.get(word) == null ? 0 : wordsOccurrences.get(word);
	}
}
