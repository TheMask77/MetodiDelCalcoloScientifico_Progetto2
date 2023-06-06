import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        boolean isLoop = false;
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
            isLoop = true;
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
        double[][] singleBlock;
        int adjustedXDim = inputGrayscale.length - (inputGrayscale.length % blockDimension);
        int adjustedYDim = inputGrayscale[0].length - (inputGrayscale[0].length % blockDimension);
        int blockNumber = 0;
        int blocksInAColumn = adjustedXDim / blockDimension;
        int blocksInARow = adjustedYDim / blockDimension;
        int newBlockPosX = 0;
        int newBlockPosY = 0;

        for (int i = 0; i < blocksInARow * blocksInAColumn; i++) {
            blocks.add(i, new double[blockDimension][blockDimension]);
        }

        for (int i = 0; i < adjustedXDim; i++) {
            for (int j = 0; j < adjustedYDim; j++) {
                blockNumber = blocksInARow * Math.floorDiv(i, blockDimension) + Math.floorDiv(j, blockDimension);
                blocks.get(blockNumber)[i % blockDimension][j % blockDimension] = inputGrayscale[i][j];
            }
        }

        /*
        while (newBlockPosX < adjustedXDim && newBlockPosY < adjustedYDim) {
            singleBlock= new double[blockDimension][blockDimension];
            Utils.copyBlock(singleBlock, inputGrayscale, newBlockPosX, newBlockPosY, blockDimension);
            blocks.add(singleBlock);

            newBlockPosX += blockDimension;

            if(newBlockPosX == adjustedXDim) {
                newBlockPosX = 0;
                newBlockPosY += blockDimension;
            }

        }
        */

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


        /*
        for (double[][] block : blocksArray){
            tempX = dimXMultiplier * blockDimension;
            tempY = dimYMultiplier * blockDimension;
            if (tempX != 0) tempX--;
            if(tempY != 0) tempY--;
            for(int i = 0; i < blockDimension; i++){
                for(int j = 0; j < blockDimension; j++){

                    imageMatrix[i + (tempX)][j + (tempY)] = block[i][j];
                }
            }
            blocksAnalyzed++;
            if (adjustedXDim - ((blocksAnalyzed * blockDimension)) != 0){
                dimXMultiplier++;
            }else{
                blocksAnalyzed = 0;
                dimXMultiplier = 0;
                dimYMultiplier++;
            }
        }
        */
    }

    /*public static void imageLoading(String imagePath){
        BufferedImage img=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int r=0; r<height; r++)
            for(int c=0; c<width; c++)
            {
                int index=r*width+c;
                int red=colors[index] & 0xFF;
                int green=colors[index+1] & 0xFF;
                int blue=colors[index+2] & 0xFF;
                int rgb = (red << 16) | (green << 8) | blue;
                img.setRGB(c, r, rgb);
            }
    }*/
}

