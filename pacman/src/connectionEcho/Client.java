package connectionEcho;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
	 public static void main(String[] args) throws IOException 
	 {
		 Socket clientSocket = null;
		 DataInputStream input = null;
		 PrintStream printStream = null;
		 DataInputStream inputLine = null;
		 
		 clientSocket = new Socket ("192.168.0.102", 2222); 
		 printStream = new PrintStream(clientSocket.getOutputStream());
		 input = new DataInputStream(clientSocket.getInputStream());
		 inputLine = new DataInputStream (new BufferedInputStream(System.in));
		 
		 
		 if (clientSocket != null && printStream != null && input !=null)
		 {
			 System.out.println("The client started. Type and text. To quit, tupe 'Ok'.");
			 String responseLine;
			 printStream.println(inputLine.readLine());
			 
			 while ((responseLine = input.readLine()) != null)
			 {
				 System.out.println(responseLine);
				 if (responseLine.indexOf("Ok") != -1)
				 {
					 break;
				 }
				 printStream.println(inputLine.readLine());
			 }
			 printStream.close();
			 clientSocket.close();
			 inputLine.close();
		 }
	 }
}
