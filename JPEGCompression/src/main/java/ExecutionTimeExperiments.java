import org.jtransforms.dct.DoubleDCT_2D;

import java.text.DecimalFormat;

public class ExecutionTimeExperiments {
    public static void experiments(double[][] inputMatrix) {
        double startTime;
        double endTime;
        int dimension = inputMatrix.length;

        System.out.println("===================================");

        System.out.println("Operating on a " + dimension + " x " + dimension + " matrix");
        System.out.println("Starting home-made DCT");
        startTime = System.nanoTime();

        DCT.applyDCT2(inputMatrix);

        endTime = System.nanoTime();
        System.out.println("TIME ELAPSED: " + new DecimalFormat("#####.##").format((endTime - startTime)/1000000000) + " s");

        System.out.println();

        System.out.println("Starting jTransform DCT");
        startTime = System.nanoTime();

        DoubleDCT_2D transformer = new DoubleDCT_2D(dimension, dimension);
        transformer.forward(new double[dimension][dimension], true);

        endTime = System.nanoTime();
        System.out.println("TIME ELAPSED: " + new DecimalFormat("#####.##").format((endTime - startTime)/1000000000) + " s");

        System.out.println("===================================");
    }

}
