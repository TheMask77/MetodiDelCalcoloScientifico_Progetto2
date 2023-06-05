import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public static double[][] getGrayLevelsMatrixFromFile(String filePath) throws IOException {

        File image = new File(filePath);
        BufferedImage img = ImageIO.read(image);

        int dimX = img.getWidth();
        int dimY = img.getHeight();
        double[][] grayLevels = new double[dimX][dimY];

        for(int x=0; x<dimX; x++){
            for(int y=0; y<dimY; y++){
                int pixel = img.getRGB(x, y);
                grayLevels[x][y] = (pixel & 0xff);
            }
        }

        return grayLevels;
    }
}

