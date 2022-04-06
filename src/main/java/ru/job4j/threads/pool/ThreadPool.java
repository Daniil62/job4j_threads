package ru.job4j.threads.pool;

import ru.job4j.threads.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(16);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        while (threads.size() < size) {
            Thread thread = new InnerThread();
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        if (job != null) {
            tasks.offer(job);
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    private final class InnerThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        AtomicInteger value = new AtomicInteger();
        pool.work(value::getAndIncrement);
        pool.work(value::getAndIncrement);
        pool.work(value::getAndIncrement);
        pool.work(value::getAndIncrement);
        pool.shutdown();
        System.out.println(value);
    }
}