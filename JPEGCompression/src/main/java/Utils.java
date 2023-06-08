import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Utils {

    private static int imageWidth;
    private static int imageHeight;

    public static int getImageWidth() {
        return imageWidth;
    }

    public static int getImageHeight() {
        return imageHeight;
    }

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

    private static void setImageDimensions(BufferedImage image) {
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    public static double[][] getGrayLevelsMatrixFromFile(String filePath) {

        File image = new File(filePath);
        BufferedImage img = null;
        try {
            img = ImageIO.read(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setImageDimensions(img);

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

    public static ArrayList<double[][]> getBlocksFromGrayscale(double[][] inputGrayscale, int blockDimension) {
        ArrayList<double[][]> blocks = new ArrayList<>();
        int adjustedXDim = inputGrayscale.length - (inputGrayscale.length % blockDimension);
        int adjustedYDim = inputGrayscale[0].length - (inputGrayscale[0].length % blockDimension);
        int blockNumber = 0;
        int blocksInAColumn = adjustedXDim / blockDimension;
        int blocksInARow = adjustedYDim / blockDimension;

        for (int i = 0; i < blocksInARow * blocksInAColumn; i++) {
            blocks.add(i, new double[blockDimension][blockDimension]);
        }

        for (int i = 0; i < adjustedXDim; i++) {
            for (int j = 0; j < adjustedYDim; j++) {
                blockNumber = blocksInARow * Math.floorDiv(i, blockDimension) + Math.floorDiv(j, blockDimension);
                blocks.get(blockNumber)[i % blockDimension][j % blockDimension] = inputGrayscale[i][j];
            }
        }

        return blocks;
    }

    private static void copyBlock(double[][] recipientBlock, double[][] inputMatrix, int startingPosX, int startingPosY, int blockDimension) {
        for (int i = 0; i < blockDimension; i++ ) {
            for (int j = 0; j < blockDimension; j++) {
                recipientBlock[i][j] = inputMatrix[startingPosX + i][startingPosY + j];
            }
        }
    }

    public static void getImageMatrixFromBlocks(double[][] imageMatrix, int blockDimension, ArrayList<double[][]> blocksArray, int adjustedXDim, int adjustedYDim){
        int dimXMultiplier = 0;
        int dimYMultiplier = 0;
        int blocksAnalyzed = 0;
        int tempX = 0;
        int tempY = 0;
        int blockNumber = 0;
        int blocksInARow = adjustedYDim / blockDimension;

        for (int i = 0; i < adjustedXDim; i++)
            for (int j = 0; j < adjustedYDim; j++) {
                blockNumber = blocksInARow * Math.floorDiv(i, blockDimension) + Math.floorDiv(j, blockDimension);
                imageMatrix[i][j] = blocksArray.get(blockNumber)[i % blockDimension][j % blockDimension];
            }
    }

    public static void print2DArray(double[][] array, int dimX, int dimY) {
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                System.out.print(formatter(array[i][j]) + ",\t ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printArray(double[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(formatter(array[i]) + ",\t ");
        }
    }

    public static void print2DArrayToBlocks(double[][] array, int dimX, int dimY, int blockDim) {
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                System.out.print(array[i][j] + ", ");
                if ((j + 1) % blockDim == 0)
                    System.out.print("      ");
            }
            if ((i + 1) % blockDim == 0)
                System.out.println();
            System.out.println();
        }
        System.out.println();
    }

    private static String formatter(double number){
        DecimalFormat formatter = new DecimalFormat("0.00E00");
        String fnumber = formatter.format(number);
        if (!fnumber.contains("E-")) { //don't blast a negative sign
            fnumber = fnumber.replace("E", "E+");
        }
        return fnumber;
    }
}

