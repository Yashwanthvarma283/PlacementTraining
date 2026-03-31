package threads;
    class Numbers implements Runnable {
        public void run() {
            for(int i=1; i<=5; i++) {
                System.out.println(i);
            }
        }
    }

public class ThreadsPractice {
        public static void main(String[] args) {
            Thread t = new Thread(new Numbers());

            Thread u=new Thread(new Numbers());
            t.start();
            u.start();
//            t.run();
//            u.run();

        }
    }