import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ProcessInputData {
    private String inputFileName = "";
    private String topicsFileName = "";
    private List<Article> articles;
    private int numClusters;
    private String[] topics;
    private Article lastArticle;
    private int lineCounter;
    public Map<String, Integer> wordsOccurrences = new HashMap<String, Integer>();

    public ProcessInputData(String inputFileName, String topicsFileName, int numClusters) {
        this.inputFileName = inputFileName;
        this.topicsFileName = topicsFileName;
        this.numClusters = numClusters;
    }

    public DevelopmentSet readInputFile() {
        DevelopmentSet result = null;

        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
            result = new DevelopmentSet();
            articles = new ArrayList<>();
            lineCounter = 0;
            stream.forEachOrdered(line -> processInputLine(line));
            result.setArticles(articles);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Topics readTopicsFile() {
        Topics result = new Topics(numClusters);
        ;
        topics = new String[numClusters];

        try (Stream<String> stream = Files.lines(Paths.get(topicsFileName))) {
            lineCounter = 0;
            stream.forEachOrdered(line -> processTopicLine(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.setTopics(topics);

        return result;
    }

    private void processInputLine(String line) {
        switch (++lineCounter) {
            case 1:
                lastArticle = new Article();
                List<String> title = Arrays.asList(line.replace("<", "").replace(">", "").split("\\s"));
                lastArticle.setId(Integer.parseInt(title.get(1)));
                lastArticle.setTopics(title.subList(2, title.size()));
                break;
            case 3:
                lastArticle.addWords(Arrays.asList(line.split("\\s")));
                articles.add(lastArticle);
                break;
            case 4:
                lineCounter = 0;
                break;
        }
    }

    private void processTopicLine(String line) {
        if (lineCounter % 2 == 0) {
            topics[lineCounter / 2] = line;
        }
        lineCounter++;
    }
}
