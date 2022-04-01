package ru.job4j.threads.nonblocking;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CASCountTest {

    @Test
    public void whenIncrementCompletedThenGet() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(count.get(), is(1));
    }

    @Test
    public void whenIncrementTo4StepsThenAnother3() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(count.get(), is(1));
        count.increment();
        assertThat(count.get(), is(2));
    }

    @Test
    public void whenIncrementExecuteInBackgroundThread() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread = new Thread(count::increment);
        thread.start();
        thread.join();
        assertThat(count.get(), is(1));
    }

    @Test
    public void whenIncrementExecuteInDifferentThreads() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(count.get(), is(2));
    }
}
