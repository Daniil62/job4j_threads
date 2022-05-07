package ru.job4j.threads.examples.concurrent.synchronizers;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    private final static int FREE_WAY_CONTROL_COUNT = 2;
    private final static int THREADS_COUNT = 7;

    private static final boolean[] WAY_CONTROLS = new boolean[FREE_WAY_CONTROL_COUNT];

    private static final String ON_CRITICAL_SECTION_MESSAGE = "Thread number %d before critical section.";
    private static final String CHECK_TO_PERMISSION_MESSAGE = "\tThread number %d check to blocking permission.";
    private static final String IN_CRITICAL_SECTION_MESSAGE = "\t\tThread number %d in critical section. control = %d";
    private static final String FINISH_MESSAGE = "\t\t\tThread number %d finished.";

    /**
     *   Синхронизатор Semaphore предоставляет одновременный
     * доступ к критической секции, количеству потоков,
     * значение которого передано в конструктор семафора.
     * Управление доступом реализовано счетчиком, если
     * значение счетчика более ноля, поток получает доступ
     * и значение счетчика уменьшается на единицу.
     *   Конструктор Semaphore(int permits) передает количество
     * потоков одновременно допускаемых к критической секции
     * не учитывая порядка в котором потоки запрашивают доступ.
     * Конструктор Semaphore(int permits, boolean fair)
     * дополнительно передает условие о предоставлении допуска
     * в том порятке, в котором потоки его запрашивали.
     *   Метод acquire() запрашивает доступ для одного потока,
     * метод acquire(int number) - для группы потоков.
     * Для разблокировки - метод release().
     */
    private static Semaphore semaphore = null;

    public static class Runner implements Runnable {

        private final int number;

        public Runner(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println(String.format(ON_CRITICAL_SECTION_MESSAGE, number));
            try {
                semaphore.acquire();
                System.out.println(String.format(CHECK_TO_PERMISSION_MESSAGE, number));
                int controlNumber = -1;
                synchronized (WAY_CONTROLS) {
                    for (int i = 0; i < FREE_WAY_CONTROL_COUNT; i++) {
                        if (WAY_CONTROLS[i]) {
                            WAY_CONTROLS[i] = false;
                            controlNumber = i;
                            System.out.println(String.format(IN_CRITICAL_SECTION_MESSAGE, number, controlNumber + 1));
                            break;
                        }
                    }
                }
                Thread.sleep(3000);
                synchronized (WAY_CONTROLS) {
                    WAY_CONTROLS[controlNumber] = true;
                }
                semaphore.release();
                System.out.println(String.format(FINISH_MESSAGE, number));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < FREE_WAY_CONTROL_COUNT; i++) {
            WAY_CONTROLS[i] = true;
        }
        semaphore = new Semaphore(FREE_WAY_CONTROL_COUNT, true);
        for (int i = 1; i <= THREADS_COUNT; i++) {
            new Thread(new Runner(i)).start();
            Thread.sleep(400);
        }
    }
}
