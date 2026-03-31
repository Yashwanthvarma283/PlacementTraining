package threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallablePractice {
        public static void main(String[] args) throws Exception {
            ExecutorService ex = Executors.newSingleThreadExecutor();

            Callable<Integer> task = () -> 50;

            Future<Integer> result = ex.submit(task);

            System.out.println("Result: " + result.get());

            ex.shutdown();
        }
    }
