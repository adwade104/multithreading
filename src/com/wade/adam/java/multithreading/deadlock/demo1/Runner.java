package com.wade.adam.java.multithreading.deadlock.demo1;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
  In the example below, we are doing our locks in different orders in the first and second
  thread, which creates a deadlock.

  Example:
    1. First thread acquires lock1
    2. Second thread acquires lock2
    3. First thread tries to acquire lock2, but it cannot, because the second thread has lock2
    4. Second thread tries to acquire lock1, but it cannot, because the first thread has lock1
    5. Neither of the threads can proceed because each thread needs the lock that the other thread has got
    6. This is a deadlock

  Deadlocks occur when you lock your locks in different orders. It can not only happen with re-entrant locks but
  also with nested synchronized blocks when you lock in different orders. The nested synchronized blocks may not
  be obviously nested. You could have one synchronized block in one method, and that could call another method
  which has a synchronized block.

  One solution to this is to always lock your locks in the same order. The other solution is discussed in demo2.
*/
public class Runner {

    Account account1 = new Account();
    Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public void firstThread() throws InterruptedException{
        Random random = new Random();

        for(int i = 0; i < 10000; i++){
            lock1.lock();
            lock2.lock();

            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        Random random = new Random();

        for(int i = 0; i < 10000; i++){
            lock2.lock();
            lock1.lock();

            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished(){
        System.out.println(String.format("Account 1 balance: %d", account1.getBalance()));
        System.out.println(String.format("Account 2 balance: %d", account2.getBalance()));
        System.out.println(String.format("Total Balance: %d", (account1.getBalance() + account2.getBalance())));
    }

}
