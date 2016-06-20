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
				if (user[i] == null)
				{
					System.out.println("Connection from: " + socket.getInetAddress());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					DataInputStream in = new DataInputStream (socket.getInputStream());
								
					user[i] = new Users(out,in,user,i);
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
	int playerId;
	int playerIdIn;
	int xIn;
	int yIn;
	int qtdPlayers;
	
	public Users(DataOutputStream out, DataInputStream in, Users[] user,int pid)
	{
		this.out = out;
		this.in = in;	
		this.user = user;
		this.playerId = pid;
	}
	
	@Override
	public void run() 
	{	
		int i = 0, aux = 0;
		
		try
		{
			out.writeInt(playerId);
		}
		catch(IOException e)
		{
			System.out.println("Failed to send PlayerID");	
		}
		
		while (true)
		{	
			try
			{
				playerIdIn = in.readInt();
				/*xIn = in.readInt();
				yIn = in.readInt();*/
				
				int xCompare = in.readInt();
				int yCompare = in.readInt();
				
				for (i = 0 ; i < 10 ; i++)
				{
					if (user[i] != null && i != playerIdIn)
					{
						//VERIFICA SE OS PLAYERS IRÃO ESTAR NO MESMO SQM
						aux++;
						if (xCompare != user[i].xIn || yCompare != user[i].yIn)
						{
							xIn = xCompare;
							yIn = yCompare;
						}	
					}
				}
				
				if (aux == 0)
				{
					xIn = xCompare;
					yIn = yCompare;
				}
								
				for (i = 0 ; i < 10 ; i++)
				{
					if (user[i] != null)
					{
						user[i].out.writeInt(playerIdIn);
						user[i].out.writeInt(xIn);
						user[i].out.writeInt(yIn);
						user[i].out.writeInt(aux + 1);
					}
				}
			}
			catch(IOException e)
			{
				user[playerId] = null;
				break;
			}
			
		}
	}
	
}