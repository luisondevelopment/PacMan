package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ClienteTCP {
	
	 public static void main(String[] args)  throws UnknownHostException, IOException 
	 {
		 
		 Socket cliente = new Socket("192.168.0.101",12345);
	     System.out.println("O cliente se conectou ao servidor!");
	     
	     Scanner teclado = new Scanner(System.in);
	     PrintStream saida = new PrintStream(cliente.getOutputStream());
	     
	     while (teclado.hasNextLine()) 
	     {
	    	 saida.flush();
	    	 saida.println(teclado.nextLine());
	    	 saida.flush();
	     }
	     
	     saida.close();
	     teclado.close();
	     cliente.close();
	     
	 }
}

