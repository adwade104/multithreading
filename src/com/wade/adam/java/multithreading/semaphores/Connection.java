package com.wade.adam.java.multithreading.semaphores;

import java.util.concurrent.Semaphore;

/*
  Semaphore:
    An object that maintains a count
    We refer to the count as the number of available permits of the semaphore.
    Semaphores are usually used to control access to a resource.

  semaphore.release()
    Increments the number of available permits

  semaphore.acquire()
    Decrements the amount of available permits. If the amount of available permits is zero,
    it will wait indefinitely until a permit is released somewhere.

  acquire() and release() with a semaphore that has one permit acts kind of like a lock. The difference is that
  with a lock, you must unlock with the same thread that you did the lock from. With a semaphore there is no such
  requirement.

  Any number of different threads can acquire permits until no permits remain.

  Example below:
    1. Thread one acquires a permit
    2. Thread two acquires a permit
    3. Thread three acquires a permit
    4. Other threads wait for more permits to become available
    5. Thread one releases a permit
    6. Available thread acquires the permit released by thread one
    7. Thread two releases a permit
    8. Available thread acquires the permit released by thread two
    9. Thread three releases a permit
    10. Available thread acquires the permit released by thread three

  Note: Similar to lock.unlock(), semaphore.release() must be called in a finally block to prevent
  unexpected behavior in the event of an exception before it is called, because the finally block
  will always execute.

  One other thing to note is that when creating a semaphore, you will usually want fair set to true.

  Fair (true/false):

    When true, whichever thread called acquire first will be the first one to get a permit when
    a permit becomes available.

    There may be performance benefits to having it equal to false, but you usually want it to be set to
    true, because you normally don't want it to be leaving a thread in the background while it services other threads.
*/
public class Connection {

    private static Connection instance = new Connection();

    private int connections = 0;

    private Semaphore semaphore = new Semaphore(3, true);

    private Connection(){}

    public static Connection getInstance(){
        return instance;
    }

    public void connect(){

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } finally {
            semaphore.release();
        }

    }

    public void doConnect(){

        synchronized (this){
            connections++;
            System.out.println(String.format("Current connections: %d", connections)); //Acquire a connection
        }

        try {
            Thread.sleep(2000); //Simulated work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this){
            connections--;  //Release the connection
        }

    }

}
