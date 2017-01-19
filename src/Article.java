import java.util.ArrayList;
import java.util.List;

public class Article {
    private List<String> topics;
    private String text;
    private String id;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addTopic(String topic) {
        if (topics == null) {
            topics = new ArrayList<>();
        }
        topics.add(topic);
    }
}
