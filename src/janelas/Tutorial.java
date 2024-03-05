package janelas;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Tutorial extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5471013275914447230L;
	public static final int LARGURA = 500;
	public static final int ALTURA = 400;
	public static CenarioTutorial cenarioTutorial = null;
	Image iconeJanela = Toolkit.getDefaultToolkit().getImage("imagens/icon.png");

	
	public Tutorial(){
		
		//inicializando as propriedades da janela
				setLayout(null);
				setRootPaneCheckingEnabled(true);
				setResizable(false);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setSize(LARGURA, ALTURA);
				setTitle("Guia para iniciantes");
				this.setIconImage(iconeJanela);
				setVisible(true);
				setLocationRelativeTo(Janela.cenarioSingle);
				
				
				
				cenarioTutorial = new CenarioTutorial();	
				cenarioTutorial.setBackground(Color.BLACK);
				cenarioTutorial.setFocusable(true);
				cenarioTutorial.setSize(800,570);

				cenarioTutorial.setDoubleBuffered(true); 
				this.add(cenarioTutorial);
				
		
	}

}
