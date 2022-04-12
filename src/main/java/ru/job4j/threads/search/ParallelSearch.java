package ru.job4j.threads.search;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    private final static int MAX_LENGTH = 10;

    private ParallelSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        Integer result;
        if (to - from <= MAX_LENGTH) {
            result = linearSearch();
        } else {
            int mid = (from + to) / 2;
            ParallelSearch<T> leftSearch = new ParallelSearch<>(array, value, from, mid);
            ParallelSearch<T> rightSearch = new ParallelSearch<>(array, value, mid + 1, to);
            leftSearch.fork();
            rightSearch.fork();
            Integer leftResult = leftSearch.join();
            Integer rightResult = rightSearch.join();
            result = leftResult != -1 ? leftResult : rightResult;
        }
        return result;
    }

    private Integer linearSearch() {
        int result = -1;
        if (array != null && value != null && from >= 0 && from < to && to <= array.length) {
            for (int i = from; i < to; i++) {
                if (value.equals(array[i])) {
                    result = i;
                    break;
                }
            }
        }
        return result;
    }

    public static <T> Integer search(T[] array, T value) {
        return new ForkJoinPool().invoke(new ParallelSearch<>(array, value, 0, array.length));
    }

}