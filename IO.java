//IO class for the project
import java.io.*;

public class IO {

    Console cl = System.console();

    public String getUserCredentials() {
        // one-time method
        // passes creds back up to the Ctrl class...
        // ...which uses those creds to instantiate a database connection
        // todo: better collection of username
        print("Welcome to the J&J database interface!");
        String user = new String(cl.readLine("Enter username: "));
        String pass = new String(cl.readPassword("Enter password for " + user + "@itec3: "));
        return user + "," + pass;
    }

    public String prompt() {
        // called by the Ctrl class
        // prompts the user for input

        // then has to return this input to the Ctrl class

        // must give the user the option to quit or create an invoice
        // after invoice creation, return to this method
        // (if you quit, make sure to .close the DataSource)

        // 
        // Unrecognized input! Please type either "q" or "i"

        String response = new String(cl.readLine("Would you like to quit (q) or insert an invoice (i)?"));
        if (response.equals("q")){
            //pass it up the chain so Ctrl can quit the program
            return response;
        } else if (response.equals("i")){
            //now prompt for invoice information before passing it up to Ctrl
            return response;
        } else {
            print("Unrecognized input! Please type either \"q\" or \"i\"");
            prompt();
        }
        print("If you're seeing this, then you need to check IO.prompt()");
        return "Just putting this here to skirt around compilation issues";

    }

    public void print(String s) {
        // output method
        // will be called by the Ctrl class to pass info to the user
        System.out.println(s);
    }
}