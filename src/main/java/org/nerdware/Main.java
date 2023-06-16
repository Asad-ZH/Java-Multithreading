package org.nerdware;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class runnable extends Thread {
    private int id;

    public runnable(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Thread " + id + " is running");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {

        // Assigning the number of threads to be used
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 7; i++) {
            executor.submit(new runnable(i));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All tasks are completed");
    }
}