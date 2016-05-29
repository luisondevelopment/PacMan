package last.connenction;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client 
{
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
		
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Connencting...");
		socket = new Socket("localhost", 4444);
		System.out.println("Connection succesful...");
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		Input input = new Input(in);
		Thread thread = new Thread(input);
		thread.start();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name: ");
		String name = sc.nextLine();
		out.writeUTF(name);
		
		while(true)
		{
			String sendMessage = sc.nextLine();
			out.writeUTF(sendMessage);
		}
	}

}

class Input implements Runnable
{
	DataInputStream in;
	
	public Input(DataInputStream in)
	{	
		this.in = in;
	}
	@Override
	public void run() 
	{
		while(true)
		{
			String message;
			try 
			{
				message = in.readUTF();
				System.out.println(message);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}	
	}
	
}