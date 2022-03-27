package ru.job4j.threads.queue;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void multiThreadOfferAndPollTest() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(1);
        AtomicReference<String> word = new AtomicReference<>("");
        Thread producer = new Thread(() -> {
            try {
                queue.offer("result");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                word.set(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        assertThat(word.get(), is(""));
        consumer.start();
        consumer.join();
        assertThat(word.get(), is("result"));
    }
}