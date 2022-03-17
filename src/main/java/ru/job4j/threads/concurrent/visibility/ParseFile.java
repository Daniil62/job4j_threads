package ru.job4j.threads.concurrent.visibility;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized String getContent(File file, Predicate<Character> filter) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                    .map(String::toCharArray)
                    .forEach(array -> {
                        for (char symbol : array) {
                            builder.append(filter.test(symbol) ? symbol : "");
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public String getFullContent() {
        return getContent(file, symbol -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(file, symbol -> symbol < 0x80);
    }
}