package ru.job4j.threads.mail;

public class User {

    private final String name;
    private final String male;

    public User(String nme, String male) {
        this.name = nme;
        this.male = male;
    }

    public String getName() {
        return name;
    }

    public String getMale() {
        return male;
    }
}
