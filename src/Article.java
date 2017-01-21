import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Article implements Comparable<Article>{
    private List<String> topics;
    private Map<String, Integer> wordsOccurrences = new HashMap<String, Integer>();
    
    private int id;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addTopic(String topic) {
        if (topics == null) {
            topics = new ArrayList<>();
        }
        topics.add(topic);
    }

    public Map<String, Integer> getWordsOccurrences() {
        return wordsOccurrences;
    }

    public void setWordsOccurrences(List<String> words) {
        for (String word : words) {
            this.wordsOccurrences.put(word, getWordOccurrences(word) + 1);
        }
    }

    public int getWordOccurrences(String word) {
        return this.wordsOccurrences.get(word) == null ? 0 : this.wordsOccurrences.get(word);
    }


    public void removeRareWords(List<String> rareWords) {
        for (String word : rareWords) {
            this.wordsOccurrences.remove(word);
        }
    }
    
    public long getNumberOfWords() {
    	long numberOfWords = 0;
    	for (String word : this.wordsOccurrences.keySet()) {
    		numberOfWords += this.wordsOccurrences.get(word);
    	}
    	return numberOfWords;
    }

	@Override
	public int compareTo(Article oArticle) {
		return id-oArticle.id;
	}
}
