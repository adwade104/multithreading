package com.wade.adam.java.multithreading.synch.keyword.demo1;

/*
  The problem with this example is that count++ is not an atomic operation.
  It looks like something that happens in one step but it doesn't

  Count++ is equivalent to count = count + 1;
  1. Read the current value of count
  2. Add one to the value of count
  3. Set count to the new value (store it back in count)

  From a computer point of view, there is considerable time between each step,
  because it is continuing to do other things.

  Let count = 100;
  Thread 1 could fetch the value of count when it is 100
  Thread 2 could, at the same time, fetch the value of count when it is 100

  Thread 1 increments it and stores it back in the original variable
  Count is now 101

  Thread 2 increments it and and stores it as 101
  Count is now 101

  -----

  Worse than that:

    Thread 1 could read value of count as 100
    Thread 2 might increment count twice in that time before
    Thread 1 then gets around to storing its incremented value back

  -----

  Basically some of the increments are being skipped

  We need to find a way of making sure that in the time where a thread reads a
  value of count, increments it, and stores it back, no other thread can get at it.

  -----

  A solution is proposed in demo2.

*/
 public class Driver {

    private int count = 0;

    public static void main(String[] args) throws InterruptedException {

        Driver driver = new Driver();
        driver.doWorkIncorrectly();

    }

    public void doWorkIncorrectly() throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10000; i++){
                    count++;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10000; i++){
                    count++;
                }
            }
        });

        /*
         Start both threads
         Threads t1 & t2 are running in parallel
        */
        t1.start();
        t2.start();

        /*
         Wait for both threads to die (finish)
         Join pauses the execution of the thread it is called in (in our case, the main thread)
         t1.join() waits until t1 finishes
         t2.join() waits until t2 finishes
        */
        t1.join();
        t2.join();

        /*
         Printing count
         We would expect this to always be 20000, but it is not due to the threads interleaving
        */
        System.out.println(String.format("Count is: %s", count));

    }

}
