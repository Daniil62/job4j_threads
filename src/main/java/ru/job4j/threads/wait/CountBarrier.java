package ru.job4j.threads.wait;

public class CountBarrier {

    private final int total;
    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        this.notifyAll();
    }

    public synchronized void await() {
        while (count < total) {
            try {
                this.wait(total);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}