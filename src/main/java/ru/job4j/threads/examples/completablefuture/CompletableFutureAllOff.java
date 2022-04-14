package ru.job4j.threads.examples.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAllOff {

    /**
     * метод с асинхронной задачей возвращающей CompletableFuture<Void>.
     */
    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    /**
     * Метод вызывающий четыре асинхронные задачи.
     * Метод allOf обеспечивает выполнение всех объединенных
     * в нем задач.
     */
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static void main(String[] args) throws Exception {
        CompletableFutureAllOff.allOfExample();
    }
}
