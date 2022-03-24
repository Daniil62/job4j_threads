package ru.job4j.threads.wait;

public class MultiUser {

    public static void main(String[] args) {
        int value = 100000000;
        CountBarrier barrier = new CountBarrier(value);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    int count = 0;
                    while (count < value) {
                        count++;
                        barrier.count();
                    }
                },
                "Master"
        );
        Thread waiting = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Waiting"
        );
        master.start();
        waiting.start();
    }
}
