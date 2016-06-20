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
	int screenHeight = 300;
	int screenWidth = 300;
	int sqm = 20;
	public short screenData[][] = { 
								{10,10,10,10,10,10,10,10,10,10,10,30,00,00,00,00,00},
								{10,10,10,10,10,10,10,10,10,10,10,50,50,50,50,00,00},
								{10,10,10,10,10,10,50,50,10,10,10,10,10,10,60,00,00},
								{10,10,10,10,10,10,30,60,10,10,10,10,10,10,60,00,00},
								{10,10,10,10,10,10,30,60,10,10,10,10,10,10,10,50,50},
								{10,10,10,10,10,10,50,50,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,50,50,50,50,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,30,00,00,50,65,10,10,10,10,10},
								{10,10,10,10,10,10,10,30,00,60,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,30,00,60,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,50,50,50,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
								{10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10}
								 };
	/*public short screenData[] = {
								 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 20,10,10,10,10,10,10,10,10,10,10,10,10,10,10, 
								 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 20,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
								 };*/

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
		
		if (screenData[ x2 * sqm][y2 * sqm] == 10)
		{
			//CHAMAR PAINT
		}
	}

	public void paint(Graphics g) 
	{
		super.paint(g);
		int x1,x2,y1,y2;
		for (int i = 0; i < 10; i++) 
		{
			g.drawOval(x[i], y[i], sqm, sqm);
		}
		for (int i = 0; i < 16; i++)
		{
			for (int j = 0; j < 17; j++)
			{
				//g.drawRect(i*20, j*20, 20, 20);
				if(screenData[i][j] == 20)
				{
					x1 = j * sqm; 
					x2 = j * sqm;
					y1 = (i * sqm) + sqm;
					y2 = (i * sqm);
					g.drawLine(x1,y1, x2 ,y2);
				}
				if(screenData[i][j] == 50)
				{
					x1 = (j * sqm);
					x2 = (j * sqm) - sqm;
					y1 = (i * sqm);
					y2 = (i * sqm);
					g.drawLine(x1,y1, x2 ,y2);
				}
				if(screenData[i][j] == 65)
				{
					x1 = (j * sqm) - sqm;
					x2 = (j * sqm) - sqm;
					y1 = (i * sqm);
					y2 = (i * sqm) - sqm;
					g.drawLine(x1,y1, x2 ,y2);
				}
				if(screenData[i][j] == 30)
				{
					x1 = (j* sqm) - sqm; 
					x2 = (j * sqm) - sqm;
					y1 = (i * sqm) + sqm;
					y2 = (i * sqm) - sqm;
					g.drawLine(x1,y1, x2 ,y2);
				}
				if(screenData[i][j] == 60)
				{
					x1 = (j* sqm); 
					x2 = (j * sqm);
					y1 = (i * sqm) + sqm;
					y2 = (i * sqm) - sqm;
					g.drawLine(x1,y1, x2 ,y2);
				}
				if(screenData[i][j] == 10)
				{
					g.drawOval(j * sqm - (sqm/2), i * sqm - (sqm/2) - 2, 5, 5);
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
			if (right == true && playerx < screenWidth) 
			{
				playerx += 1;
			}
			if (left == true && playerx > (screenWidth * (-1)) && playerx > 0) 
			{
				playerx -= 1;
			}
			if (down == true && playery < screenHeight) 
			{
				playery += 1;
			}
			if (up == true && playery > 0) 
			{
				playery -= 1;
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