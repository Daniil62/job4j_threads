package ru.job4j.threads.examples.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureThenApply {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * метод с асинхронной задачей возвращающей CompletableFuture<String>.
     */
    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    /**
     * Запуск асинхронной задачи, по завершении
     * которой, выполняется действие с преобразованием
     * возвращенной этой задачей строкой. Метод thenApply
     * принимает аргументом Function.
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    public static void main(String[] args) throws Exception {
        CompletableFutureThenApply.thenApplyExample();
    }
}
