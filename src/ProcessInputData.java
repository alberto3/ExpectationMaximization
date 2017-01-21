import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ProcessInputData {
    private String inputFileName = "";
    private List<Article> articles;
    private Article lastArticle;
    private int lineCounter;
    public Map<String, Integer> wordsOccurrences = new HashMap<String, Integer>();

    public ProcessInputData(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public DevelopmentSet readInputFile() {
        DevelopmentSet result = null;

        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
            result = new DevelopmentSet();
            articles = new ArrayList<>();
            lineCounter = 0;
            stream.forEachOrdered(line -> processLine(line));
            result.setArticles(articles);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void processLine(String line) {
        switch (++lineCounter) {
            case 1:
                lastArticle = new Article();
                List<String> title = Arrays.asList(line.replace("<", "").replace(">", "").split("\\s"));
                lastArticle.setId(Integer.parseInt(title.get(1)));
                lastArticle.setTopics(title.subList(2, title.size()));
                break;
            case 3:
                lastArticle.setWordsOccurrences(Arrays.asList(line.split("\\s")));
                articles.add(lastArticle);
                break;
            case 4:
                lineCounter = 0;
                break;
        }
    }
}
