package com.wade.adam.java.multithreading.interrupting.threads;

import java.util.Random;

/*
The example below shows how you can set the interrupted flag, detect it, and handle it.

Thread pools have a cancel() method, which allows them to cancel threads before they even run.
The cancel() method will set the interrupted flag on a thread.

Also, if you run a thread using a callable and return a future from it, the future has a method called
cancel/interrupt, which achieves exactly the same effect.

t1.interrupt() will not interrupt running code. It will only set the interrupt flag, which
you need to again check...UNLESS you have a wait() or a sleep(), which will pick that interrupted state
up automatically.

Note: 1E8 = 1x10^8 = 100000000
*/
public class Driver {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting...");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                for(int i = 0; i < 1E8; i++){

                    /*
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted!");
                        break;
                    }*/

                    try{
                        Thread.sleep(1);
                    } catch (InterruptedException e){
                        System.out.println("Interrupted!");
                        break;
                    }

                    Math.sin(random.nextDouble());
                }
            }
        });
        t1.start();

        Thread.sleep(500); //Give a little bit of time for thread to start running

        t1.interrupt(); //Sets the interrupted flag

        t1.join();

        System.out.println("Finished.");

    }

}
