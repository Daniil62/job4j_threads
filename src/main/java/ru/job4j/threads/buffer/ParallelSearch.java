package ru.job4j.threads.buffer;

import ru.job4j.threads.queue.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        final Thread consumer = new Thread(
                () -> {
                    Thread currentThread = Thread.currentThread();
                    try {
                        while (!currentThread.isInterrupted()) {
                            System.out.println(queue.poll());
                        }
                    } catch (InterruptedException e) {
                        currentThread.interrupt();
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index < 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}