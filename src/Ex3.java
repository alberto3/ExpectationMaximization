public class Ex3 {

    public static void main(String[] args) {
        // Load input file
        ProcessInputData processFileData = new ProcessInputData("develop.txt");
        
        DevelopmentSet DS = processFileData.readInputFile();
        ExpectationMaximization EM = new ExpectationMaximization();
        EM.init(DS);
        


    }
}
