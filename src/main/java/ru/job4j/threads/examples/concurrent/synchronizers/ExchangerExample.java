package ru.job4j.threads.examples.concurrent.synchronizers;

import java.util.concurrent.Exchanger;

public class ExchangerExample {


    /**
     *   Обобщенный синхронизатор Exchanger бменивает данные
     * из двух потоков. Потоки блокируются до вызова у каждого
     * из них метода T exchange(T data) throws InterruptedException,
     * или T exchange(T data, long wait, TimeUnit unit) throws InterruptedException,
     * первый принимает ссылку на обмениваемые данные,
     * второй дополнительно принимает параметры времени
     * ожидания. Метод exchange не завершится ни в
     * одном из потоков, пока не будет вызван в обоих потоках.
     */
    private static Exchanger<Letter> exchanger;

    private static final String MESSAGE_1 = "Почтальон %s получил письма: %s, %s";
    private static final String MESSAGE_2 = "Почтальон %s выехал из %s в %s";
    private static final String MESSAGE_3 = "Почтальон %s приехал в пункт D";
    private static final String MESSAGE_4 = "Почтальон %s получил письма для %s";
    private static final String MESSAGE_5 = "Почтальон %s привез в %s: %s, %s";

    public static class Postman implements Runnable {

        private final String id;
        private final String departure;
        private final String destination;
        private final Letter[] letters;

        public Postman(String id, String departure, String destination, Letter[] letters) {
            this.id = id;
            this.departure = departure;
            this.destination = destination;
            this.letters = letters;
        }

        @Override
        public void run() {
            try {
                System.out.println(String.format(MESSAGE_1, id, letters[0], letters[1]));
                System.out.println(String.format(MESSAGE_2, id, departure, destination));
                Thread.sleep((long) (Math.random() * 5000) + 5000);
                System.out.println(String.format(MESSAGE_3, id));
                letters[1] = exchanger.exchange(letters[1]);
                System.out.println(String.format(MESSAGE_4, id, destination));
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println(String.format(MESSAGE_5, id, destination, letters[0], letters[1]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Letter {

        private  final String address;

        public Letter(String letter) {
            this.address = letter;
        }

        @Override
        public String toString() {
            return address;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        exchanger = new Exchanger<>();
        Letter[] posts1 = new Letter[] {new Letter("dest. В - Петров"), new Letter("dest. C - Иванов")};
        Letter[] posts2 = new Letter[] {new Letter("dest. C - Семенов"), new Letter("dest. В - Макаров")};
        new Thread(new Postman("a", "A", "B", posts1)).start();
        Thread.sleep(100);
        new Thread(new Postman("b", "B", "C", posts2)).start();
    }
}
