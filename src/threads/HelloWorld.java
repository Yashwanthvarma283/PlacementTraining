package threads;
class UsingInterface implements Runnable{
    String arr[]={"Hello"," world"};
    int count=0;
    public synchronized void run(){
        System.out.print(arr[count]);
        count++;
    }
}

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        UsingInterface obj=new UsingInterface();
        Thread obj1=new Thread(obj);
        Thread obj2=new Thread(obj);
        obj1.start();
        Thread.sleep(1000);
        obj2.start();
    }
}
