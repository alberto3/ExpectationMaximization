public class Ex3 {

    public static void main(String[] args) {
        final int maxClusters = 9;

        // Load input file
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt");

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();

        ExpectationMaximization expectationMaximization = new ExpectationMaximization();

        // Init the EM algorithm
        expectationMaximization.init(developmentSet, maxClusters);
    }
}
