package threads;
class Sharing {
    volatile boolean flag = true;
}
public class Testing {

        public static void main(String[] args) {
            Sharing s = new Sharing();

            Thread t1 = new Thread(() -> {
                while(s.flag) {}
                System.out.println("Stopped");
            });

            Thread t2 = new Thread(() -> {
                try { Thread.sleep(1000); } catch(Exception e){}
                s.flag = false;
            });

            t1.start();
            t2.start();
        }
    }
