public class Topics {
    private int numClusters;
    private String[] topics = new String[numClusters];

    public Topics(int numClusters) {
        this.numClusters = numClusters;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public String[] getTopics() {
        return topics;
    }

    public int getTopicIndex(String topic) {
        for (int i = 0; i < numClusters; i++) {
            if (topics[i].equals(topic)) {
                return i;
            }
        }
        return -1;
    }

}
