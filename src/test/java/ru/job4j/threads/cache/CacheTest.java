package ru.job4j.threads.cache;

import org.junit.Test;
import ru.job4j.threads.nonblocking.cache.Base;
import ru.job4j.threads.nonblocking.cache.Cache;
import ru.job4j.threads.nonblocking.cache.OptimisticException;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenBaseAdded() {
        Cache cache = new Cache();
        assertTrue(cache.add(new Base(1, 1)));
    }

    @Test
    public void whenTryToAddBaseWithAlreadyExistingVersion() {
        Cache cache = new Cache();
        Base item = new Base(1, 1);
        cache.add(item);
        assertFalse(cache.add(new Base(1, 1)));
    }

    @Test
    public void whenTryToAddBaseWithAlreadyExistingVersionAfterDeleting() {
        Cache cache = new Cache();
        Base item = new Base(1, 1);
        cache.add(item);
        assertFalse(cache.add(new Base(1, 1)));
        cache.delete(item);
        assertTrue(cache.add(new Base(1, 1)));
    }

    @Test
    public void whenBaseUpdated() {
        Cache cache = new Cache();
        Base item = new Base(1, 1);
        cache.add(item);
        item.setName("name");
        assertTrue(cache.update(item));
    }

    @Test(expected = OptimisticException.class)
    public void whenBaseCanNotBeUpdated() {
        Cache cache = new Cache();
        Base item = new Base(1, 1);
        cache.add(item);
        item.setName("name");
        cache.update(new Base(1, 25));
    }
}
