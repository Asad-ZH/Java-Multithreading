package org.nerdware;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class runnable extends Thread {

    private CountDownLatch latch;

    public runnable(CountDownLatch latch) {
        this.latch = latch;
    }


    public void run() {
        System.out.println("Thread " + latch + " is running");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}

public class Main {
    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(7);

        // Assigning the number of threads to be used
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 7; i++) {
            executor.submit(new runnable(latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All threads are finished");
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}