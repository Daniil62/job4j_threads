package ru.job4j.threads.sum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums(horizon(matrix, i), vertical(matrix, i));
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        List<CompletableFuture<Sums>> tasks = new ArrayList<>(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            tasks.add(getTask(matrix, i));
        }
        int count = 0;
        for (CompletableFuture<Sums> task : tasks) {
            result[count++] = task.get();
        }
        return result;
    }

    private static int vertical(int[][] matrix, int start) {
        int result = 0;
        for (int[] array : matrix) {
            if (start < array.length) {
                result += array[start];
            }
        }
        return result;
    }

    private static int horizon(int[][] matrix, int start) {
        int result = 0;
        if (start < matrix.length) {
            for (int i = 0; i < matrix.length; i++) {
                result += matrix[start][i];
            }
        }
        return result;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> new Sums(horizon(matrix, start), vertical(matrix, start)));
    }

    static class Sums {

        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Sums)) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum
                    && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(rowSum);
            result *= 31 + Integer.hashCode(colSum);
            return result;
        }
    }
}
