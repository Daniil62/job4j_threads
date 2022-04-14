package ru.job4j.threads.examples.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAnyOff {

    /**
     * метод с асинхронной задачей возвращающей CompletableFuture<String>.
     */
    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }

    /**
     * Метод вызывающий четыре асинхронные задачи.
     * Метод anyOf обеспечивает выполнение всех объединенных
     * в нем задач. Возвращает результат первой из четырех выполненных задач.
     */
    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        CompletableFutureAnyOff.anyOfExample();
    }
}
