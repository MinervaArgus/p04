//IO class for the project
import java.io.*;

public class IO{

    public IO(){
        //System.out.println("Testing!");
        Console cl = System.console();
        //todo: these prompts are only for testing, need to be moved to the IO class
        String user = new String(cl.readPassword("Enter username: "));
        String pass = new String(cl.readPassword("Enter password for" + user +"@itec3: "));

        DataSource d = new DataSource(user, pass);
    }
}