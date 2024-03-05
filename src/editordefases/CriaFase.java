package editordefases;

import java.awt.Container;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import janelas.Janela;
import objetos.Fase;

public class CriaFase extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4047068596922397166L;
	
	

	private JButton botaoSairCriar;
	Image iconeJanela = Toolkit.getDefaultToolkit().getImage("imagens/final.png");
	
	public Fase fase;
	
	public CriaFase(){
		super("Criar");
		
		Container content = getContentPane();
		content.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(iconeJanela);
		this.setSize(210, 550);
		this.setLocation(810, 5);
		this.setResizable(false);
		this.setVisible(true);
		
		fase = new Fase(" ");
		fase.addPlanetaFinal(400, 200, 30);
		
		
		botaoSairCriar = new JButton("Cancelar");
		botaoSairCriar.setBounds(new Rectangle(10, 480, 185, 25));
		content.add(botaoSairCriar, null);
		botaoSairCriar.setActionCommand("SAI");
		botaoSairCriar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = (String) event.getActionCommand();
		
		if(comando.equals("SAI")){
			janelas.CenarioSobre.editorDeFase.setVisible(true);
			Janela.cenarioEdicao.setVisible(false);

			this.dispose();
		}
		
	}

}
