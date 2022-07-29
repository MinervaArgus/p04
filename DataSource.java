
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
        c = DriverManager.getConnection("jdbc:oracle:thin:@Worf.radford.edu:1521:itec3", user, pass);
        // what the fuck was I thinking publishing my creds like that... I hope Jackson
        // keeps the repo private
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

        //HELLO DR. PITTGES
        //Just wanted to say that I'm aware of the relative lack of input validation here
        //I'm not sure how much of it ought to be handled by the java program and how much by constraints

        // when testing make sure there's an owner record in the db we can use

        String[] args = query.split(",");
        // try splitting the string

        // needed to leave these outside the try/catch
        String dog_ID_s;
        String dog_name;
        String owner_ID_s;
        String dog_DOB_s;
        String breed;
        String invoice_no_s;
        String invoice_date_s;
        String amount_s;

        try {
            dog_ID_s = args[0];
            dog_name = args[1];
            owner_ID_s = args[2];
            dog_DOB_s = args[3];
            breed = args[4];
            invoice_no_s = args[5];
            invoice_date_s = args[6];
            amount_s = args[7];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "One or more of your entries were blank! Please try again.";
        }

        java.sql.Date dog_dob;
        // java.sql.Date invoice_date;
        //double amount; ended up using string. Don't parse what you don't have to!
        int dog_ID;
        int owner_ID;
        // parse the strings as needed
        
        try {
            dog_dob = stringToSQLDate(dog_DOB_s);
            //invoice_date = stringToSQLDate(invoice_date_s);
            //amount = Double.parseDouble(amount_s);
        } catch (ParseException e) {
            return "Invalid Format for DogDOB!\nPlease try again.\nError code: " + e;
            // todo: if time permits, split this for better error handling. Tell the user
            // *where* the parse failed
        }

        try {
            dog_ID = Integer.parseInt(dog_ID_s);
        } catch (Exception e){
            return "Invalid Format for DogID!\nPlease try again.\nError code: " + e;
        }

        try {
            dog_ID = Integer.parseInt(owner_ID_s);
        } catch (Exception e){
            return "Invalid Format for OwnerID!\nPlease try again.\nError code: " + e;
        }

        try {
            c.setAutoCommit(false);
            CallableStatement insertDog = c.prepareCall("{call insertDog(?,?,?,?,?)}");
            insertDog.setInt(1, dog_ID);
            insertDog.setString(2, dog_name);
            insertDog.setInt(3, owner_ID);
            insertDog.setDate(4, dog_dob);
            insertDog.setString(5, breed);
            insertDog.execute();
            c.commit();
        } catch (SQLException e1) {
            try {
                c.rollback();
                return "Problem inserting the dog record into the database!\nHere's the error code: " + e1;
            } catch (SQLException e2) {
                return "Failed to roll back the dog transaction! Please contact a database admin!\nError code: " + e2;
            }
        }

        String pquery = "Failed to initialize query!";// this shows if the code somehow fails...
        try {
            pquery = "INSERT INTO Invoices (invoice_no, owner_id, dog_id, invoice_date, amount) VALUES ('"
                    + invoice_no_s + "', '" + owner_ID_s + "', '" + dog_ID_s + "', TO_DATE('" + invoice_date_s
                    + "', 'YYYY-MM-DD'), " + amount_s + ")";
            PreparedStatement insertInvoice = c.prepareStatement(pquery);
            ResultSet rSet = insertInvoice.executeQuery();
            c.commit();
        } catch (SQLException e1) {
            try {
                c.rollback();
                return "Problem inserting the invoice record into the database!\nHere's the error code: " + e1
                        + "\n\nPlease also review the query to look for problems: " + pquery;
            } catch (SQLException e2) {
                return "Failed to roll back the invoice transaction! Please contact a database admin!\nError code: "
                        + e2;
            }
        }

        return "Successfully created record for dog with ID number" + dog_ID + "\nSuccessfully created invoice number"
                + invoice_no_s;
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
        // takes a string date in ISO 8601 and returns a java.sql.Date for it
        // I wrote this method when I thought I'd need to convert to Unix millis and
        // back...

        java.sql.Date d = Date.valueOf(s);
        // System.out.println("Testing date function: " + d.toString());
        return new java.sql.Date(d.getTime());
    }
}