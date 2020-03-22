package com.wade.adam.java.multithreading.multiple.locks.demo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
  To start to fix the incorrectness in the previous example, we need to add the synchronized keyword to the methods
  where the additions to the lists are taking place (ie. stageOne and stageTwo).

  We would hope that running this example would take two seconds, but it is really taking more like
  four or five seconds. So although this example is working correctly, it is taking twice as long
  as we would expect.

  The reason for this is that when you call the synchronized method, that is going to acquire the intrinsic lock of
  this worker object. So if one thread runs stageOne() and another thread tries to run it, the second thread will
  have to wait until the first thread finishes to acquire that lock.

  The problem here is that there is only one intrinsic lock for the Worker object.
  So if one thread is running stageOne(), another thread will have to wait to run stageTwo().

  1. Thread 1 acquires the lock on Worker object
  2. Thread 1 enters synchronized method stageOne()
  3. Thread 2 needs to acquire the lock on Worker object to enter stageTwo(), but it
     can't because Thread 1 currently has the lock for a different method.

  Yet the stageOne() and stageTwo() methods are independent, and they don't write to the same lists.
  So what we really want is a system by which no two threads can run stageOne() at the same time, and
  no two threads can run stageTwo() at the same time, but one thread can run stageOne() method while another thread
  runs stageTwo().

  We can do this by creating separate locks and synchronizing on them separately.

  A solution is presented in demo3.
*/
public class Worker {

    private Random random = new Random();

    private List<Integer> integersOne = new ArrayList<>();
    private List<Integer> integersTwo = new ArrayList<>();

    public void runCorrectlyButInefficiently() throws InterruptedException {

        System.out.println("Starting...");

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long end = System.currentTimeMillis();

        System.out.println(String.format("Time taken: %d ms", (end - start)));
        System.out.println(String.format("IntegersOne: %d, IntegersTwo: %d", integersOne.size(), integersTwo.size()));

    }

    public void process()  {
        for(int i = 0; i < 1000; i++){
            stageOne();
            stageTwo();
        }
    }

    public synchronized void stageOne()  {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        integersOne.add(random.nextInt(100));
    }

    public synchronized void stageTwo() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        integersTwo.add(random.nextInt(100));
    }

}
