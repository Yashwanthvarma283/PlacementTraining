package oopsPractice.Abstraction;


abstract class Class1{
    void hello(){
    }
    abstract void hey();
}
class Class2 extends Class1{
    void hello(){
        System.out.println("Hello from the class 2");
    }
    @Override
    void hey() {

    }
}

public class FirstClass{
    public static void main(String [] args){
        Class2 obj2=new Class2();
        Class1 obj3=new Class2();
    }
}
