package pacman;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

public class Pacman extends JFrame 
{
	
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	
    public Pacman() 
    {
    	connectToServer(); 	
        initUI();
    }
    
    public void connectToServer()
    {
		try 
		{
			socket = new Socket("localhost", 4444);
			System.out.println("Connected to " + socket.getInetAddress());
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} 
		catch (IOException e) 
		{
			//tratar erro
		}
    }
    
    private void initUI() 
    {
        
        add(new pac());
        add(new pac());
        setTitle("PACMAN MULTIPLAYER");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 425); 
        setLocationRelativeTo(null);
        setVisible(true);        
    }

    public static void main(String[] args) 
    {

        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                Pacman ex = new Pacman();
                ex.setVisible(true);
            }
        });
    }
}
