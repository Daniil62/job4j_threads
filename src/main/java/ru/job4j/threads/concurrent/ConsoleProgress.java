package ru.job4j.threads.concurrent;

public class ConsoleProgress implements Runnable {

    private final static String[] PROCESS = new String[]{"\\", "|", "/", "—"};
    private final static int FREQUENCY = 500;
    private final static int TIME = 10000;

    @Override
    public void run() {
        int index = 0;
        Thread thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            System.out.print("\r load: " + PROCESS[index++]);
            index = index == PROCESS.length ? 0 : index;
            try {
                Thread.sleep(FREQUENCY);
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(TIME);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
