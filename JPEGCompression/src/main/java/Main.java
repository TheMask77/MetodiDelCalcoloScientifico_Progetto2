import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        boolean isTestModeEnabled = false;

        //GUI Startup
        MainForm mainForm = new MainForm();

        if(isTestModeEnabled){
            executionTimeTest();
            DCTReliabilityTest();
            DCT2ReliabilityTest();
        }
    }

    private static void executionTimeTest(){
        //execution time experiments
        String[] fileNames = {"200", "400", "800", "1600"};
        double[][] inputMatrix;

        for (int i = 0; i < fileNames.length; i++) {
            inputMatrix = Utils.loadMatrixFromFile("src/main/resources/Square arrays/int" + fileNames[i] + "x" + fileNames[i] + ".csv");
            ExecutionTimeExperiments.experiments(inputMatrix);
        }
    }

    private static void DCTReliabilityTest(){
        //homemade DCT reliability test
        double[] inputData = {231, 32, 233, 161, 24, 71, 140, 245};
        double[] transformedDataDCT = new double[inputData.length];
        transformedDataDCT = DCT.applyDCT(inputData);
        Utils.printArray(transformedDataDCT);
    }

    private static void DCT2ReliabilityTest(){
        //homemade DCT2 reliability test
        String rawData = (String) ("231 32 233 161 24 71 140 245\n" +
                "247 40 248 245 124 204 36 107\n" +
                "234 202 245 167 9 217 239 173\n" +
                "193 190 100 167 43 180 8 70\n" +
                "11 24 210 177 81 243 8 112\n" +
                "97 195 203 47 125 114 165 181\n" +
                "193 70 174 167 41 30 127 245\n" +
                "87 149 57 192 65 129 178 228").replace(" ", ",");

        String[] rows = rawData.split("\n");
        double[][] inputData2 = new double[8][8];

        for(int i = 0; i < 8; i++) {
            String[] currentRow = rows[i].split(",");
            for (int ii = 0; ii < 8; ii++)
                inputData2[i][ii] = Double.parseDouble(currentRow[ii]);
        }
        double[][] transformedDataDCT2 = inputData2.clone();
        transformedDataDCT2 = DCT.applyDCT2(inputData2);
        Utils.print2DArray(transformedDataDCT2, 8, 8);
    }
}
