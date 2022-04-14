package ru.job4j.threads.examples.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureThenCombine {

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
     * которой, выполняется действие с объединением
     * двух объектов. Метод thenCombine используется,
     * если действия могут быть выполнены независимо друг
     * от друга. Причем в качестве второго аргумента, нужно
     * передавать BiFunction – функцию, которая преобразует
     * результаты двух задач во что-то одно.
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static void main(String[] args) throws Exception {
        CompletableFutureThenCombine.thenCombineExample();
    }
}
