package ru.job4j.threads.concurrent.load;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class FileDownload implements Loader {

    private final String fileName;
    private static final int BYTES_BUFFER = 1024;

    public FileDownload(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load(String url, int bytes, int time) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[BYTES_BUFFER];
            int bytesRead;
            long bytesWritten = 0;
            Instant start = Instant.now();
            while ((bytesRead = in.read(dataBuffer, 0, BYTES_BUFFER)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesWritten += bytesRead;
                if (bytesWritten >= bytes) {
                    long processTime = ChronoUnit.MILLIS.between(start, Instant.now());
                    if (time > processTime) {
                        TimeUnit.MILLISECONDS.sleep(time - processTime);
                    }
                    bytesWritten = 0;
                    start = Instant.now();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}