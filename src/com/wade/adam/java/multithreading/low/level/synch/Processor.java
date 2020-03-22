package com.wade.adam.java.multithreading.low.level.synch;

import java.util.LinkedList;
import java.util.Random;

/*
  We are using the linked list as our shared data store.
  We have to synchronize on our shared data store because it is not inherently threadsafe.

  Producer
  1. The producer gains control of the intrinsic lock
  2. Producer checks the list size
  3. As long as the list is full, the producer keeps releasing control of the intrinsic lock
  4. When the list is not full, the producer adds an item and notifies the consumer thread that it can wake up

  Consumer
  1. The consumer gains control of the intrinsic lock
  2. Consumer checks the list size
  3. As long as the list is empty, the consumer keeps releasing control of the intrinsic lock
  4. When the list is not empty, the consumer removes the first item and notifies the producer thread that it can wake up
  5. Then, the consumer sleeps from a random interval between 0 and 1 ms

  Running the example, we see that the producer produces a mass amount of items, while the consumer
  has a delay between each item it consumes, so the list size levels off at around the max of 10 items.
*/
public class Processor {

    private LinkedList<Integer> integerList = new LinkedList<>();
    private final int LIMIT = 10;
    private final Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;

        while(true){
            synchronized (lock) {
                while (integerList.size() == LIMIT){
                    lock.wait();
                }
                integerList.add(value++); //Adds the item, then increases the value
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {

        Random random = new Random();

        while(true){
            synchronized (lock) {
                while(integerList.size() == 0){
                    lock.wait();
                }
                System.out.print(String.format("List size is: %d", integerList.size()));
                int value = integerList.removeFirst();
                System.out.println(String.format("; value is %d", value));
                lock.notify();
            }

            Thread.sleep(random.nextInt(1000));
        }

    }

}
