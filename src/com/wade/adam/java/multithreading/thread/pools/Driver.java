package com.wade.adam.java.multithreading.thread.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
  One thread will do one task at a time. As soon as a thread becomes idle, it
  picks up another task and runs until all tasks are complete.

  executor.submit() Submits a Runnable task for execution.

  executor.shutdown() Initiates an orderly shutdown in which previously submitted
  tasks are executed, but no new tasks will be accepted.

  executor.awaitTermination()  Blocks until all tasks have completed execution
  after a shutdown request, or the timeout occurs, or the current thread is
  interrupted, whichever happens first.

  There is a lot of overhead to starting threads, so by recycling them in a pool,
  you avoid that overhead.
*/

public class Driver {

    public static void main(String[] args){

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for(int i = 0; i < 5; i++){
            executor.submit(new Processor(i));
        }

        executor.shutdown();

        System.out.println("All tasks submitted.");

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed.");

    }

}

class Processor implements Runnable {

    private int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println("Starting " + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed " + id);

    }
}