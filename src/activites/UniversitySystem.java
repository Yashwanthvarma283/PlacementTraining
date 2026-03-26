package activites;

import java.util.Random;

abstract class Person{
    String name;
    int age;
    String type;
    abstract void displayDetails();
}
interface UniversityMember{
    void getMemberType();
    Random gen=new Random();
}
class Student extends Person implements UniversityMember{
    int rollNumber;
    String course;
    Student(String name,int age,String course){
        this.name=name;
        this.course=course;
        this.age=age;
        rollNumber=gen.nextInt(1,999);
        this.type="Student";
    }
    void displayDetails(){
        System.out.println("Member Type : "+type);
        System.out.println("Name of Student : "+name);
        System.out.println("RollNumber of Student :"+rollNumber);
        System.out.println("Age of Student is :"+age);
        System.out.println("Course of Student is :"+course);

    }
    public void getMemberType(){
        System.out.println("Member Type : "+type);
    }
}
class Faculty extends Person implements UniversityMember{
    int facultyId;
    String department;
    Faculty(String name,int age,String department){
        this.department=department;
        this.name=name;
        this.age=age;
        this.facultyId= gen.nextInt(1,999);
        this.type="Faculty";
    }
    void displayDetails(){
        System.out.println("Member Type : "+type);
        System.out.println("Name of Faculty : "+name);
        System.out.println("Faculty Id :"+facultyId);
        System.out.println("Age of Faculty is :"+age);
        System.out.println("Department of Faculty is :"+department);
    }
    public void getMemberType(){
        System.out.println("Member Type : "+type);
    }
}


public class UniversitySystem {
    public static void main(String[] args){
        Student obj=new Student("Yashwanth",20,"CSE");
        Faculty obj2=new Faculty("Rahul",35,"Mechanical");
        obj.displayDetails();
        obj2.displayDetails();
    }
}
