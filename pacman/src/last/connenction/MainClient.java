package last.connenction;

import javax.swing.JFrame;

public class MainClient {

	public static void main(String[] args)
	{
		Client cl = new Client();

		JFrame janela = new JFrame("PacMan - Player" );
		janela.setSize(300, 200);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.add(cl);
		janela.addKeyListener(cl);
		
		janela.setVisible(true);
		cl.init();

	}

}
