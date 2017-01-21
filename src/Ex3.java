public class Ex3 {

    public final static int NUM_OF_CLUSTERS = 9;
    public final static int MIN_WORD_OCCURRENCE = 3;

    public static void main(String[] args) {
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt");
        ExpectationMaximization expectationMaximization = new ExpectationMaximization();

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();

        // Filter rare words
        developmentSet.countWordsOccurrences();
        developmentSet.filterRareWords(MIN_WORD_OCCURRENCE);

        // Init the EM algorithm
        expectationMaximization.init(developmentSet, NUM_OF_CLUSTERS);

        // RUN!
        expectationMaximization.run();
    }
}
