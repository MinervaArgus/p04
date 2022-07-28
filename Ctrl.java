public class Ctrl{
/*
    public Ctrl(){
        //don't think we need anything here... but I need to test to make sure
    }
*/
    private IO io;
    private DataSource d;

    public void start(){
        //start the program
        io = new IO();

        //start by creating a DB connection with DataSource
        String rawcreds = io.getUserCredentials();
        String[] creds = rawcreds.split("$")
        try{
            d = new DataSource(creds[0], creds[1]);
            io.print("Connection created to database!");
        }catch(Exception e){
            io.print("Could not load the database!\nPlease check your credentials and try again.\nError code: " + e);
        }

        //move to a different method to handle recursion
        proceed();
    }

    private void proceed(){
        String s = io.prompt();
        //prompt should return either a formatted string to insert the statement
        //or a q to signal that we should close the connection
        if(s.equals("q")){
            //quit
            io.print("Exiting...");
            d.close();
            System.exit(0);
        } else {
            //insert a statement
            String message = d.insertInvoice(s);
            io.print(message);
            //end with recursion
            proceed();
        }
        
    }
}
//I know I'm breaking incremental development
//but I'm the one to pay the consequences...