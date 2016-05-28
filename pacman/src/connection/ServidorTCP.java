package connection;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import connection.Client;

public class ServidorTCP 
{

	private static int port=4444;
	private static int maxConnections=2;
	  // Listen for incoming connections and handle them
	public static void main(String[] args) 
	{
		int i=0;

	    try
	    {
	    	ServerSocket server = new ServerSocket(port);
	    	System.out.println(port + " Opened");
    		Socket cliente;
    		String ip = server.getInetAddress().getHostAddress().toString();
    		
	    	while((i++ < maxConnections) || (maxConnections == 0))
	    	{
	    		Client connection= new Client();
	    		cliente = server.accept();
	    		Thread t = new Thread(connection);
	    		t.start();
	    	}
	    } 
	    catch (IOException ioe) 
	    {
	    	System.out.println("IOException on socket listen: " + ioe);
	    	ioe.printStackTrace();
	    }
	}

}