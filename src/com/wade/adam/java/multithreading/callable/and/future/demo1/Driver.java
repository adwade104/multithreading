package com.wade.adam.java.multithreading.callable.and.future.demo1;

import java.util.Random;
import java.util.concurrent.*;

/*
  Callable is an alternative to Runnable that allows you to return a value from a thread.
  When you submit a thread for execution using callable interface, you will get a future back.

  Calling future.get() will get the value if it is available. For example, if you were to call
  executorService.awaitTermination(1, TimeUint.DAYS) on the line before it.

  If you haven't decided to await termination of your threads, future.get() will block the current thread until the
  thread that it is tied to has finished executing and it can obtain the result.
 */
public class Driver {

    public static void main(String[] args)  {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future =
                executorService.submit(new Callable<Integer>() {

                    @Override
                    public Integer call() throws Exception {
                        Random random = new Random();
                        int duration = random.nextInt(4000);

                        System.out.println("Starting...");

                        try {
                            Thread.sleep(duration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Finished.");

                        return duration;
                    }
                });

        executorService.shutdown();

        try {
            System.out.println(String.format("Result is: %d", future.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("Exception: " + e);
        }

    }

}
