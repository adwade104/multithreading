package com.wade.adam.java.multithreading.reentrant.locks.demo2;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
  lock.newCondition()
     Returns a new Condition instance that is bound to a Lock instance.
     Before waiting on the condition the lock must be held by the current thread.
     A call to Condition.await() will atomically release the lock
     before waiting and re-acquire the lock before the wait returns.

  condition.await() does the same thing as wait() inside a synchronized block.
  It hands over the intrinsic lock (i.e. unlocks the lock)

  condition.signal() does the same thing as notify() inside a synchronized block.
  It acquires the intrinsic lock  (i.e. locks the lock)

  1. Second thread sleeps
  2. First thread acquires the lock
  3. First threat releases the lock
  4. Second thread acquires the lock
  5. Second thread requests return key press
  6. Return key is pressed
  7. Second thread notifies first thread that it can continue, but does not hand
     over the intrinsic lock immediately

  When you call condition.signal(), you should call lock.unlock() immediately afterwards.
  If you do not, it will not give the first thread enough time to reacquire the lock.
*/
public class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("Waiting...");
        condition.await();
        System.out.println("Woken up!");

        try {
            increment();
        }
        finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();

        System.out.println("Press the return key!");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Return key was pressed!");

        condition.signal();

        try {
            increment();
        } finally {
            lock.unlock();
        }

    }

    private void increment(){
        for(int i = 0; i < 10000; i++){
            count++;
        }
    }

    public void finished(){
        System.out.println(String.format("Count is: %d", count));
    }

}
