package com.wade.adam.java.multithreading.semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/*
     Executors.newCachedThreadPool()
       Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads
       when they are available. These pools will typically improve the performance of programs that execute
       many short-lived asynchronous tasks.
*/
public class Driver {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < 200; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }

}
