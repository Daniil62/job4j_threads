package ru.job4j.threads.concurrent;

public class Wget {

    public static void main(String[] args) {
        new Thread(
                () -> {
                    try {
                        int index = 0;
                        while (index <= 100) {
                            System.out.print("\rLoading : " + index++  + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}