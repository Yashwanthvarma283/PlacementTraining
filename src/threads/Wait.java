package threads;

class MyThread implements Runnable {
    public void run() {
        for(int i = 1; i <= 3; i++) {
            System.out.println("T1: " + i);
            try{
                Thread.sleep(500);
            }
            catch (Exception e){
                System.out.println("Error occures");
            }
        }
    }
}

public class Wait {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyThread());
        Thread t2 = new Thread(new MyThread());

        t1.start();
        t2.start();
    }
}
