package ru.job4j.threads.pipedstreams;

import java.io.*;

public class SimplePipedStreamExample {

    public static void main(String[] args) throws IOException {

        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();

        final BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);

        Thread firstThread = new Thread(() -> {
            try {
                bufferedOutputStream.write("Job4j_Job4j".getBytes());
                bufferedOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread secondThread = new Thread(() -> {
            try {
                int ch;
                while ((ch = bufferedInputStream.read()) != -1) {
                    Thread.sleep(1000);
                    System.out.print((char) ch);
                }
                bufferedInputStream.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        in.connect(out);
        firstThread.start();
        secondThread.start();
    }
}