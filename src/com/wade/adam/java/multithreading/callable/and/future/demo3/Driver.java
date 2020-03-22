package com.wade.adam.java.multithreading.callable.and.future.demo3;

import java.util.Random;
import java.util.concurrent.*;

/*
  This example shows how you set the return type of a Callable as Void. This allows you to
  use methods of Future without returning a result.
*/
public class Driver {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?> future =
                executorService.submit(new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        Random random = new Random();
                        int duration = random.nextInt(4000);

                        System.out.println("Starting...");

                        try {
                            Thread.sleep(duration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Finished.");

                        return null;
                    }
                });

        executorService.shutdown();

        System.out.println("Future done?: " + future.isDone());

        future.get();

        System.out.println("Future done?: " + future.isDone());
    }

}
