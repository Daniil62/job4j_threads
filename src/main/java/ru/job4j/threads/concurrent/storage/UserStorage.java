package ru.job4j.threads.concurrent.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public final class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean result = user != null && !users.containsKey(user.getId());
        if (result) {
            users.put(user.getId(), user);
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = user != null && users.containsKey(user.getId());
        if (result) {
            users.replace(user.getId(), user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return user != null && users.remove(user.getId(), user);
    }

    public synchronized int size() {
        return users.size();
    }

    public synchronized int getAmount(int id) {
        int result = -1;
        User user = users.get(id);
        if (user != null) {
            result = user.getAmount();
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User sender = users.get(fromId);
        User receiver = users.get(toId);
        if (sender != null && receiver != null) {
            int senderAmount = sender.getAmount();
            if (senderAmount >= amount) {
                sender.setAmount(senderAmount - amount);
                receiver.setAmount(receiver.getAmount() + amount);
                result = true;
            }
        }
        return result;
    }
}
