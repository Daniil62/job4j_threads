package ru.job4j.threads.concurrent.visibility;

import java.io.*;

public final class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
