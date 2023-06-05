import org.jtransforms.dct.DoubleDCT_2D;

import java.util.ArrayList;

public class Compression {
    public static void compress(ArrayList<double[][]> blocks, int F, int d) {
        ArrayList<double[][]> blocksTransformed = new ArrayList<>();
        DoubleDCT_2D transformer = new DoubleDCT_2D(blocks.get(0).length, blocks.get(0).length);


        //Applying DCT
        for (double[][] block : blocks) {
            transformer.forward(block, true);
            blocksTransformed.add(block);

        }

        //Deleting frequencies
        for (double[][] block : blocksTransformed) {
            for (int i = 0; i < block.length; i++)
                for (int j = 0; j < block[0].length; j++) {
                    if(i + j >= d)
                        block[i][j] = -1;
                }
        }

        //Applying invDCT
        for (double[][] block : blocks) {
            blocksTransformed.add(DCT.applyDCT2(block));
        }


    }
}
