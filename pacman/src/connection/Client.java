package connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable 
{
	private Socket cliente;
	private String line,input;

    Client(int port) throws UnknownHostException, IOException 
    {
    	//this.cliente = cliente;
    	Socket cliente = new Socket("192.168.0.101",port);
	}

    @SuppressWarnings("deprecation")
	public void run () 
    {
    	input="";

    	try 
    	{
    		// Get input from the client
	        DataInputStream in = new DataInputStream (cliente.getInputStream());
	        PrintStream out = new PrintStream(cliente.getOutputStream());

	        while((line = in.readLine()) != null && !line.equals(".")) 
	        {
	          input=input + line;
	          out.println("I got:" + line);
	        }

	        // Now write to the client

	        System.out.println("Overall message is:" + input);
	        out.println("Overall message is:" + input);

	        cliente.close();
	    } 
    	catch (IOException ioe) 
    	{
	        System.out.println("IOException on socket listen: " + ioe);
	        ioe.printStackTrace();
	     }
    }
}