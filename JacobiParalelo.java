import java.util.concurrent.*;

public class JacobiParalelo {
    static final ForkJoinPool fjPool = new ForkJoinPool();

    static class JacobiTask extends RecursiveAction {
        final double[][] A;
        final double[] B;
        final double[] X;
        final double[] lastX;
        final int start;
        final int end;
        final double error;

        JacobiTask(double[][] A, double[] B, double[] X, double[] lastX, int start, int end, double error) {
            this.A = A;
            this.B = B;
            this.X = X;
            this.lastX = lastX;
            this.start = start;
            this.end = end;
            this.error = error;
        }

        protected void compute() {
            if (end - start <= 500) { // Ajusta este valor según el tamaño de tu problema
                for (int i = start; i < end; i++) {
                    double sum = B[i];
                    for (int j = 0; j < A[i].length; j++) {
                        if (j != i) sum -= A[i][j] * lastX[j];
                    }
                    X[i] = 1/A[i][i] * sum;
                }
            } else {
                int mid = (start + end) / 2;
                invokeAll(new JacobiTask(A, B, X, lastX, start, mid, error),
                          new JacobiTask(A, B, X, lastX, mid, end, error));
            }
        }
    }

    public static void main(String[] args) {
        // Define tu matriz A y el vector B aquí
        double[][] A = {
            {4, -1, 0, 0},
            {-1, 4, -1, 0},
            {0, -1, 4, -1},
            {0, 0, -1, 3}
        };
        
        double[] B = {15, 10, 10, 10};
        double[] X = new double[B.length];
        double[] lastX = new double[B.length];
        double error = 1e-10;

        while (true) {
            fjPool.invoke(new JacobiTask(A, B, X, lastX, 0, A.length, error));

            double errorNorm = 0;
            for (int i = 0; i < X.length; i++) {
                errorNorm += Math.abs(X[i] - lastX[i]);
                lastX[i] = X[i];
            }

            if (errorNorm < error) break;
        }

        // Imprime el resultado
        for (double x : X) System.out.println(x);
    }
}
