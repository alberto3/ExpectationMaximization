public class Ex3 {
	
	public final static int NUM_OF_CLUSTERS = 9;

    public static void main(String[] args) {
        

        // Load input file
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt");

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();
        // Filter rare words
        developmentSet.countWordsOccurrences();
        developmentSet.filterRareWords();
        ExpectationMaximization expectationMaximization = new ExpectationMaximization();

        // Init the EM algorithm
        expectationMaximization.init(developmentSet, NUM_OF_CLUSTERS);
        
        expectationMaximization.EMAlgorithmRun(developmentSet);
    }
}
