package ru.job4j.threads.sum;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RolColSumTest {

    @Test
    public void sumTest() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {2, 2, 3},
                {3, 2, 3}
        };
        assertThat(RolColSum.sum(matrix), is(new RolColSum.Sums[]{
                new RolColSum.Sums(6, 6),
                new RolColSum.Sums(7, 6),
                new RolColSum.Sums(8, 9)
        }));
    }

    @Test
    public void compareBetweenSumAndAsyncSumResults() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {2, 2, 3},
                {3, 2, 3}
        };
        assertThat(RolColSum.sum(matrix), is(RolColSum.asyncSum(matrix)));
    }
}
