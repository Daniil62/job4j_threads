package ru.job4j.threads.examples.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureRun {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * метод с асинхронной задачей возвращающей CompletableFuture<Void>.
     */
    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    /**
     * Запуск асинхронной задачи возвращающей CompletableFuture<Void>.
     */
    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static void main(String[] args) throws Exception {
        CompletableFutureRun.runAsyncExample();
    }
}
