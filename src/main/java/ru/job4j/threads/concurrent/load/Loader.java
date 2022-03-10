package ru.job4j.threads.concurrent.load;

public interface Loader {

    void load(String url, int bytes, int time);
}
