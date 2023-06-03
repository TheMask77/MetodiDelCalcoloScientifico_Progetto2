
import org.apache.commons.imaging.formats.tiff.datareaders.DataReaderTiled;
import org.apache.commons.imaging.formats.tiff.datareaders.ImageDataReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.math3.transform.*;
import org.jtransforms.dct.DoubleDCT_1D;
import org.jtransforms.dct.DoubleDCT_2D;

public class Main {

    public static void main(String[] args) throws IOException {

        Utils.loadMatrixFromFile("src/main/resources/Square arrays/int400x400.csv", null);

/*
        File image = new File("src/main/resources/images/bridge.bmp");
        Scanner reader = new Scanner(image);
        BufferedImage img = ImageIO.read(image);

        int dimX = img.getWidth();
        int dimY = img.getHeight();
// you should stop here
        byte[][] grayLevels = new byte[dimX][dimY];

        for(int x=0; x<dimX; x++){
            for(int y=0; y<dimY; y++){
                int color = -img.getRGB(x,y);
                grayLevels[x][y] = (byte)(color);
            }
        }



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
