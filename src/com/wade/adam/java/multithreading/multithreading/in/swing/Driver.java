package com.wade.adam.java.multithreading.multithreading.in.swing;

import javax.swing.*;

public class Driver {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame("SwingWorker Demo");
            }
        });

    }

}
