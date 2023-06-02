public class DCT {
    public static double[] applyDCT(double[] inputVector) {
        int numElements = inputVector.length;
        double[] transformedVector = new double[numElements];

        for (int k = 0; k < numElements; k++) {
            double sum = 0.0;

            for (int i = 0; i < numElements; i++) {
                double cosFactor = Math.cos((Math.PI * k * (2.0 * i + 1)) / (2.0 * numElements));
                sum += inputVector[i] * cosFactor;
            }

            double cu = (k == 0) ? 1.0 / Math.sqrt(numElements) : Math.sqrt(2.0) / Math.sqrt(numElements);
            transformedVector[k] = cu * sum;
        }

        return transformedVector;
    }

    public static double[][] applyDCT2(double[][] inputMatrix){
        int numRows = inputMatrix.length;
        int numColumns = inputMatrix[0].length;
        double[][] transformedMatrix = new double[numRows][numColumns];
        double[] tempVector;

        for(int columnIndex = 0; columnIndex < numColumns; columnIndex++) {
            tempVector = getColumn(inputMatrix, columnIndex);
            copyColumn(transformedMatrix, applyDCT(tempVector), columnIndex);
        }

        for(int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            tempVector = transformedMatrix[rowIndex].clone();
            transformedMatrix[rowIndex] = applyDCT(tempVector);
        }

        return transformedMatrix;
    }

    public static double[] getColumn(double[][] inputMatrix, int columnIndex) {
        double[] column = new double[inputMatrix.length];

        for (int i = 0; i < column.length; i++) {
            column[i] = inputMatrix[i][columnIndex];
        }

        return column;
    }

    public static void copyColumn(double[][] recipientMatrix, double[] columnToBeCopied, int columnIndex) {
        for (int i = 0; i < columnToBeCopied.length; i++)
            recipientMatrix[i][columnIndex] = columnToBeCopied[i];
    }

}
