public class Ex3 {

    public final static int NUM_OF_CLUSTERS = 9;
    public final static int MIN_WORD_OCCURRENCE = 3;

    public static void main(String[] args) {
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt", "dataset/topics.txt", NUM_OF_CLUSTERS);
        ExpectationMaximization expectationMaximization = new ExpectationMaximization();

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();
        Topics topics = processFileData.readTopicsFile();

        // Filter rare words
        developmentSet.calcWordsOccurrences();
        developmentSet.filterRareWords(MIN_WORD_OCCURRENCE);

        // Init the EM algorithm
        expectationMaximization.init(developmentSet, NUM_OF_CLUSTERS, topics);

        // RUN!
        expectationMaximization.run();
    }
}
