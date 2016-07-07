package last.connenction;

import java.net.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	int playerSize = 25;
	int screenHeight = 680;
	int screenWidth = 950;
	int sqm = 20;
	int pontos = 0;
	int qtdPontos = 0;
	BufferedImage down1, down2, down3;
	BufferedImage left1, left2, left3;
	BufferedImage right1, right2, right3;
	BufferedImage up1, up2, up3;
	BufferedImage imgPacFechado = null;
	BufferedImage imgPacMaisAberto = null;
	BufferedImage imgPacAberto = null;
	BufferedImage imagem = null;
	BufferedImage imgEat = null;
	BufferedImage background = null;
	int contador = 0;
	int ajuste = 0;
	private final Font smallfont = new Font("Helvetica", Font.BOLD, 14);
	private final Font fonteVencedor = new Font("Helvetica", Font.BOLD, 30);
	private final Color dotcolor = new Color(192, 192, 0);

	
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
								{ 0, 0,20, 0, 0,20, 0,20, 0, 0,25,25,25,25,25,25,25,25,25,25,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0,20, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,25,25, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0,20,25,25,25,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*20*/	{ 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25,25,25, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*25*/	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25, 0, 0},
						/*30*/	{ 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,20, 9,20, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,20,25,25,25,25,25, 0,25,25,25,25, 0, 0, 0, 0, 0,20, 0, 0,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*35*/	{ 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,20, 0,25,25,25,25,25,25,25,25,25, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					   /*40*/	{ 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0,20, 0,20, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0,20, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							    { 0, 0, 0,20, 0,25,25,25,25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,25,25, 0, 0},
							    { 0, 0, 0,20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20,9,20, 0, 0},
						/*45*/  { 0, 0, 0,20, 0,25,25,25,25, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25, 0,20, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,20, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,25,25,25,25,25,20, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
						/*50*/  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								 };

	public void init() 
	{
		try 
		{
			carregaImagens();
			
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
			
			for(int i = 0 ; i < 49 ; i++)
			{
				for (int j = 0; j < 34 ; j++)
				{	
					if (screenData[i][j] == 9)
						qtdPontos++;
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Cliente com problema");
		}
	}
	
	public void carregaImagens()
	{
		try 
		{
			background = ImageIO.read(new File("../pacman/src/images/background.png"));
			down1 = ImageIO.read(new File("../pacman/src/images/down1.gif"));
			down2 = ImageIO.read(new File("../pacman/src/images/down2.gif"));
			down3 = ImageIO.read(new File("../pacman/src/images/down3.gif"));
			up1 = ImageIO.read(new File("../pacman/src/images/up1.gif"));
			up2 = ImageIO.read(new File("../pacman/src/images/up2.gif"));
			up3 = ImageIO.read(new File("../pacman/src/images/up3.gif"));
			left1 = ImageIO.read(new File("../pacman/src/images/left1.gif"));
			left2 = ImageIO.read(new File("../pacman/src/images/left2.gif"));
			left3 = ImageIO.read(new File("../pacman/src/images/left3.gif"));
			right1 =  ImageIO.read(new File("../pacman/src/images/right1.gif"));
			right2 =  ImageIO.read(new File("../pacman/src/images/right2.gif"));
			right3 =  ImageIO.read(new File("../pacman/src/images/right3.gif"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("Problema ao carregar as imagens");
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
		
		Font fonte = new Font("Helvetica", Font.BOLD, 15);
		g.setFont(fonte);
		
		g.drawImage(background, 0, 0,1000,1000,this);
		g.drawImage(imagem, x[playerid] - ajuste, y[playerid] - ajuste,playerSize ,playerSize,this);
		
		g.setColor(new Color(96, 128, 255));
		g.drawString("Player " + (playerid + 1) + "   Pontos:" + pontos + "/" + qtdPontos, 90, 10);
		g.setColor(Color.BLACK);
		
		if (pontos == qtdPontos && pontos > 0)
			g.drawString("Player: " + (playerid + 1) +" ganhou!!", 110, 400);
		
		for (int k = 0; k < 10; k++) 
		{
			if((x[k] < x[playerid] + 80 && x[k] > x[playerid] - 80) && (y[playerid] < y[playerid] + 80 && y[k] > playerx - 80))
			{
				if(k != playerid)
					g.drawImage(imagem, x[k], y[k],25 ,25 ,this);
			}
			if(pontos == qtdPontos)
			{
				g.setFont(fonteVencedor);
				g.setColor(Color.BLUE);
				g.drawString("Player " + (playerid + 1) + " é o vencedor!!" , 200, 200);
			}
		}
		
		for(int i = 0 ; i < 49 ; i++)
		{
			g.setColor(Color.WHITE);
			for (int j = 0; j < 34 ; j++)
			{	
				if ((j < playery + 4 && j > playery - 4) && (i < playerx + 4 && i > playerx - 4))
				{	
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(1));
					
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
						g.drawImage(imgEat, offsetX, offsetY,10 ,10 ,this);
						
			            g2.setColor(dotcolor);
			            g2.fillRect(offsetX, offsetY, 7, 7);
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
				playerSize += 4;
				ajuste += 2;
				playerSpeed -= 10;
				screenData[playerx][playery] = 0;
				pontos++;
			}
			
			if (right == true && (playerx * sqm) < screenWidth) 
			{
				if (screenData[playerx + 1][playery] < 20)
				{
					playerx += 1;
					
					contador++;
					if(contador > 15)
						contador = 0;
					if(contador >= 0 && contador < 5)
						imagem = right1;
					if(contador >= 5 && contador < 10)
						imagem = right2;
					if(contador <= 15 && contador >=10)
						imagem = right3;
				}
			}
			if (left == true && (playerx * sqm) > (screenWidth * (-1)) && playerx > 0) 
			{
				if (screenData[playerx - 1][playery] < 20)
				{
					playerx -= 1;
					
					contador++;
					if(contador > 15)
						contador = 0;
					if(contador >= 0 && contador < 5)
						imagem = left1;
					if(contador >= 5 && contador < 10)
						imagem = left2;
					if(contador <= 15 && contador >=10)
						imagem = left3;
				}
			}
			if (down == true && (playery * sqm)< screenHeight) 
			{
				if (screenData[playerx][playery + 1] < 20)
				{
					playery += 1;
					
					contador++;
					if(contador > 15)
						contador = 0;
					if(contador >= 0 && contador < 5)
						imagem = down1;
					if(contador >= 5 && contador < 10)
						imagem = down2;
					if(contador <= 15 && contador >=10)
						imagem = down3;
				}
			}
			if (up == true && (playery * sqm) > 0) 
			{
				if (screenData[playerx][playery - 1] < 20)
				{
					playery -= 1;
					
					contador++;
					if(contador > 15)
						contador = 0;
					if(contador >= 0 && contador < 5)
						imagem = up1;
					if(contador >= 5 && contador < 10)
						imagem = up2;
					if(contador <= 15 && contador >=10)
						imagem = up3;
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
	public void keyTyped(KeyEvent e) {}

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