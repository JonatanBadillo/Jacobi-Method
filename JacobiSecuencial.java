public class JacobiSecuencial {
    public static void main(String[] args) {
        // Define tu matriz A y el vector B aquí
        double[][] A = {
            {4, -1, 0, 0},
            {-1, 4, -1, 0},
            {0, -1, 4, -1},
            {0, 0, -1, 3}
        };
        
        double[] B = {15, 10, 10, 10};
        double[] X = new double[B.length]; // Vector de soluciones iniciales, inicializado a 0
        double[] lastX = new double[B.length]; // Vector para almacenar la solución de la iteración anterior
        double error = 1e-10; // Criterio de convergencia

        while (true) {
            for (int i = 0; i < A.length; i++) {
                double sum = B[i]; // Inicializa la suma con el elemento correspondiente del vector B
                for (int j = 0; j < A[i].length; j++) {
                    if (j != i) sum -= A[i][j] * lastX[j]; // Resta los productos Aij*Xj para j != i
                }
                X[i] = 1/A[i][i] * sum; // Divide por el coeficiente diagonal Aii
            }

            // Calcula la norma del error como la suma de las diferencias absolutas entre las soluciones actuales y las de la iteración anterior
            double errorNorm = 0;
            for (int i = 0; i < X.length; i++) {
                errorNorm += Math.abs(X[i] - lastX[i]);
                lastX[i] = X[i]; // Actualiza lastX con la solución actual para la próxima iteración
            }

            // Si la norma del error es menor que el criterio de convergencia, termina el bucle
            if (errorNorm < error) break;
        }

        // Imprime el resultado
        for (double x : X) System.out.println(x);
    }
}
