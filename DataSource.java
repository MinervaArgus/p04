import java.sql.*;
import java.io.*;

public class DataSource{
    Connection c = null;

    public DataSource(){
        DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
        Console cl = System.console();
         String user = "jnevins";
         String pass = new String(cl.readPassword("Enter password for" + user +"@itec3: "));

         try {
			c = DriverManager.getConnection("jdbc:oracle:thin:@Worf.radford.edu:1521:itec3", user, pass);
		} catch (SQLException x) {
			System.out.println("Could not load the database" + x);
		}
    }


// private String insertInvoice(String query) {
//     CallableStatement insertDog = c.prepareCall("{call insertDog(?,?,?,?,?)}");
//     insertDog.setInt(1, 122);
//     insertDog.setString("aname", "Astroid" );

//     return "End of method";
// }

private void close(){
    try{
        if (c != null){
            c.close();
        }
    }
    catch (Exception x){
        System.out.println("Failed to close the connection!" + x);
    }
}
}