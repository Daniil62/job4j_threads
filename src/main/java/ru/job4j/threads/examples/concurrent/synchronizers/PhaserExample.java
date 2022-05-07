package ru.job4j.threads.examples.concurrent.synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserExample {

    /**
     *   Синхронизатор Phaser реализует алгоритм
     * работы цикличного барьера.
     *   Phaser может иметь одну и более фаз (барьеров).
     * Каждая фаза имеет свой номер. Количество птоков
     * участвующих в каждой фазе может изменяться, потоки
     * могут быть как зарегистрированы, так и отключены
     * от участия.
     *   Phaser имеет конструкторы:
     *     Phaser();
     *     Phaser(int parties); ----------------- количество участвующих потоков параметрах.
     *     Phaser(Phaser parent); --------------- родительский Phaser в параметрах.
     *     Phaser(Phaser parent, int parties);
     * Методы фазера:
     *     int register(); --------------- регистрирует поток, возвращает номер текущей фазы.
     *     int arrive(); ----------------- указывает на завершение текущей фазы, возвращает ее номер.
     *                                       Если фаза не завершена, возвращается отрицательное число.
     *                                       arrive() не останавливает поток, он продолжает выполняться.
     *     int arriveAndAwaitAdvance(); -- Вызывается потоком, указывает на завершение им текущей фазы.
     *                                       Аналог метода CyclicBarrier.await(), сообщающего о прибытии к барьеру.
     *     int arriveAndDeregister(); ---- Сообщает о завершении потоком всех стадий и дерегистрации,
     *                                       возвращает номер текущей фазы, или отрицательное число, если
     *                                       фазер завершил работу.
     *     int getPhase(); --------------- Возвращает номер текушщей фазы.
     */
    private static Phaser phaser;

    private static final String OPEN = " opening the doors";
    private static final String CLOSE = " closing the doors";

    private static final String WAIT = " wait on station ";
    private static final String ENTER = "  enter the wagon ";
    private static final String EXIT = "  exit out from wagon";
    private static final String SPACE = "    ";

    private static class Passenger implements Runnable {

        private final int id;
        private final int departure;
        private final int destination;

        public Passenger(int id, int departure, int destination) {
            this.id = id;
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + WAIT + departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(SPACE + this + ENTER);
                while (phaser.getPhase() < destination) {
                    phaser.arriveAndAwaitAdvance();
                }
                Thread.sleep(500);
                System.out.println(SPACE + this + EXIT);
                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "passenger " + id + " {" + departure + " -> " + destination + '}';
        }
    }

    public static void main(String[] args) {
        phaser = new Phaser(1);
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < 5; i++)  {
            if ((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(10 + i, i, i + 1));
            }
            if ((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(20 + i, i, 5));
            }
        }
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0: {
                    System.out.println("train started from depot.");
                    phaser.arrive();
                    break;
                }
                case 6: {
                    System.out.println("train go to depot.");
                    phaser.arriveAndDeregister();
                    break;
                }
                default: {
                    int currentStation = phaser.getPhase();
                    System.out.println("station " + currentStation);
                    for (Passenger pass : passengers) {
                        if (pass.departure == currentStation) {
                            phaser.register();
                            new Thread(pass).start();
                        }
                    }
                    System.out.println(OPEN);
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(CLOSE);
                }
            }
        }
    }
}
