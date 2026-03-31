package threads;
class Threading implements Runnable{
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(i);
        }
    }
}

public class Practicing {
    public static void main(String[] args){
    Thread obj1=new Thread(new Threading());
    obj1.start();
    }
}
