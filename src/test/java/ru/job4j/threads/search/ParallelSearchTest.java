package ru.job4j.threads.search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParallelSearchTest {

    @Test
    public void searchTest() {
        Integer[] array = new Integer[57];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(ParallelSearch.search(array, 27), is(27));
    }
}
