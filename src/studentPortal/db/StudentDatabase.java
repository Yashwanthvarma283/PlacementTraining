package studentPortal.db;

import java.util.*;
import studentPortal.models.*;

public class StudentDatabase {

    HashMap<Integer, Student> students=new HashMap<>();

    public boolean save(Student student){
        int id=student.getId();
        if(students.get(id)!=null){
            return false;
        }
        students.put(student.getId(),student);
        return true;
    }
    public void delete(int id){
        students.remove(id);
    }

}