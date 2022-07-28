//last assignment, we're better than this...
//I stg I haven't come so far to give up here
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.sql.Date;
public class DataSource{
    Connection c = null;

    public DataSource(String user, String pass){
        //this needs to throw an error but I'm not sure what
        //for some reason registering the driver doesn't work with VSCode's default execution parameters
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        /*
        user = "jririe";
        pass = "mHtq5bXWy4_UGC4";
        */
        c = DriverManager.getConnection("jdbc:oracle:thin:@Worf.radford.edu:1521:itec3", user, pass);	
    }


    public String insertInvoice(String query) {
        //this method is called by the Ctrl class
        //it will return the string to the Ctrl class
        //which will pass it to an output method in the IO class
        //return string must identify *which* invoice was created
        //or precisely why the invoice was not created

        //this needs to use a prepared statement for the invoice
        //use a callable statement to insert a dog...
        //not required to test (or handle) the case of a dog already existing

        //make sure we have an owner record in the db and that the dog and invoice are removed each time

        //please also remember that we're working with transactions,
        //so disable autocommit!
        String dString = "21-07-2052";
        Date date = Date.valueOf(dString);

        String rs;
        try{
            CallableStatement insertDog = c.prepareCall("{call insertDog(?,?,?,?,?)}");
            insertDog.setInt(1, 122);
            insertDog.setString(2, "Astroid" );
            insertDog.setInt(3, 301);
            insertDog.setDate("aDOB", date);
            insertDog.setString(5, "Rottweiler");
            insertDog.execute();
        } catch (SQLException e){
            //System.out.println("Failed to insert Dog" + x1);
            rs = "Problem inserting the dog record into the database!\nHere's the error code: " + e;
            return rs;
        }

        try{
            PreparedStatement insertInvoice = c.prepareStatement(query);
            ResultSet rSet = insertInvoice.executeQuery();
        } catch (SQLException e){
            //System.out.println("Failed to insert Invoice" + x2);
            rs = "Problem inserting the invoice record into the database!\nHere's the error code: " + e;
            return rs;
        }

        //return "Invoice " + invoiceNo + " successfully created.";
        return "You forgot to complete this method, dumbass!";
    }

    public void close(){
        try{
            if (c != null){
                c.close();
            }
        }
        catch (Exception e){
            System.out.println("Failed to close the connection!\n" + e);
        }
    }
}