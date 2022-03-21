package ru.job4j.threads.concurrent.storage;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    private UserStorage storage;
    private User user1;
    private User user2;

    @Before
    public void init() {
        storage = new UserStorage();
        user1 = new User(1, 240000);
        user2 = new User(2, 15000);
    }

    @Test
    public void whenUserAdded() {
        assertTrue(storage.add(user1));
        assertTrue(storage.add(user2));
    }

    @Test
    public void whenUsersAddedFromDifferentThreads() throws InterruptedException {
        Thread first = new Thread(() -> storage.add(user1));
        Thread second = new Thread(() -> storage.add(user2));
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(storage.size(), is(2));
    }

    @Test
    public void whenUserIsNullAndCanNotBeAdded() {
        assertFalse(storage.add(null));
    }

    @Test
    public void whenSimilarUserAlreadyExist() {
        assertTrue(storage.add(user1));
        assertFalse(storage.add(new User(1, 240000)));
    }

    @Test
    public void whenUserUpdated() {
        storage.add(user1);
        assertTrue(storage.update(new User(1, 300000)));
    }

    @Test
    public void whenUserNotUpdated() {
        storage.add(user1);
        assertFalse(storage.update(new User(3, 300000)));
    }

    @Test
    public void whenUsersDeleted() {
        storage.add(user1);
        storage.add(user2);
        assertThat(storage.size(), is(2));
        assertTrue(storage.delete(user1));
        assertThat(storage.size(), is(1));
        assertTrue(storage.delete(new User(2, 15000)));
        assertThat(storage.size(), is(0));
    }

    @Test
    public void whenUsersDeletedFromDifferentThreads() throws InterruptedException {
        storage.add(user1);
        storage.add(user2);
        Thread first = new Thread(() -> storage.delete(user1));
        Thread second = new Thread(() -> storage.delete(user2));
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(storage.size(), is(0));
    }

    @Test
    public void whenUserNotDeleted() {
        storage.add(user1);
        assertThat(storage.size(), is(1));
        assertFalse(storage.delete(new User(3, 1000)));
        assertThat(storage.size(), is(1));
    }

    @Test
    public void whenTransferSuccessful() {
        storage.add(user1);
        storage.add(user2);
        assertTrue(storage.transfer(1, 2, 1000));
    }

    @Test
    public void whenTransfersExecuteFromDifferentThreads() throws InterruptedException {
        storage.add(user1);
        storage.add(user2);
        Thread first = new Thread(() -> storage.transfer(1, 2, 1000));
        Thread second = new Thread(() -> storage.transfer(1, 2, 1000));
        first.start();
        first.join();
        second.start();
        second.join();
        assertThat(storage.getAmount(1), is(238000));
        assertThat(storage.getAmount(2), is(17000));
    }

    @Test
    public void whenAmountInsufficient() {
        storage.add(user1);
        storage.add(user2);
        assertFalse(storage.transfer(1, 2, 260000));
    }

    @Test
    public void whenReceiverInvalid() {
        storage.add(user1);
        storage.add(user2);
        assertFalse(storage.transfer(1, 7, 1000));
    }
}
