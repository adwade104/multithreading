package com.wade.adam.java.multithreading.callable.and.future.demo2;


import java.util.Random;
import java.util.concurrent.*;

/*
  Another benefit of Callable is that you can throw an exception inside of the call() method.

  The future.get() method will catch your exception, wrap the exception in an ExecutionException, and rethrow it.

  If you would like, you can also get the cause of the ExecutionException and cast it to your original exception type,
  essentially unwrapping it. This way, you can print the message of the original exception,
  as show below:

  catch (ExecutionException e) {
            RuntimeException ex = (RuntimeException) e.getCause();
            System.out.println(ex.getMessage());
  }

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

                        if(duration > 200){
                            throw new RuntimeException("Sleeping for too long!");
                        }

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
            RuntimeException ex = (RuntimeException) e.getCause();
            System.out.println(ex.getMessage());
        }

    }

}
