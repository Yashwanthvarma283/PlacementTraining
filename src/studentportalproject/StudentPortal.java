package studentportalproject;

import java.util.Scanner;

public class StudentPortal {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the Number of Students: ");
        int n=sc.nextInt();
        sc.nextLine();
        String names[]=new String[n];
        int maths[]=new int[n];
        int chem[]=new int[n];
        int phy[]=new int[n];
        int math=0,p=0,c=0;
        int hm=0,hp=0,hc=0;
        for(int i=0;i<n;i++){
            System.out.print("Enter the name of the student: ");
            names[i]=sc.nextLine();
            System.out.print("Enter the marks of "+names[i]+" in maths: ");
            maths[i]=sc.nextInt();
            System.out.print("Enter the marks of "+names[i]+" in physics: ");
            phy[i]=sc.nextInt();
            System.out.print("Enter the marks of "+names[i]+" in chemistry: ");
            chem[i]=sc.nextInt();
            math+=maths[i];
            p+=phy[i];
            c+=chem[i];
            if(maths[i]>maths[hm]) hm=i;
            if(phy[i]>phy[hp]) hp=i;
            if(chem[i]>chem[hc]) hc=i;
            sc.nextLine();
        }
        for(int i=0;i<n;i++){
            System.out.println("Mark Sheet of "+names[i]);
            System.out.println("Maths: "+maths[i]);
            System.out.println("Physics: "+phy[i]);
            System.out.println("Chemistry: "+chem[i]);
            int total=maths[i]+phy[i]+chem[i];
            System.out.println("Total Marks of "+names[i]+" is "+total);
        }
        System.out.println("Average Marks in Maths: "+math);
        System.out.println("Average Marks in Physics: "+p);
        System.out.println("Average Marks in Chemistry: "+c);
        System.out.print("The highest Marks in Maths is scored by: ");
        for(int i=0;i<n;i++){
            if(maths[i]==maths[hm]){
                System.out.print(names[i]+" ");
            }
        }
        System.out.println();
        System.out.print("The highest Marks in Physics is scored by: ");
        for(int i=0;i<n;i++){
            if(phy[i]==phy[hp]){
                System.out.print(names[i]+" ");
            }
        }
        System.out.println();
        System.out.print("The highest Marks in Chemistry is scored by: ");
        for(int i=0;i<n;i++){
            if(chem[i]==chem[hc]){
                System.out.print(names[i]+" ");
            }
        }
    }
}
