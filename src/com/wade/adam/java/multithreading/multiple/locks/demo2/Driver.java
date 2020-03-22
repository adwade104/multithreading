package com.wade.adam.java.multithreading.multiple.locks.demo2;

public class Driver {

    public static void main(String[] args) throws InterruptedException {

        Worker worker = new Worker();
        worker.runCorrectlyButInefficiently();

    }

}
