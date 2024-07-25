package janelas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import estruturas.Fases;
import objetos.Sons;
import salvamento.Carrega;
import sons.ReprodutorDeMusicaInicio;
import sons.ReprodutorDeSom;
import util.Funcoes;

public class CenarioInicio extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3023445802850613990L;

	

	private Image fundo = null, comsom = null, semsom = null, commusica = null, semmusica = null;

	public static ReprodutorDeMusicaInicio musica_inicio = null;
	
	public static Sons audio = null;
	
	public static boolean jogaFree = false;
	
	private int x, y;
	
	
	
	

	public CenarioInicio(){
		
		
		
		audio = new Sons();
		
		Carrega.carregaConfigAudio();
		
		

		//carga da imagem de fundo em um ícone (primitivo da imagem)
		fundo = Toolkit.getDefaultToolkit().getImage("imagens/inicio.png");
		fundo = fundo.getScaledInstance(
				Janela.LARGURA, Janela.ALTURA, Image.SCALE_DEFAULT);
		
		comsom = Toolkit.getDefaultToolkit().getImage("imagens/comsom.png");
		comsom = comsom.getScaledInstance(25, 20, Image.SCALE_DEFAULT);
		semsom = Toolkit.getDefaultToolkit().getImage("imagens/semsom.png");
		semsom = semsom.getScaledInstance(25, 20, Image.SCALE_DEFAULT);
		commusica = Toolkit.getDefaultToolkit().getImage("imagens/commusica.png");
		commusica = commusica.getScaledInstance(25, 20, Image.SCALE_DEFAULT);
		semmusica = Toolkit.getDefaultToolkit().getImage("imagens/semmusica.png");
		semmusica = semmusica.getScaledInstance(25, 20, Image.SCALE_DEFAULT);
		
		musica_inicio = new ReprodutorDeMusicaInicio("sons/musica-inicio-loop.wav");
		musica_inicio.start();

		
		//configura os listeners de teclado
		this.addKeyListener(
				new KeyListener(){
					
					@Override
					public void keyPressed(KeyEvent evento){


						if (evento.getKeyCode() == KeyEvent.VK_1){
							Janela.trocaCenario(Janela.cenarioSingle);

							musica_inicio.pausar();
							CenarioSingle.musica_single.recomeca();
							CenarioSingle.musica_single.retomar();
							CenarioSingle.reiniciaFase();
							CenarioSingle.setFaseAtual();
							
							jogaFree = false;
						}
						
						if(evento.getKeyCode() == KeyEvent.VK_2){
							Janela.trocaCenario(Janela.cenarioMulti);
							
							
							musica_inicio.pausar();
							CenarioMulti.musica_multi.recomeca();	
							CenarioMulti.musica_multi.retomar();
							CenarioMulti.reiniciaJogo();
						}
						
						if(evento.getKeyCode() == KeyEvent.VK_3){
							int indice = selecionaFaseLiberada();
							if(indice != -1){
								jogaFree = true;
								Janela.trocaCenario(Janela.cenarioSingle);
								
								musica_inicio.pausar();
								CenarioSingle.musica_single.recomeca();
								CenarioSingle.musica_single.retomar();
								CenarioSingle.setFaseAtual(indice);
							}
							ReprodutorDeSom.getInstancia().reproduzir("click");
						}
						
						if(evento.getKeyCode() == KeyEvent.VK_4){
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.trocaCenario(Janela.cenarioSobre);
							
							musica_inicio.pausar();
							CenarioSobre.musica_sobre.recomeca();
							CenarioSobre.musica_sobre.retomar();
						}
						
						if(evento.getKeyCode() == KeyEvent.VK_5){
							Funcoes.fecharJogo();
							
						}
						
						

					}

					@Override
					public void keyReleased(KeyEvent evento){
					}

					@Override
					public void keyTyped(KeyEvent evento){
					}
				}
				);
		
		this.addMouseListener(
				new MouseListener(){
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
						x = e.getX();
						y = e.getY();
						
						//BOTAO 1
						if(x > 295 && x < 520 && y > 262 && y < 292){
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.trocaCenario(Janela.cenarioSingle);
							
							musica_inicio.pausar();
							CenarioSingle.musica_single.recomeca();
							CenarioSingle.musica_single.retomar();
							CenarioSingle.setFaseAtual();
							
							jogaFree = false;
						}
						//BOTAO 2
						if(x > 295 && x < 520 && y > 310 && y < 340){
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.trocaCenario(Janela.cenarioMulti);
							
							musica_inicio.pausar();
							CenarioMulti.musica_multi.recomeca();
							CenarioMulti.musica_multi.retomar();
							CenarioMulti.reiniciaJogo();
						}
						
						//BOTAO 3
						
						/**TODO Selecionar fase vencida já*/
						if(x > 295 && x < 520 && y > 358 && y < 388){
							int indice = selecionaFaseLiberada();
							if(indice != -1){
								jogaFree = true;
								Janela.trocaCenario(Janela.cenarioSingle);
								
								musica_inicio.pausar();
								CenarioSingle.musica_single.recomeca();
								CenarioSingle.musica_single.retomar();
								CenarioSingle.setFaseAtual(indice);
							}
							ReprodutorDeSom.getInstancia().reproduzir("click");
						}
						
						//BOTAO 4
						if(x > 295 && x < 520 && y > 405 && y < 435){
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.trocaCenario(Janela.cenarioSobre);
							
							musica_inicio.pausar();
							CenarioSobre.musica_sobre.recomeca();
							CenarioSobre.musica_sobre.retomar();
						}
						//BOTAO 5
						if(x > 295 && x < 520 && y > 450 && y < 480){
							Funcoes.fecharJogo();
						}
						
						//SOM
						if(x > 725 && x < 750 && y > 485 && y < 505){
							if(audio.isSom()){
								audio.setSom(false);
								
							}
							else{
								audio.setSom(true);
								ReprodutorDeSom.getInstancia().reproduzir("auxilio");
							}
							
						}
						
						//MUSICA
						if(x > 755 && x < 780 && y > 485 && y < 505){
							if(audio.isMusica()){
								audio.setMusica(false);
							}
							else{
								audio.setMusica(true);
							}
						}
						//volume menos 
						// (657, 485, 15, 15)
						if(x > 657 && x < 672 && y > 485 && y < 500){
							audio.setVolumeDiminui();
							ReprodutorDeSom.getInstancia().reproduzir("auxilio");
						}
						//volume mais
						// (674, 485, 15, 15)
						if(x > 674 && x < 689 && y > 485 && y < 500){
							audio.setVolumeAumenta();
							ReprodutorDeSom.getInstancia().reproduzir("auxilio");
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				}
				);

	}
		
		

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		desenharCoisas(g);

	}

	/** Renderizador do jogo*/

	
	public void desenharCoisas(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		//desenha o FUNDO
		g2d.drawImage(fundo, 0, -30, null);


		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		g2d.drawString("Um jogador", 360, 284);
		g2d.drawString("Dois jogadores", 350, 330);
		g2d.drawString("Selecionar fase", 347, 378);
		g2d.drawString("Sobre", 385, 425);
		g2d.drawString("Sair", 392, 470);
		
		if(audio.isSom()){
			g2d.drawImage(comsom, 725, 485, null);
		}
		else{
			g2d.drawImage(semsom, 725, 485, null);
		}
		if(audio.isMusica()){
		g2d.drawImage(commusica, 755, 485, null);
		}
		else{
		g2d.drawImage(semmusica, 755, 485, null);
		}
		
		
		//controle de volume
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
//		g2d.drawRect(674, 485, 15, 15);
		g2d.drawString("+", 675, 500);
//		g2d.drawRect(657, 485, 15, 15);
		g2d.drawString("-", 660, 498);
		
		
		if(audio.getVolume() >= -66.0f){
			g2d.setColor(Color.green);
		}
		else
			g2d.setColor(Color.darkGray);
		g2d.fillRect(695, 490, 2, 10);
		if(audio.getVolume() >= -52.0f){
			g2d.setColor(Color.GREEN);
		}
		else
			g2d.setColor(Color.darkGray);
		g2d.fillRect(700, 490, 2, 10);
		if(audio.getVolume() >= -38.0f){
			g2d.setColor(Color.yellow);
		}
		else
			g2d.setColor(Color.darkGray);
		g2d.fillRect(705, 490, 2, 10);
		if(audio.getVolume() >= -24.0f){
			g2d.setColor(Color.orange);
		}
		else
			g2d.setColor(Color.darkGray);
		g2d.fillRect(710, 490, 2, 10);
		if(audio.getVolume() >= -10.0f){
			g2d.setColor(Color.red);
		}
		else
			g2d.setColor(Color.darkGray);
		g2d.fillRect(715, 490, 2, 10);
		
		
//		g2d.drawRect(295, 262, 225, 30);
//		g2d.drawRect(295, 310, 225, 30);
//		g2d.drawRect(295, 358, 225, 30);
//		g2d.drawRect(295, 405, 225, 30);
//		g2d.drawRect(295, 450, 225, 30);
		

		Toolkit.getDefaultToolkit().sync(); /* garante atualização da tela
        nos variados sistemas de janela */
		g.dispose(); /* libera os recursos gráficos */
		
		
		repaint();

	}
	
	private int selecionaFaseLiberada(){
		
		// cria uma estrutura de string para exibir as fases, com mais uma posição para 
		// exibir "Selecionar" rsss
		String[] string = new String[Fases.vetorFases.size() + 1];
		string[0] = "Selecionar";
		
		// atribui a todas "indisponivel" a partir da posicao 1
		for(int i = 1; i < string.length; i++)
			string[i] = "Indisponível";
		Object[] Opcoes;
		
		// adiciona na lista de exibição de fases as fases que já foram vencidas
		for(int i = 0; i < Fases.vetorFases.size(); i++){
			if(Fases.vetorFases.elementAt(i).isFaseJogarLivre()){
				string[i + 1] = (Fases.vetorFases.elementAt(i).getNome());	
			}
		}
		
		Opcoes = (Object[]) string;
		
		// JOption pane para selecionar fase em um combobox
		String faseEscolhida = (String) JOptionPane.showInputDialog(Janela.cenarioInicio,
				"Selecione uma fase para jogar.", "Selecionar fase", JOptionPane.QUESTION_MESSAGE, 
				null, Opcoes, Opcoes);
		
		int indice = -1;
		
		/* retorna o indice da fase selecionada. se a opcao selecionada nao for um indice de fase vencida,
		* é retornado -1
		*/
		for(int i = 0; i < Fases.vetorFases.size(); i ++){
			if(Fases.vetorFases.elementAt(i).getNome().equals(faseEscolhida)){
				indice = i;
				break;
			}
		}
		
		return indice;
	}

}

