import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class DataSource{
    Connection c = null;

    public DataSource(String user, String pass){
        
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            user = "jririe";
            pass = "mHtq5bXWy4_UGC4";
			c = DriverManager.getConnection("jdbc:oracle:thin:@Worf.radford.edu:1521:itec3", user, pass);
            System.out.println("Connection created successfully!");
		} catch (Exception e) {
			System.out.println("Could not load the database: " + e);
		}
    }


private String insertInvoice(String query) {
    String dString = "21-07-2052 13:20:21";
    SimpleDateFormat f = new SimpleDateFormat("dd - MM - yyyy HH:mm:ss" );
    Date date = f.parse(dString);
    try{
        CallableStatement insertDog = c.prepareCall("{call insertDog(?,?,?,?,?)}");
        insertDog.setInt(1, 122);
        insertDog.setString(2, "Astroid" );
        insertDog.setInt(3, 301);
        //insertDog.setDate("aDOB", "21-JUL-2052");
        insertDog.setString(5, "Rottweiler");
        insertDog.execute();
    } catch (SQLException x1){
        System.out.println("Failed to insert Dog" + x1);
    }

    try{
        PreparedStatement insertInvoice = c.prepareStatement(query);
        ResultSet rSet = insertInvoice.executeQuery();
    } catch (SQLException x2){
        System.out.println("Failed to insert Invoice" + x2);
    }

    return "End of method";
}

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