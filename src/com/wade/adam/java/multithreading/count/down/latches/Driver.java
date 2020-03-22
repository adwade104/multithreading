package com.wade.adam.java.multithreading.count.down.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
  There are lots of classes in java that are designed to be thread safe, CountDownLatch is one of them.

  CountDownLatch counts down from a number that you specify and makes one or more threads
  wait until the latch count has reached zero.
*/
public class Driver {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 3; i++){
            executorService.submit(new Processor(countDownLatch));
        }

        countDownLatch.await(); //Causes the current thread to wait until the latch has counted down to zero

        System.out.println("Completed.");
    }

}

class Processor implements Runnable {

    private CountDownLatch latch; //No need to use synchronized, this class is threadsafe

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        System.out.println("Started.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown(); //Decrements the count of the latch by one
    }

}
