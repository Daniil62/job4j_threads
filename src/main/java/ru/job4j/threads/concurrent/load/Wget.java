package ru.job4j.threads.concurrent.load;

public class Wget implements Runnable {

    private final String url;
    private final int bytes;
    private final int time;
    private final Loader loader;

    private final static String ERROR_MESSAGE = "Missing or invalid arguments.";

    public Wget(String[] args, Loader loader) {
        validate(args);
        this.url = args[0];
        this.bytes = Integer.parseInt(args[1]);
        this.time = Integer.parseInt(args[2]);
        this.loader = loader;
    }

    @Override
    public void run() {
        loader.load(url, bytes, time);
    }

    private void validate(String[] args) {
        if (args.length < 4 || !args[0].startsWith("http")
                || !args[1].matches("\\d+") || !args[2].matches("\\d+")) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Loader loader = new FileDownload(args[3]);
        Thread wget = new Thread(new Wget(args, loader));
        wget.start();
        wget.join();
    }
}