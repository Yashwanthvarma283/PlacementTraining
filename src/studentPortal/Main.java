package studentPortal;

import studentPortal.main.Menu;
import studentPortal.services.DeleteStudentsService;
import studentPortal.services.StudentService;

public class Main {

    public static void main(String[] args) {
        StudentService service= new StudentService();
        DeleteStudentsService deleteStudents=new DeleteStudentsService();
        char action;
        while(true){
            action= Menu.menu();
            action=Character.toUpperCase(action);

            switch(action){
                case 'A':
                    service.addStudent();
                    break;
                case 'D':
                    deleteStudents.deleteStudent();
                    break;
                case 'V':
                    service.viewStudent();
                    break;
                case 'S':
                    service.allStudents();
                    break;
                case 'U':
                    service.updateStudent();
                    break;
                case 'X':
                    System.out.println("============= Existed =============");
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
