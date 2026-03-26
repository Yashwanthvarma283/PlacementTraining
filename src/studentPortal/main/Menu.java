package studentPortal.main;

import java.util.Scanner;

public class Menu {
    public static char menu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("========== Student Portal ==========");
        System.out.println();
            System.out.println("Add a New Student [A] : ");
            System.out.println("Delete a Student [D] : ");
            System.out.println("View Student Details [V] : ");
            System.out.println("Update Student Details [U] : ");
            System.out.println("Display All the Students [S] : ");
            System.out.println("Exit [x] : ");
        char input=sc.next().charAt(0);
        input=Character.toUpperCase(input);
            return input;
    }
}
