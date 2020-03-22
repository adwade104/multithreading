package com.wade.adam.java.multithreading.wait.and.notify;


import java.util.Scanner;

/*
  Every object in java has a wait() method, it is a member of the object class
  Every object in java has a notify() method, it is a member of the object class

  You can only call wait() within synchronized code blocks
  You can only call notify() within synchronized code blocks

  wait() hands over control of the lock that the synchronized block is locked on
  At that point the synchronized thread will lose control of the lock

  This thread will not resume until two things happen:
     1. It must regain control of the lock
     2. We must call notify() from another thread locked on the same object

  *Very important to realize that both of these synchronized blocks are (and must be) locked on the SAME object*

  As an example:
    1. Consumer thread sleeps
    2. Producer thread obtains the intrinsic lock on Processor
    3. Producer thread prints and then calls wait(), releasing the intrinsic lock
    4. Consumer thread picks up intrinsic lock
    5. Consumer thread waits for return key press
    6. When the return key is pressed, Consumer calls notify()
    7. notify() will notify the first other thread that locked on the object that it can wake up
    8. However, notify() does NOT automatically relinquish control...
       so you should release control of the lock quickly after it is called,
       otherwise the other thread (producer thread) will not be able to continue as it will not get
       a chance to reacquire the lock.

  notifyAll() notifies all threads waiting on a lock
*/
public class Processor {

    public void produce() throws InterruptedException {

        synchronized (this){
            System.out.println("Producer thread running...");
            wait();
            System.out.println("Resumed.");
        }

    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000); //Ensures that the producer kicks off first

        Scanner scanner = new Scanner(System.in);

        synchronized (this){
            System.out.println("Waiting for return key...");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            notify();

            /*This line below is just added to show that notify() does not relinquish control.
            If you uncomment Thread.sleep(), you will see that the producer will wait the full five seconds
            while the consumer to finishes before it retakes control*/

            //Thread.sleep(5000);
        }
    }

}
