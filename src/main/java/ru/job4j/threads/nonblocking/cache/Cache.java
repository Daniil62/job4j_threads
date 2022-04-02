package ru.job4j.threads.nonblocking.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();
    private final static String ERROR_MESSAGE = "Versions are not equal";

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }
    
    public boolean update(Base model) {
        return model != null
                && memory.computeIfPresent(model.getId(), (k, v) -> {
                    if (v.getVersion() != model.getVersion()) {
                        throw new OptimisticException(ERROR_MESSAGE);
                    }
                    v = new Base(v.getId(), v.getVersion() + 1);
                    v.setName(model.getName());
                    return v;
        }) != null;
    }
    
    public void delete(Base model) {
        if (model != null) {
            memory.remove(model.getId());
        }
    }
}