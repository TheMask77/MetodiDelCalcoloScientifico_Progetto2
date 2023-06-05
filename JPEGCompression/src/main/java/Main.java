import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        //GUI Startup
        MainForm mainForm = new MainForm();

        int blockWidth = 0;
        int frequenciesToDrop = 0;
        Scanner inputReader = new Scanner(System.in);
/*
        do {
            System.out.print("Insert a value for F (blocks dimension) ==> ");
            blockWidth = inputReader.nextInt();
        } while (blockWidth < 1);

        do {
            System.out.print("Insert a value for d (frequencies to be dropped) between 0 and 2F - 2 ==> ");
            frequenciesToDrop = inputReader.nextInt();
        } while (!(frequenciesToDrop >= 0 && frequenciesToDrop <= (2*blockWidth - 2)));

        System.out.println("F = " + blockWidth + " and d = " + frequenciesToDrop);

 */

        double[][] inputImage = Utils.getGrayLevelsMatrixFromFile("src/main/resources/images/20x20.bmp");
        ArrayList<double[][]> blocks = Utils.getBlocksFromGrayscale(inputImage, 8);
        Compression.compress(blocks, 8, 14);
        System.out.println();


        /*
        String[] fileNames = {"200", "400", "800", "1600"};
        double[][] inputMatrix;

        for (int i = 0; i < fileNames.length; i++) {
            inputMatrix = Utils.loadMatrixFromFile("src/main/resources/Square arrays/int" + fileNames[i] + "x" + fileNames[i] + ".csv");
            ExecutionTimeExperiments.experiments(inputMatrix);
        }
         */

/*
        double[] inputData = {231, 32, 233, 161, 24, 71, 140, 245};
        double[] transformedData = new double[inputData.length];

        transformedData = DCT.applyDCT(inputData);
        System.out.println();
        System.out.println("Trans data");


*/
/*
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


        double[][] transformedData = inputData2.clone();
        //DoubleDCT_2D transformer = new DoubleDCT_2D(inputData2.length, inputData2[0].length);
        //transformer.forward(transformedData, true);

        transformedData = DCT.applyDCT2(inputData2);
        System.out.println();

 */



    }
}
