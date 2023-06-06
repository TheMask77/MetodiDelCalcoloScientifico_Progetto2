import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) throws IOException {

        int dimX = 10;
        int dimY = 13;
        double[][] img = new double[dimX][dimY];
        int num = 1;
        int blockDimension = 8;

        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                img[i][j] = num;
                num++;
            }
        }

        img = Utils.getGrayLevelsMatrixFromFile("src/main/resources/images/shoe.bmp");
        dimX = img.length;
        dimY = img[0].length;

        ArrayList<double[][]> blocks = new ArrayList<>();
        //Utils.getBlocksFromGrayscale(img, blockDimension);
        int adjustedXDim = dimX - (dimX % blockDimension);
        int adjustedYDim = dimY - (dimY % blockDimension);
        int blockNumber = 0;
        int blocksInAColumn = adjustedXDim / blockDimension;
        int blocksInARow = adjustedYDim / blockDimension;

        double[][] imgRec = new double[adjustedXDim][adjustedYDim];

        for (int i = 0; i < blocksInARow * blocksInAColumn; i++) {
            blocks.add(i, new double[blockDimension][blockDimension]);
        }

        for (int i = 0; i < adjustedXDim; i++) {
            for (int j = 0; j < adjustedYDim; j++) {
                blockNumber = blocksInARow * Math.floorDiv(i, blockDimension) + Math.floorDiv(j, blockDimension);
                blocks.get(blockNumber)[i % blockDimension][j % blockDimension] = img[i][j];
            }
        }

        ArrayList<double[][]> compressedImg = Compression.compress(blocks, blockDimension, 14);

        for (int i = 0; i < adjustedXDim; i++)
            for (int j = 0; j < adjustedYDim; j++) {
                blockNumber = blocksInARow * Math.floorDiv(i, blockDimension) + Math.floorDiv(j, blockDimension);
                imgRec[i][j] = compressedImg.get(blockNumber)[i % blockDimension][j % blockDimension];
            }

        BufferedImage image = new BufferedImage(adjustedXDim, adjustedYDim, BufferedImage.TYPE_INT_RGB);
        //we either have to loop through all values, or convert to 1-d array
        for(int i = 0; i < imgRec.length; i++) {
            for(int j = 0; j < imgRec[i].length; j++) {
                int grayLevelInt = (int) imgRec[i][j];
                Color grayLevel = new Color(grayLevelInt, grayLevelInt, grayLevelInt);
                image.setRGB(i, j, grayLevel.getRGB());
            }
        }

       File outputfile = new File("output.jpg");
       ImageIO.write(image, "jpg", outputfile);



    }

    public static void print2DArray(double[][] array, int dimX, int dimY) {
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                System.out.print(array[i][j] + ", ");
            }
            System.out.println();
        }
        System.out.println();
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


}
