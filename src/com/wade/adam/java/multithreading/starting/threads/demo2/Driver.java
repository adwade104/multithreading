package com.wade.adam.java.multithreading.starting.threads.demo2;

public class Driver {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runner());
        t1.start();

        Thread t2 = new Thread(new Runner());
        t2.start();

    }

}

class Runner implements Runnable {

    @Override
    public void run() {

        for(int i = 0; i < 10; i++){
            System.out.println("Hello " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
