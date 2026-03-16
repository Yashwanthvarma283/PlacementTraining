package practice;

import java.util.Scanner;
public class Main
{
    static int tickets()
    {
        String stations [] ={"MGR Central","Egmore","Nehru Park",
                "Kilpauk Medical College","Pachaiyappa College","Shenoy Nagar",
                "Anna Nagar East","Anna Nagar Tower","Thirumangalam","Koyambedu",
                "CMBT","Arumbakkam","Vadapalani","Ashok Nagar","Ekkattuthangal",
                "Arignar Anna Alandur","St Thomas Mount"} ;
        Scanner get_data = new Scanner(System.in);
        System.out.print("Enter your Name: ");
        String name=get_data.nextLine();
        System.out.print("Enter your Starting point: ");
        String Start=get_data.nextLine();
        System.out.print("Enter your Stopping point: ");
        String Stop=get_data.nextLine();
        int start_point=0,stop_point=0;
        for (int i=0;i<stations.length;i++)
        {
            if(stations[i].equals(Start))
            {
                start_point=i;
            }
            if(stations[i].equals(Stop))
            {
                stop_point=i;
            }
        }
        return stop_point-start_point+1;
    }
    public static void main(String args[])
    {
        int fair=0;
        int stations=tickets();
        if(stations<=2) fair=10;
        else if(stations>2 && stations<=4) fair=20;
        else if(stations>4 && stations<=7) fair=30;
        else fair=40;
        System.out.println("The total fair is : "+fair);
    }
}