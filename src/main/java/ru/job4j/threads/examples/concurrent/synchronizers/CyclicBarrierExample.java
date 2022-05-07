package ru.job4j.threads.examples.concurrent.synchronizers;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    /**
     * Синхронизатор CyclicBarrier похож на
     * CountDownLatch, но его можно переиспользовать
     * в цикле. CyclicBarrier блокирует потоки и ждет
     * готовности нужного количества потоков, после чего
     * работа всех потоков продолжается. Количество потоков
     * определяется значением переданным в конструктор
     * CyclicBarrier(int count). Конструктор
     * CyclicBarrier(int count, Runnable class) вторым
     * параметром принимает объект Runnable который будет
     * запущен в потоках при запуске от точки сбора.
     */
    private static CyclicBarrier barrier;
    private static final int SIZE = 3;
    private static final String START_MESSAGE = "Threads on start.";
    private static final String STARTED_MESSAGE = "Threads started.";
    private static final String READINESS_MESSAGE = "Thread number %d ready to start.";
    private static final String CONTINUED_MESSAGE = "Thread number %d continued work.";

    public static class ThreadsRunner implements Runnable {

        @Override
        public void run() {
            System.out.println(START_MESSAGE);
            try {
                Thread.sleep(1000);
                System.out.println(STARTED_MESSAGE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Runner implements Runnable {

        private final int number;

        public Runner(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            try {
                System.out.println(String.format(READINESS_MESSAGE, number));
                barrier.await();
                System.out.println(String.format(CONTINUED_MESSAGE, number));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        barrier = new CyclicBarrier(SIZE, new ThreadsRunner());
        for (int i = 1; i < 10; i++) {
            new Thread(new Runner(i)).start();
            Thread.sleep(1000);
        }
    }
}
