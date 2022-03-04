package ru.job4j.threads.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(() -> { });
        Thread second = new Thread(() -> { });
        System.out.println("First thread state: " + first.getState());
        System.out.println("Second thread state " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + " " + first.getState());
            System.out.println(second.getName() + " " + second.getState());
        }
        System.out.println("First thread state: " + first.getState());
        System.out.println("Second thread state " + second.getState());
        System.out.println("Background threads terminated.");
    }
}
