package ru.job4j.threads.examples.concurrent.synchronizers;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    private static final int THREADS_COUNT = 5;
    private static final int LENGTH = 3000;

    /**
     * Синхронизатор CountDownLatch блокирует потоки до
     * выполнения заданных условий и достижения обратным
     * счетчиком значения ноля.
     *   CountDownLatch(int number) - в конструктор передается
     * количество событий, которые должны произойти для
     * разблокировки потоков.
     *   Для самоблокировки потоков у CountDownLatch есть
     * два метода - void await() throws InterruptedException
     * и boolean await(long wait, TimeUnit unit) throws InterruptedException,
     * первый ожидает до обнуления счетчика, второй ожидает
     * время переданное в параметры.
     *   Для уменьшения счетчика используется метод void countDown().
     */
    private static CountDownLatch latch;

    private static class Runner implements Runnable {

        private final int number;
        private final int speed;

        private static final String FIRST_MESSAGE = "Thread number %d before critical section";
        private static final String SECOND_MESSAGE = "Thread number %d finished";

        private Runner(int riderNumber, int riderSpeed) {
            this.number = riderNumber;
            this.speed = riderSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.println(String.format(FIRST_MESSAGE, number));
                latch.countDown();
                latch.await();
                Thread.sleep(LENGTH / speed * 10);
                System.out.println(String.format(SECOND_MESSAGE, number));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Runner createRunner(final int number) {
        return new Runner(number, (int) (Math.random() * 10) + 5);
    }

    public static void main(String[] args) throws InterruptedException {
        latch = new CountDownLatch(THREADS_COUNT + 3);
        for (int i = 1; i <= THREADS_COUNT; i++) {
            new Thread(createRunner(i)).start();
            Thread.sleep(1000);
        }
        while (latch.getCount() > 0) {
            System.out.println(String.format("countdown = %d", latch.getCount()));
            latch.countDown();
            Thread.sleep(1000);
        }
    }
}
