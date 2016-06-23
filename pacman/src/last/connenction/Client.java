package last.connenction;

import java.net.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client extends JPanel implements Runnable, KeyListener {
	
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	int playerid;
	int[] x = new int[10];
	int[] y = new int[10];
	boolean left, down, right, up;
	int playerx;
	int playery;
	int qtdPlayers;
	int screenHeight = 500;
	int screenWidth = 500;
	int sqm = 20;
	public short screenData[][] = { //		  5             10             15             20 
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,1 ,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,2,0,2,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,2,0,2,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						 /*5*/	{10,10,2,0,2,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,2,0,2,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,2,0,2,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,1,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						/*10*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						/*15*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						/*20*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						/*25*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						    	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						/*30*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
						/*35*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
					   /*40*/	{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								 };

	public void init() 
	{
		try 
		{
			System.out.println("Connencting...");
			socket = new Socket("localhost", 4444);
			System.out.println("Connection successful...");
			in = new DataInputStream(socket.getInputStream());
			playerid = in.readInt();
			out = new DataOutputStream(socket.getOutputStream());

			Input input = new Input(in, this);
			Thread thread = new Thread(input);
			thread.start();
			Thread thread2 = new Thread(this);
			thread2.start();
		} 
		catch (Exception e) 
		{
			System.out.println("Unable to start client");
		}
	}

	public void updateCoordinates(int pid, int x2, int y2, int qtdPlayers) 
	{
		this.x[pid] = x2 * sqm;
		this.y[pid] = y2 * sqm;
		this.qtdPlayers = qtdPlayers;
	}

	public void paint(Graphics g) 
	{
		super.paint(g);
		int x1,x2,y1,y2;
		for (int i = 0; i < 10; i++) 
		{
			g.drawOval(x[i], y[i], sqm, sqm);
		}
		
		for(int i = 0 ; i < 39 ; i++)
		{
			for (int j = 0; j < 19 ; j++)
			{
				if(screenData[i + 1][j + 1] == 10)
				{
					int offsetX = (i * sqm) + 8;
					int offsetY = (j * sqm) + 8;
					g.drawOval(offsetX, offsetY, 5, 5);
				}
				if(screenData[i + 1][j + 1] == 2)
				{
					int x = (i * sqm);
					int y = (j * sqm) + 10;
					g.drawLine(x, y, x+20, y);
				}
				if(screenData[i + 1][j + 1] == 1)
				{
					int x = (i * sqm) + 10;
					int y = (j * sqm);
					g.drawLine(x, y, x, y+20);
				}
			}
		}
		
		g.drawString(Integer.toString(playerx) + Integer.toString(playery),15,15);
		repaint();
	}

	@Override
	public void run() 
	{
		while (true) 
		{	//REGRAS DE MOVIMENTAÇÃO
			if (right == true && (playerx * sqm) < screenWidth) 
			{
				if (screenData[playerx + 1][playery] == 10)
				{
					playerx += 1;
				}
			}
			if (left == true && (playerx * sqm) > (screenWidth * (-1)) && playerx > 0) 
			{
				if (screenData[playerx - 1][playery] == 10)
				{
					playerx -= 1;
				}
			}
			if (down == true && (playery * sqm)< screenHeight) 
			{
				if (screenData[playerx][playery + 1] == 10)
				{
					playery += 1;
				}
			}
			if (up == true && (playery * sqm) > 0) 
			{
				if (screenData[playerx][playery - 1] == 10)
				{
					playery -= 1;
				}
			}
			if (right || left || up || down) 
			{
				try 
				{
					out.writeInt(playerid);
					out.writeInt(playerx);
					out.writeInt(playery);
				} 
				catch (Exception e) 
				{
					System.out.println("Error sending the Coordinates");
				}
			}

			repaint();

			try 
			{
				Thread.sleep(90);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == 37) {
			left = true;
		}
		if (e.getKeyCode() == 38) {
			up = true;
		}
		if (e.getKeyCode() == 39) {
			right = true;
		}
		if (e.getKeyCode() == 40) {
			down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			left = false;
		}
		if (e.getKeyCode() == 38) {
			up = false;
		}
		if (e.getKeyCode() == 39) {
			right = false;
		}
		if (e.getKeyCode() == 40) {
			down = false;
		}
	}

}

//CRIAR THREAD PARA SABER A QTD DE PLAYERS

class Input implements Runnable 
{
	DataInputStream in;
	Client client;

	public Input(DataInputStream in, Client c) 
	{
		this.in = in;
		this.client = c;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			try 
			{
				int playerid = in.readInt();
				int x = in.readInt();
				int y = in.readInt();
				int qtdPlayers = in.readInt();
				client.updateCoordinates(playerid, x, y, qtdPlayers);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

}