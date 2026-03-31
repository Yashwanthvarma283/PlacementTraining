package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
        public static void main(String[] args) {
            ExecutorService ex = Executors.newFixedThreadPool(2);

            ex.execute(() -> {
                System.out.println("Task 1");
            });

            ex.execute(() -> {
                System.out.println("Task 2");
            });

            ex.shutdown();
        }
    }
