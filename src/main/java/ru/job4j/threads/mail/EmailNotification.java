package ru.job4j.threads.mail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        if (user != null) {
            pool.submit(() -> {
                String email = user.getMale();
                String subject = String.format("subject = Notification {%s} to email {%s}", user.getName(), email);
                String body = String.format("body = Add a new event to {%s}", user.getName());
                send(subject, body, email);
            });
        }
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
