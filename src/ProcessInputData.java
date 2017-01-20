import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProcessInputData {

    private String inputFileName = "";
    private List<Article> articles;
    private Article lastArticle;
    private int lineCounter;

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
                List<String> title = new ArrayList<String>(Arrays.asList(line.replace("<", "").replace(">", "").split("\t")));
                title.remove(0);
                lastArticle.setId(title.remove(0));
                lastArticle.setTopics(title);
                break;
            case 3:
                lastArticle.setText(line);
                articles.add(lastArticle);
                lineCounter = 0;
        }
    }
}
