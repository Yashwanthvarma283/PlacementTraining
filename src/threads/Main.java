package threads;

class SleepExample extends Thread {
    public void run() {
        for(int i = 1; i <= 5; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000); // pause 1 sec
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SleepExample t1 = new SleepExample();
        SleepExample t2 = new SleepExample();
        SleepExample t3 = new SleepExample();
        SleepExample t4 = new SleepExample();
        SleepExample t5 = new SleepExample();
        SleepExample t6 = new SleepExample();
        SleepExample t7 = new SleepExample();
        SleepExample t8 = new SleepExample();
        SleepExample t9 = new SleepExample();
        SleepExample t10 = new SleepExample();
        SleepExample t11 = new SleepExample();
        SleepExample t12 = new SleepExample();
        SleepExample t13 = new SleepExample();
        SleepExample t14 = new SleepExample();
        SleepExample t15 = new SleepExample();
        SleepExample t16 = new SleepExample();
        SleepExample t17 = new SleepExample();
        SleepExample t18 = new SleepExample();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        t14.start();
        t15.start();
        t16.start();
        t17.start();
        t18.start();
    }
}
