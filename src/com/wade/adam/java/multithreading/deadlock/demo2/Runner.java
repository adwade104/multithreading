package com.wade.adam.java.multithreading.deadlock.demo2;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
  One way to acquire locks without deadlocking is to always acquire them in the same order.
  Here is another example of acquiring locks without deadlocking.

  The acquireLocks() method makes sure that a thread safely acquires both locks before proceeding.

  Scenario 1:
        When acquiring locks:
            1. Thread is able to acquire the first lock
            2. Thread is able acquire the second lock
            3. Method returns and thread can proceed

  Scenario 2:
        When acquiring locks:
            1. Thread is able to acquire the first lock
            2. Thread is unable to acquire the second lock
            3. Thread unlocks the first lock
            4. Thread sleeps 1 ms
            5. Thread attempt to acquire the second lock
            6. Thread acquires the second lock and proceeds

  Scenario 3:
        When acquiring locks:
            1. Thread is able to acquire the second lock
            2. Thread is unable to acquire the first lock
            3. Thread unlocks the second lock
            4. Thread sleeps for 1 ms
            5. Thread attempts to acquire the first lock
            6. Thread acquires the first lock and proceeds
*/
public class Runner {

    Account account1 = new Account();
    Account account2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public void firstThread() throws InterruptedException{
        Random random = new Random();

        for(int i = 0; i < 10000; i++){

            acquireLocks(lock1,lock2);

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

            acquireLocks(lock2, lock1);

            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true){
            //acquire locks
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try{
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            }
            finally {
                if(gotFirstLock && gotSecondLock){
                    return;
                }

                if (gotFirstLock){
                    firstLock.unlock();
                }

                if (gotSecondLock){
                    secondLock.unlock();
                }
            }

            //locks not acquired
            Thread.sleep(1);
        }
    }

    public void finished(){
        System.out.println(String.format("Account 1 balance: %d", account1.getBalance()));
        System.out.println(String.format("Account 2 balance: %d", account2.getBalance()));
        System.out.println(String.format("Total Balance: %d", (account1.getBalance() + account2.getBalance())));
    }

}
