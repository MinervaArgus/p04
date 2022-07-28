//IO class for the project
import java.io.*;

public class IO{

    //todo: delete the constructor and see if this still works
    public IO(){
    }

    public String getUserCredentials(){
        //one-time method
        //passes creds back up to the Ctrl class...
        //...which uses those creds to instantiate a database connection

        Console cl = System.console();
        //todo: better collection of username
        String user = new String(cl.readPassword("Enter username: "));
        String pass = new String(cl.readPassword("Enter password for " + user +"@itec3: "));
        return user + "$" + pass;
    }

    public String prompt(){
        //called by the Ctrl class
        //prompts the user for input

        //then has to return this input to the Ctrl class

        //must give the user the option to quit or create an invoice
        //after invoice creation, return to this method
        //(if you quit, make sure to .close the DataSource)

        //Would you like to quit (q) or insert an invoice (i)?
        //Unrecognized input! Please type either "q" or "i"


        return null;
    }

    public void print(String s){
        //output method
        //will be called by the Ctrl class to pass info to the user
        System.out.println(s);
    }
}