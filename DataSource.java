
//last assignment, we're better than this...
//I stg I haven't come so far to give up here
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.sql.Date;

public class DataSource {
    Connection c = null;

    public DataSource(String user, String pass) throws SQLException {
        // this needs to throw an error but I'm not sure what
        // for some reason registering the driver doesn't work with VSCode's default
        // execution parameters
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        /*
         * user = "jririe";
         * pass = "mHtq5bXWy4_UGC4";
         */
        c = DriverManager.getConnection("jdbc:oracle:thin:@Worf.radford.edu:1521:itec3", user, pass);
    }

    public String insertInvoice(String query) {
        // this method is called by the Ctrl class
        // it will return the string to the Ctrl class
        // which will pass it to an output method in the IO class
        // return string must identify *which* invoice was created
        // or precisely why the invoice was not created

        // this needs to use a prepared statement for the invoice
        // use a callable statement to insert a dog...
        // not required to test (or handle) the case of a dog already existing

        // make sure we have an owner record in the db and that the dog and invoice are
        // removed each time

        // please also remember that we're working with transactions,
        // so disable autocommit!

        String[] args = query.split(",");
        //try splitting the string

        //needed to leave these outside the try/catch
        String dog_ID_s;
        String dog_name;
        String owner_ID_s;
        String dog_DOB_s;
        String breed;
        String invoice_no_s;
        String invoice_date_s;
        String amount_s;

        try{
            dog_ID_s = args[0];
            dog_name = args[1];
            owner_ID_s = args[2];
            dog_DOB_s = args[3];
            breed = args[4];
            invoice_no_s = args[5];
            invoice_date_s = args[6];
            amount_s = args[7];
        } catch (ArrayIndexOutOfBoundsException e){
            return "One or more of your entries were blank! Please try again.";
        }

        //parse the strings as needed
        try {
            java.util.Date dog_dob = stringToSQLDate(dog_DOB_s);
            java.util.Date invoice_date = stringToSQLDate(invoice_date_s);

            double amount = Double.parseDouble(amount_s);

            int dog_ID = Integer.parseInt(dog_ID_s);
            int owner_ID = Integer.parseInt(owner_ID_s);
        } catch (ParseException e) {
            return "One or more of your entries were improperly formatted!\nPlease try again.\nError code: " + e;
            //todo: if time permits, split this for better error handling. Tell the user *where* the parse failed
        }
        

        //String dString = "21-07-2052";
        //Date date = Date.valueOf(dString);

        try {
            CallableStatement insertDog = c.prepareCall("{call insertDog(?,?,?,?,?)}");
            insertDog.setInt(1, 122);
            insertDog.setString(2, "Astroid");
            insertDog.setInt(3, 301);
            insertDog.setDate("aDOB", date);
            insertDog.setString(5, "Rottweiler");
            insertDog.execute();
        } catch (SQLException e) {
            // System.out.println("Failed to insert Dog" + x1);
            return "Problem inserting the dog record into the database!\nHere's the error code: " + e;
        }

        try {
            PreparedStatement insertInvoice = c.prepareStatement(query);
            ResultSet rSet = insertInvoice.executeQuery();
        } catch (SQLException e) {
            // System.out.println("Failed to insert Invoice" + x2);
            return "Problem inserting the invoice record into the database!\nHere's the error code: " + e;
        }

        // return "Invoice " + invoiceNo + " successfully created.";
        return "You forgot to complete this method, dumbass!";
    }

    public void close() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            System.out.println("Failed to close the connection!\n" + e);
        }
    }

    private java.sql.Date stringToSQLDate(String s) throws ParseException {
        //takes a string date in ISO 8601 and returns a java.sql.Date for it
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date d = sdf.parse(s);
        return new java.sql.Date(d.getTime());
    }
}