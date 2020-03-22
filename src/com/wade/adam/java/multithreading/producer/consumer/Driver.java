package com.wade.adam.java.multithreading.producer.consumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
  Array Blocking Queue: FIFO queue
  Array Blocking Queue is thread safe, we do not need to use synchronized on its operations
  If the queue is at max capacity, put() will wait until items are removed from the queue
  If the queue is empty, take() will wait until an item has been added to execute
*/
public class Driver {

    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        /*Remember, these joins force the main thread to wait until the
        other threads have completed which, in this case, will be never*/
        t1.join();
        t2.join();
    }

    private static void producer() throws InterruptedException {
        Random random = new Random();

        while(true){
            blockingQueue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        Random random = new Random();

        while (true){
            Thread.sleep(100);

            if(random.nextInt(10) == 0){
                Integer value = blockingQueue.take();
                System.out.println(String.format("Took value: %d; Queue size is: %d", value, blockingQueue.size()));
            }
        }

    }
}
