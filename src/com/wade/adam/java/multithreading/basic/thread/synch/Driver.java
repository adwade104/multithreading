package com.wade.adam.java.multithreading.basic.thread.synch;

import java.util.Scanner;

/*
  We have two threads. The main program thread and the processor thread.
  Both threads are accessing the same field variable. When java tries to optimize code,
  one single thread does not expect other threads to modify its data.
*/
public class Driver {

    public static void main(String[] args) {

        Processor processor1 = new Processor();
        processor1.start();

        System.out.println("Press enter to stop...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        processor1.shutdown();

    }

}

/*
  Under some conditions, this thread may decide to cache the value of "running" at the start.
  So it will never see the changed value of it, even if we explicitly call shutdown.

  Volatile is used to prevent threads from caching variables when they are not
  changed from within that thread.

  If you want to change the variable from another thread, you need to either
  use volatile OR thread synchronization.
*/
class Processor extends Thread {


    private volatile boolean running = true;

    @Override
    public void run() {

        while (running) {
            System.out.println("Hello");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void shutdown() {
        running = false;
    }

}
