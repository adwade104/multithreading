package com.wade.adam.java.multithreading.multiple.locks.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
  You get the same problems here with threads interleaving as with the synch keyword example.
  Writing to a list is not a single step (atomic) operation.
  Multiple operations are occurring behind the scenes when writing to that list.

  A solution is presented in demo2.
*/
public class Worker {

    private Random random = new Random();

    private List<Integer> integersOne = new ArrayList<>();
    private List<Integer> integersTwo = new ArrayList<>();

    public void runIncorrectly() throws InterruptedException {

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
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        integersOne.add(random.nextInt(100));
    }

    public void stageTwo() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        integersTwo.add(random.nextInt(100));
    }

}
