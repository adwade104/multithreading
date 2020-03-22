package com.wade.adam.java.multithreading.reentrant.locks.demo1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
  A reentrant lock is an alternative to using the synchronized keyword.

  lock()

     Acquires the lock if it is not held by another thread and returns immediately,
     setting the lock hold count to one.

     If the current thread already holds the lock then the hold
     count is incremented by one and the method returns immediately

     If the lock is held by another thread then the current thread becomes disabled
     for thread scheduling purposes and lies dormant until the lock has been acquired,
     at which time the lock hold count is set to one.

  unlock()

     Attempts to release this lock.

     If the current thread is the holder of this lock then the hold
     count is decremented.  If the hold count is now zero then the lock
     is released.  If the current thread is not the holder of this
     lock then IllegalMonitorStateException is thrown.

  lock.unlock() should always be called in a finally block, because it is always guaranteed
  to execute. Otherwise, you could lock the lock, an exception could be thrown, and it's
  possible that lock.unlock() would never be called. This would result in bad and unpredictable
  behavior.
*/
public class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();

    public void firstThread() throws InterruptedException {
        lock.lock();

        try {
            increment();
        }
        finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        lock.lock();

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
