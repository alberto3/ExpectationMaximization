public class Ex3 {

    public static void main(String[] args) {
        // Load input file
        ProcessInputData processFileData = new ProcessInputData("dataset/develop.txt");

        // Prepare the development set
        DevelopmentSet developmentSet = processFileData.readInputFile();


    }
}
