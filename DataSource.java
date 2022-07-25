<<<<<<< HEAD
import java.sql.*;
import java.io.*;

public class DataSource{
    static Connection c = null;

    public DataSource(){
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Console cl = System.console();
         String user = 'jnevins';
         String pass = new String(cl.readPassword("Enter password for jririe@itec3: "));

         try {
			c = DriverManager.getConnection("jdbc:oracle:thin:@Worf.radford.edu:1521:itec3", user, pass);
		} catch (SQLException e) {
			System.out.println("Could not load the db " + e);
		}
    }
}
=======
This is Jimmy's edit...
>>>>>>> 49aa81ce104f03b4377674c3d7495eba95d41081
