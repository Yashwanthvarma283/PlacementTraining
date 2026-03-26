package studentPortal.services;
import studentPortal.db.*;
import studentPortal.models.*;

import java.util.Scanner;

public class StudentService {
    StudentDatabase db=new StudentDatabase();

    Scanner sc=new Scanner(System.in);
    public void addStudent(){
        while(true){
            System.out.print("Enter the Student name : ");
            String name=sc.nextLine();
            System.out.print("Enter the Id Number of Student : ");
            int id=sc.nextInt();
            System.out.print("Enter the Age of Student : ");
            int age=sc.nextInt();
            System.out.print("Enter the Department of Student : ");
            String dept=sc.next();

            Student student=new Student(id,name,age,dept);
            boolean status=db.save(student);
            if(status){

                System.out.println();
                System.out.println("============= Student Added Successfully =============");
                System.out.println();
                char st;
                while(true){
                    System.out.print("Add Another Student : [Y] ");
                    System.out.println("Back to Main Menu : [M] ");
                    st=sc.next().charAt(0);
                    sc.nextLine();
                    st=Character.toUpperCase(st);
                    if(st=='M' || st=='Y'){
                        break;
                    }
                    else{
                        System.out.println("Invalid Input!!!");
                    }
                }

                if(st=='M') return;

            }
            else{
                System.out.println();
                System.out.println("============= Student Already Exists =============");
                System.out.println();
                char st;
                while(true){
                    System.out.print("Try Again : [Y] ");
                    System.out.println("Back to Main Menu : [M] ");
                    st=sc.next().charAt(0);
                    sc.nextLine();
                    st=Character.toUpperCase(st);
                    if(st=='M' || st=='Y'){
                        break;
                    }
                    else{
                        System.out.println("Invalid Input!!!");
                    }
                }

                if(st=='M') return;
            }
        }
    }

    public void updateStudent(){

    }
    public void viewStudent(){

    }
    public void allStudents(){

    }

}
