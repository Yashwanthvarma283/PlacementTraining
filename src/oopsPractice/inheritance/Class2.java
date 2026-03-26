package oopsPractice.inheritance;

abstract class Class1{
    abstract void print();
}

class Class3 extends Class1{
    void print(){
        System.out.println("Hey, Hello from Class3");
    }
}

public class Class2 {
    public static void main(String [] args){
        Class1 obj2=new Class3();
        Class3 obj3= new Class3();
        obj2.print();
        obj3.print();
    }
}