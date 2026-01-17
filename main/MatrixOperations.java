public class MatrixOperations {

    public static double[][] augmenting(double[][] matrix, int size) {
        double[][] augMatrix = new double[size][size * 2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size * 2; j++) {
                if (j < size) augMatrix[i][j] = matrix[i][j];
                else if (j - size == i) augMatrix[i][j] = 1.0;
            }
        }
        return augMatrix;
    }

    public static void RREFmatrix(double[][] augmentedMatrix, int size) {
        for (int i = 0; i < size; i++) {
            double pivot = augmentedMatrix[i][i];
            if (pivot == 0) continue;
            for (int j = 0; j < size * 2; j++) augmentedMatrix[i][j] /= pivot;
            for (int r = 0; r < size; r++) {
                if (r != i) {
                    double factor = augmentedMatrix[r][i];
                    for (int j = 0; j < size * 2; j++) {
                        augmentedMatrix[r][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }
    }

    public static double[][] extractInverse(double[][] augmentedMatrix) {
        int size = augmentedMatrix.length;
        double[][] inverse = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                inverse[i][j] = augmentedMatrix[i][j + size];
        return inverse;
    }

    /*public static boolean isSingular(double[][] augmentedMatrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    if (Math.abs(augmentedMatrix[i][j] - 1.0) > 1e-9) {
                        return true;
                    }
                } else {
                    if (Math.abs(augmentedMatrix[i][j]) > 1e-9) {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/
   
    public static double determinant(double[][] matrix, int n) {
        double[][] temp = new double[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                temp[i][j] = matrix[i][j];

        double det = 1;
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            int pivotRow = i;
            while (pivotRow < n && Math.abs(temp[pivotRow][i]) < 1e-9)
                pivotRow++;

            if (pivotRow == n)
                return 0;

            if (pivotRow != i) {
                double[] t = temp[i];
                temp[i] = temp[pivotRow];
                temp[pivotRow] = t;
                swaps++;
            }

            double pivot = temp[i][i];
            det *= pivot;

            for (int r = i + 1; r < n; r++) {
                double factor = temp[r][i] / pivot;
                for (int c = i; c < n; c++) {
                    temp[r][c] -= factor * temp[i][c];
                }
            }
        }
        if (swaps % 2 != 0)
            det = -det;

        return det;
    }
}
