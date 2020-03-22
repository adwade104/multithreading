package com.wade.adam.java.multithreading.synch.keyword.demo2;

/*
  Every object in java has an intrinsic/monitor lock (i.e. mutex)

  You have to acquire the intrinsic lock on an object before you can call its
  synchronized method.

  In our case we are calling the synchronized method of the Driver object.

  Only one thread can acquire the intrinsic lock at a time.

  1. One thread  acquires the lock and enters the synchronized method.
  2. Another thread then tries to enter the synchronized method at the same time
  3. Then the second thread will have to quietly wait until the first thread releases
     the intrinsic lock. This happens when the synchronized method finishes executing.

  Need to be aware of this whenever you have multiple threads accessing shared data.

  Now, we don't need to declare count volatile, because if you're running something in
  a synchronized block, it is guaranteed that the current state of the shared variables
  will be physical to all threads.
*/
public class Driver {

    private int count = 0;

    public static void main(String[] args) throws InterruptedException {

        Driver driver = new Driver();
        driver.doWorkCorrectly();

    }

    public synchronized void increment(){
        count++;
    }

    public void doWorkCorrectly() throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10000; i++){
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10000; i++){
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(String.format("Count is: %s", count));

    }
}
