import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Utils {

    public static double[][] loadMatrixFromFile(String filePath) {
        File inputFile = new File(filePath);
        Scanner inputFileReader = null;
        try {
            inputFileReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] tempRowString;
        double[] row;
        int rowIndex = 0;
        double[][] matrix = null;

        while (inputFileReader.hasNextLine()) {
            tempRowString = inputFileReader.nextLine().trim().split(",");
            row = new double[tempRowString.length];

            for (int i = 0; i < tempRowString.length; i++) {
                row[i] = Double.parseDouble(tempRowString[i]);
            }

            if(matrix == null)
                matrix = new double[row.length][row.length];
            matrix[rowIndex] = row;

            rowIndex++;
        }

        inputFileReader.close();
        return matrix;
    }

}

