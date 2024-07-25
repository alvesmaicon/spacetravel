package editordefases;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import estruturas.Fases;
import janelas.Janela;
import janelas.Jogo;
import salvamento.Salva;

public class EditorDeFase extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2242012436624614162L;

	private JButton botaoCriaFase, botaoEditaFase, botaoSairEditor;
//	private JButton botaoRemoveFase, botaoJogaFase;
	private JLabel criarFase, editarFase;
//	private JLabel experimentarFase, removerFase;
	private JComboBox<String> experimentaFaseBox, editaFaseBox, removeFaseBox;
	Image iconeJanela = Toolkit.getDefaultToolkit().getImage("imagens/icon.png");
	
	private String[] colecao;
	
	public EditorDeFase(){
		super("Editor");
		Container content = getContentPane();
		content.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(iconeJanela);
		this.setSize(220, 550);
		this.setLocation(810, 5);
		this.setResizable(false);
		this.setVisible(true);
		
		Font font = new Font("Serif", Font.PLAIN, 30);
		content.setFont(font);
		
		criarFase = new JLabel("Criar uma fase");
		criarFase.setBounds(new Rectangle(10, 10, 185, 25));
		content.add(criarFase, null);
		
		

		// cria uma estrutura de string para exibir as fases, com mais uma posição para 
		// exibir "Selecionar" rsss
		colecao = new String[Fases.vetorFases.size() + 1];
		colecao[0] = "Selecionar"; 

		atualizaColecaoComboBox();


		
		botaoCriaFase = new JButton("Criar uma nova fase");
		botaoCriaFase.setBounds(new Rectangle(10, 40, 185, 25));
		content.add(botaoCriaFase, null);
		botaoCriaFase.setActionCommand("CRIAF");
		botaoCriaFase.addActionListener(this);
		
//		experimentarFase = new JLabel("Experimentar uma fase");
//		experimentarFase.setBounds(new Rectangle(10, 200, 185, 25));
//		content.add(experimentarFase, null);
//		
//		experimentaFaseBox = new JComboBox<String>(colecao);
//		experimentaFaseBox.setBounds(new Rectangle(10, 230, 185, 25));
//		content.add(experimentaFaseBox, null);
//		
//		
//		botaoJogaFase = new JButton("Experimentar fase");
//		botaoJogaFase.setBounds(new Rectangle(10, 260, 185, 25));
//		content.add(botaoJogaFase, null);
//		botaoJogaFase.setActionCommand("JOGAF");
//		botaoJogaFase.addActionListener(this);
		
		editarFase = new JLabel("Editar uma fase existente");
		editarFase.setBounds(new Rectangle(10, 90, 185, 25));
		content.add(editarFase, null);
		
		editaFaseBox = new JComboBox<String>(colecao);
		editaFaseBox.setBounds(new Rectangle(10, 120, 185, 25));
		content.add(editaFaseBox, null);
		
		
		botaoEditaFase = new JButton("Editar fase");
		botaoEditaFase.setBounds(new Rectangle(10, 150, 185, 25));
		content.add(botaoEditaFase, null);
		botaoEditaFase.setActionCommand("EDITAF");
		botaoEditaFase.addActionListener(this);
			
		botaoSairEditor = new JButton("Sair do modo editor");
		botaoSairEditor.setBounds(new Rectangle(10, 480, 185, 25));
		content.add(botaoSairEditor, null);
		botaoSairEditor.setActionCommand("SAI");
		botaoSairEditor.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = (String) event.getActionCommand();
		
		if(comando.equals("CRIAF")){
			JOptionPane.showMessageDialog(Jogo.janela, "Você ainda não pode criar uma fase.");
		}
		
		if(comando.equals("EDITAF")){
			String escolhida = (String) editaFaseBox.getSelectedItem();
			int indice = selecionaComboBox(escolhida);
			editaFaseBox.setSelectedIndex(0);
			if(indice != -1){
				new editordefases.EditaFase(indice);
				
				Janela.cenarioEdicao.setVisible(true);
				Janela.cenarioSobre.setVisible(false);
				
				this.setVisible(false);
			}
		}
		
		if(comando.equals("JOGAF")){			
			experimentaFaseBox.setSelectedIndex(0);
			JOptionPane.showMessageDialog(Jogo.janela, "Você ainda não pode experimentar uma fase.");
		}
		
		if(comando.equals("REMOVEF")){
			removeFaseBox.setSelectedIndex(0);
			JOptionPane.showMessageDialog(Jogo.janela, "Você ainda não pode remover uma fase.");
			
		}
		
		
		if(comando.equals("SAI")){
			Janela.cenarioSobre.setVisible(true);
			Salva.salvaFases();
			this.dispose();
		}
		
	}
	
	private void atualizaColecaoComboBox(){
		for(int i = 0; i < Fases.vetorFases.size(); i++){
				colecao[i + 1] = (Fases.vetorFases.elementAt(i).getNome());	
		}
	}
	
	private int selecionaComboBox(String faseEscolhida){
		int indice = -1;
		
		for(int i = 0; i < Fases.vetorFases.size(); i ++){
			if(Fases.vetorFases.elementAt(i).getNome().equals(faseEscolhida)){
				indice = i;
				break;
			}
		}
		
		return indice;
	}

}
