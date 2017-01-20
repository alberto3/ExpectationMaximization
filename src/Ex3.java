public class Ex3 {

    public static void main(String[] args) {
        final int maxClusters = 9;

        // Load input file
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt");

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();

        ExpectationMaximization expectationMaximization = new ExpectationMaximization();
<<<<<<< HEAD

        // Init the EM algorithm
        expectationMaximization.init(developmentSet, maxClusters);
=======
      
        // Init the EM algorithm
        expectationMaximization.init(DS);
>>>>>>> c14fd4be093096b6a86427ad3dd71eb51346777c
    }
}
