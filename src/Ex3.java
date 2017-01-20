public class Ex3 {

    public final static int NUM_OF_CLUSTERS = 9;
    public final static int MIN_WORD_OCCURRENCE = 3;

    public static void main(String[] args) {
        // Load input file
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt");

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();
        // Filter rare words
        developmentSet.countWordsOccurrences();
        developmentSet.filterRareWords(MIN_WORD_OCCURRENCE);
        ExpectationMaximization expectationMaximization = new ExpectationMaximization();

        // Init the EM algorithm
        expectationMaximization.init(developmentSet, NUM_OF_CLUSTERS);

        expectationMaximization.run(developmentSet);
    }
}
