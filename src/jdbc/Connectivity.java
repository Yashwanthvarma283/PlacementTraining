package jdbc;

import java.sql.*;

public class Connectivity {
    public static void main(String[] args){
        String url="jdbc:postgresql://localhost:5432/placement";
        String user="postgres";
        String password="2820";

        String name="Venu";
        int id=156;
        String dept="CSE";
        double cgpa=9.00;

//        String query="select * from students order by id asc";
        String query="insert into students values (?,?,?,?)";
        try{
            Connection connection=DriverManager.getConnection(url,user,password);
            PreparedStatement st =connection.prepareStatement(query);
//            Statement st=connection.createStatement();
            st.setInt(1,id);
            st.setString(2,name);
            st.setString(3,dept);
            st.setDouble(4,cgpa);
            boolean set=st.execute();
            System.out.println(set);

//            while(set.next()){
//                System.out.print(set.getInt(1)+" - ");
//                System.out.print(set.getString(2)+" - ");
//                System.out.print(set.getString("dept")+" - ");
//                System.out.println(set.getString(4));
//            }
            connection.close();


//            set.next();
//            String name=set.getString("name");
//            System.out.println(name);
//            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
