package ru.job4j.threads.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int threshold;

    public SimpleBlockingQueue(int threshold) {
        this.threshold = threshold;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == threshold) {
            this.wait();
        }
        queue.add(value);
        this.notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        T result = queue.poll();
        this.notify();
        return result;
    }
}