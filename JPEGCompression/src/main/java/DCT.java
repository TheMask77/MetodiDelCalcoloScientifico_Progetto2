public class DCT {
    public static double[] applyDCT(double[] inputVector) {
        int numElements = inputVector.length;
        double[] output = new double[numElements];

        for (int k = 0; k < numElements; k++) {
            double sum = 0.0;

            for (int i = 0; i < numElements; i++) {
                double cosFactor = Math.cos((Math.PI * k * (2.0 * i + 1)) / (2.0 * numElements));
                sum += inputVector[i] * cosFactor;
            }

            double cu = (k == 0) ? 1.0 / Math.sqrt(numElements) : Math.sqrt(2.0) / Math.sqrt(numElements);
            output[k] = cu * sum;
        }

        return output;
    }
}
