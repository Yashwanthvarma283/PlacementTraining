package threads;

class Shared {
    synchronized void waitMethod() {
        try {
            System.out.println("Waiting...");
            wait();
            System.out.println("Resumed");
        } catch (InterruptedException e) {}
    }

    synchronized void notifyMethod() {
        System.out.println("Notifying...");
        notify();
    }
}

public class Notify {
    public static void main(String[] args) {
        Shared obj = new Shared();

        Thread t1 = new Thread(() -> obj.waitMethod());

        Thread t2 = new Thread(() -> {
            try { Thread.sleep(2000); } catch(Exception e){}
            obj.notifyMethod();
        });

        t1.start();
        t2.start();
    }
}
