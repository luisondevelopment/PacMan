package last.connenction;

import java.net.*;
import java.io.*;

public class Server 
{
	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;
	static Users[] user = new Users [10];
	
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Starting server...");
		serverSocket = new ServerSocket(4444);
		System.out.println("Server started...");
		
		while (true)
		{
			socket = serverSocket.accept();	
			for (int i = 0 ; i < 10 ; i ++)
			{
				System.out.println("Connection from: " + socket.getInetAddress());
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream (socket.getInputStream());
				
				if (user[i] == null)
				{
					user[i] = new Users(out,in,user);
					Thread thread = new Thread(user[i]);
					thread.start();
					break;
				}
				
			}
		}
	}
}


class Users implements Runnable
{
	DataOutputStream out;
	DataInputStream in;
	Users[] user = new Users[10];
	String name;
	
	public Users(DataOutputStream out, DataInputStream in, Users[] user)
	{
		this.out = out;
		this.in = in;	
		this.user = user;
	}
	
	@Override
	public void run() 
	{	
		try
		{
			name = in.readUTF();
		}
		catch(IOException e)
		{
			
		}
		
		while (true)
		{	
			try
			{
				String message = in.readUTF();
				
				for (int i = 0 ; i < 10 ; i++)
				{
					if (user[i] != null)
					{
						user[i].out.writeUTF(name + ": " + message);
					}
				}
			}
			catch(IOException e)
			{
				this.out = null;
				this.in = null;
				break;
			}
			
		}
	}
	
}