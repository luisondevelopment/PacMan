package last.connenction;

import java.net.*;
import java.awt.Color;
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
	int playerSpeed = 160;
	int screenHeight = 680;
	int screenWidth = 950;
	int sqm = 20;
	public short screenData[][] = { //		  5             10             15             20 			  25            30              35
								{ 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 9, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						 /*5*/	{ 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						 		{ 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,20, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						 		{ 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,20, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						 		{ 0, 0, 0, 0, 0, 0, 0, 0,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,20, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						 		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0,20,20, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/* 0*/	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,20 , 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,0 ,25,25,25,20, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,0 ,0 ,0 ,0 ,20, 0, 0, 0, 0, 0},
								{ 0, 0, 0,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25,25,20, 0, 0, 0, 0, 0},
								{ 0, 0,20, 0, 0,20, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*15*/	{ 0, 0,20, 0, 0,20, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0,20, 0, 0,20, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0,20, 0, 0,20, 0,20, 0, 0,25,25,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0,20, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,25,25, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*20*/	{ 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25,25,25, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0,20, 0, 0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*25*/	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*30*/	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*35*/	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					   /*40*/	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*45*/  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*50*/  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
		g.drawOval(x[playerid], y[playerid], sqm, sqm);
		
		for (int k = 0; k < 10; k++) 
		{
			if((y[k] < playery + 4 && y[k] > playery -4) && (x[k] < playerx + 4 && x[k] > playerx - 4))
				g.drawOval(x[k], y[k], sqm, sqm);
		}
		
		for(int i = 0 ; i < 49 ; i++)
		{
			for (int j = 0; j < 34 ; j++)
			{	
				if ((j < playery + 4 && j > playery - 4) && (i < playerx + 4 && i > playerx - 4))
				{
					if(screenData[i][j] == 10)
					{
						int offsetX = (i * sqm) + 8;
						int offsetY = (j * sqm) + 8;
						g.drawOval(offsetX, offsetY, 5, 5);
					}
					if(screenData[i][j] == 20)
					{
						int x = (i * sqm);
						int y = (j * sqm) + 10;
						g.drawLine(x, y, x+20, y);
					}
					if(screenData[i][j] == 25)
					{
						int x = (i * sqm) + 10;
						int y = (j * sqm);
						g.drawLine(x, y, x, y+20);
					}
					if(screenData[i][j] == 9)
					{
						int offsetX = (i * sqm) + 8;
						int offsetY = (j * sqm) + 8;
						int x = (i * sqm) + 10;
						int y = (j * sqm);
						g.setColor(Color.BLUE);
						g.drawOval(offsetX, offsetY, 7, 7);
						g.setColor(Color.BLACK);
					}
				}
			}
		}
		
		g.drawString(Integer.toString(playerx) + " - " + Integer.toString(playery),15,15);
		repaint();
	}

	@Override
	public void run() 
	{
		while (true) 
		{	//REGRAS DE MOVIMENTAÇÃO
			
			if (screenData[playerx][playery] == 9)
			{
				playerSpeed -= 10;
				screenData[playerx][playery] = 0;
			}
			
			if (right == true && (playerx * sqm) < screenWidth) 
			{
				if (screenData[playerx + 1][playery] < 20)
				{
					playerx += 1;
				}
			}
			if (left == true && (playerx * sqm) > (screenWidth * (-1)) && playerx > 0) 
			{
				if (screenData[playerx - 1][playery] < 20)
				{
					playerx -= 1;
				}
			}
			if (down == true && (playery * sqm)< screenHeight) 
			{
				if (screenData[playerx][playery + 1] < 20)
				{
					playery += 1;
				}
			}
			if (up == true && (playery * sqm) > 0) 
			{
				if (screenData[playerx][playery - 1] < 20)
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
				Thread.sleep(playerSpeed);
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