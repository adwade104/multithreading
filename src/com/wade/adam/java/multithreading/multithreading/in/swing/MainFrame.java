package com.wade.adam.java.multithreading.multithreading.in.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;


/*
  SwingWorker is proffered to regular threads when working in swing.

  SwingWorker runs tasks in its own background thread.
  You can stop it by canceling it but if you want to run it again, you will need a new swing worker.

  You can update the gui from swing workers done() and process() methods, but not its doInBackground() method.

  The SwingWorker process() method may not publish values each time. It may give you your data in chunks,
  which is why it takes a list as an argument.

  You can throw an exception from doInBackground() and catch and handle it in the done() method. The exception
  you throw will be wrapped in ExecutionException.
*/
public class MainFrame extends JFrame {

    private JLabel countLabel1 = new JLabel("0");
    private JLabel statusLabel = new JLabel("Task not completed.");
    private JButton startButton = new JButton("Start");

    public MainFrame(String title) {
        super(title);


        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        add(countLabel1, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        add(statusLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        add(startButton, gridBagConstraints);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void start(){

        SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception { //Background work
                for(int i = 0; i < 30; i++){
                    Thread.sleep(100);
                    publish(i);
                }

                return true;
            }

            @Override
            protected void process(List<Integer> chunks) { //Called while thread is running

                int value = chunks.get(chunks.size() - 1);
                countLabel1.setText("Current value: " + value);
            }

            @Override
            protected void done() {   //called when thread finishes
                try {
                    Boolean status = get();
                    statusLabel.setText("Task completed with status: " + status);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        };

        swingWorker.execute();
    }
}
