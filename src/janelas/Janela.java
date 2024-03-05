package janelas;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import editordefases.CenarioCriacao;
import editordefases.CenarioEdicao;
//import util.Funcoes;

public class Janela extends JFrame {

	/**
	 * 
	 */

	private static final long serialVersionUID = 8814249499822087744L;
	public static final int LARGURA = 800;
	public static final int ALTURA = 550;
	Image iconeJanela = Toolkit.getDefaultToolkit().getImage("imagens/icon.png");

	private JMenuBar barraDeMenus;
	private JMenu menuJogo, menuExibir, menuAjuda;
	private JMenuItem itemDeMenuSelecionaFase, itemDeMenuSair,
	itemDeMenuEditarFase, itemDeMenuTempos,itemDeMenuConquistas, itemDeMenuComoJogar, 
	itemDeMenuSobre;


	public static JComponent cenarioAtivo = null, cenarioAnterior = null;
	public static CenarioSingle cenarioSingle = null;
	public static CenarioMulti cenarioMulti = null;
	public static CenarioInicio cenarioInicio = null;
	public static CenarioSobre cenarioSobre = null;
	public static CenarioComoJogar cenarioComoJogar = null;
	//TODO
	public static JComponent cenarioEditor = null;
	public static CenarioEdicao cenarioEdicao = null;
	public static CenarioCriacao cenarioCriacao = null;

	
	public Janela(){
		
		
		

		//inicializando as propriedades da janela
		setLayout(null);
		setRootPaneCheckingEnabled(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(LARGURA, ALTURA);
		setTitle("Space Travel");
		this.setIconImage(iconeJanela);
		setVisible(true);
		setLocation(10, 5);
		
		barraDeMenus = new JMenuBar();
		//this.setJMenuBar(barraDeMenus);
		barraDeMenus.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
		
		menuJogo = new JMenu("Jogo");
		barraDeMenus.add(menuJogo);
		
		menuExibir = new JMenu("Exibir");
		barraDeMenus.add(menuExibir);
		
		menuAjuda = new JMenu("Ajuda");
		barraDeMenus.add(menuAjuda);
		
		/*-------------------------------*/

		
		itemDeMenuSelecionaFase = new JMenuItem("Selecionar fase");
		itemDeMenuSelecionaFase.setActionCommand("SELECIONAF");
		menuJogo.add(itemDeMenuSelecionaFase);
		
		itemDeMenuSair = new JMenuItem("Sair");
		menuJogo.add(itemDeMenuSair);
		
		itemDeMenuEditarFase = new JMenuItem("Editor de fases");
		menuExibir.add(itemDeMenuEditarFase);
		
		itemDeMenuTempos = new JMenuItem("Melhores tempos");
		menuExibir.add(itemDeMenuTempos);
		
		itemDeMenuConquistas = new JMenuItem("Tabela de conquistas");
		menuExibir.add(itemDeMenuConquistas);
		
		itemDeMenuComoJogar = new JMenuItem("Como jogar");
		menuAjuda.add(itemDeMenuComoJogar);
		
		itemDeMenuSobre = new JMenuItem("Sobre");
		menuAjuda.add(itemDeMenuSobre);

		//criando e inicializando a área do jogo
		criaCenarios();
		
		cenarioAtivo = cenarioInicio;
		
		
		
		

//		this.addWindowListener(new WindowAdapter(){
//			public void windowClosing (WindowEvent event){
//				
//				Funcoes.fecharJogo();
//			}
//		});
			
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		String comando = (String) e.getActionCommand();
		
		if(comando.equals("SELECIONAF")){
			
		}
		
	}

	public void criaCenarios(){
		
		cenarioInicio = new CenarioInicio();	
		cenarioInicio.setBackground(Color.BLACK);
		cenarioInicio.setFocusable(true);
		cenarioInicio.setSize(800,570);

		cenarioInicio.setDoubleBuffered(true); 
		this.add(cenarioInicio);
		
		cenarioSingle = new CenarioSingle();
		cenarioSingle.setVisible(false);
		cenarioSingle.setBackground(Color.BLACK);
		cenarioSingle.setFocusable(true);
		cenarioSingle.setSize(800,550);

		cenarioSingle.setDoubleBuffered(true); 
		this.add(cenarioSingle);
	
		cenarioMulti = new CenarioMulti();
		cenarioMulti.setVisible(false);
		cenarioMulti.setBackground(Color.BLACK);
		cenarioMulti.setFocusable(true);
		cenarioMulti.setSize(800,550);

		cenarioMulti.setDoubleBuffered(true); 
		this.add(cenarioMulti);
		
		cenarioComoJogar = new CenarioComoJogar();
		cenarioComoJogar.setVisible(false);
		cenarioComoJogar.setBackground(Color.BLACK);
		cenarioComoJogar.setFocusable(true);
		cenarioComoJogar.setSize(800,550);

		cenarioComoJogar.setDoubleBuffered(true); 
		this.add(cenarioComoJogar);

		cenarioSobre = new CenarioSobre();
		cenarioSobre.setVisible(false);
		cenarioSobre.setBackground(Color.BLACK);
		cenarioSobre.setFocusable(true);
		cenarioSobre.setSize(800,550);

		cenarioSobre.setDoubleBuffered(true); 
		this.add(cenarioSobre);
		
		
		
		cenarioCriacao = new CenarioCriacao();
		cenarioCriacao.setVisible(false);
		cenarioCriacao.setBackground(Color.BLACK);
		cenarioCriacao.setFocusable(true);
		cenarioCriacao.setSize(800,550);

		cenarioCriacao.setDoubleBuffered(true); 
		this.add(cenarioCriacao);
		
		cenarioEdicao = new CenarioEdicao();
		cenarioEdicao.setVisible(false);
		cenarioEdicao.setBackground(Color.BLACK);
		cenarioEdicao.setFocusable(true);
		cenarioEdicao.setSize(800,550);

		cenarioEdicao.setDoubleBuffered(true); 
		this.add(cenarioEdicao);
		
		
		
		
	}
	
	public static void trocaCenario(JComponent cenario){
		cenario.setVisible(true);
		cenarioAnterior = cenarioAtivo;
		cenarioAtivo.setVisible(false);
		cenarioAtivo = cenario;
	}
	
}
