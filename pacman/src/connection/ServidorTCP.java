package connection;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ServidorTCP {
	
  public static void main(String[] args) {
    try 
    {
    	ServerSocket servidor = new ServerSocket(12345);
    	System.out.println("Servidor ouvindo a porta 12345");
    	while(true) 
    	{
    		Socket cliente = servidor.accept();
    		System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
    		Scanner scanner = new Scanner(cliente.getInputStream());
    		
    		while (scanner.hasNextLine()) 
    		{		
    			if(scanner.nextLine() == "exit")
    			{
    	    		servidor.close();
    	    		scanner.close();
    	    		cliente.close();
    	    		System.out.println("Desconectado");
    			}
    			else
    			{
    				System.out.println(scanner.nextLine());
    			}		
    		}
    		servidor.close();
    		scanner.close();
    		cliente.close();
    	}  
    }   
    catch(Exception e) 
    {
       System.out.println("Erro: " + e.getMessage());
    }
  }     
}