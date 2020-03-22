package com.wade.adam.java.multithreading.multiple.locks.demo3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
  We can fix the issues found in demo2 by adding two lock objects
  and using synchronized code blocks instead of synchronized methods.

  Since we are locking on different objects, one thread can run stageOne() while another
  runs stageTwo(), but no two threads can run stageOne() at the same time and no two threads
  can run stageTwo() at the same time.

  Now we are back to our normal runtime of about two to three seconds.

  -----
  Note: It is best practice to declare separate lock objects and not lock on the
  objects you are writing to to avoid issues/confusions.
  -----
*/
public class Worker {

    private Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private List<Integer> integersOne = new ArrayList<>();
    private List<Integer> integersTwo = new ArrayList<>();

    public void runCorrectlyAndEfficiently() throws InterruptedException {

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

    public void stageOne()  {

        synchronized (lock1) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            integersOne.add(random.nextInt(100));

        }
    }

    public synchronized void stageTwo() {

        synchronized (lock2) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            integersTwo.add(random.nextInt(100));

        }

    }

}
