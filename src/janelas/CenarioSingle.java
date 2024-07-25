package janelas;

import java.awt.BasicStroke;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import estruturas.Fases;
import estruturas.Single;
import objetos.Espaconave;
import objetos.Lua;
import objetos.Planeta;
import sons.ReprodutorDeMusica1;
import sons.ReprodutorDeSom;
import util.Funcoes;

public class CenarioSingle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3023445802850613990L;

	private static final int ATRASO_INICIAL = 0;
	private static final int INTERVALO = 10;
	private static final int X_FPS = 730;
	private static final int Y_FPS = 510;
	private static int COMPRIMENTO_AUXILIO_ROTA = 230;

	public static ReprodutorDeMusica1 musica_single = null;

	/**TODO declaracao dos esquemas do sprite*/
	private static int POSICAO_X_EXPLOSAO = 300;
	private static int POSICAO_Y_EXPLOSAO = 300;

	private static final int DESLOCAMENTO_X_EXPLOSAO = 100;
	private static final int DESLOCAMENTO_Y_EXPLOSAO = 100;
	private static final int X1_EXPLOSAO = 0;
	private static final int Y1_EXPLOSAO = 0;
	private static final int X2_EXPLOSAO = 100;
	private static final int Y2_EXPLOSAO = 100;
	private static final int LARGURA_SPRITE = 900;

	private static int faseAtual = 0;


	private int sx1 = X1_EXPLOSAO, sy1 = Y1_EXPLOSAO,
			sx2 = X2_EXPLOSAO, sy2 = Y2_EXPLOSAO;


	private static final float[] tracejado = {5.0f};
	private static final float[] tracejadoAuxilio = {5.0f, 20.0f};


	private static long tempoPerdeu = 0;
	private static long tempoVenceu = 0;


	private static String tempoFase;
	private static long tempoFaseCorrido = 0;
	private static long tempoInicialFase = System.currentTimeMillis();
	private static long tempoAnterior = System.currentTimeMillis();
	private static int quantidadeDeChamadas = 0;
	private static int fps = 0;

	private Image fundo = null, sub_menu = null, menu_venceu = null, menu_venceu_2,
			menu_perdeu = null;

	private Image imagemEspaconave = null, imagemLua = null, imagemPlanetaInicial = null, 
			planetaFinal = null, planeta1 = null, planeta2 = null, 
			planeta3 = null, planeta4 = null, planeta5 = null, 
			planeta6 = null;
	private Image imagem = null;

	private Image vidas3 = null, vidas2 = null, vidas1 = null;
	private Image explosaoSprite = null;
	private Timer timer;

	private Planeta planetaOrbitado;
	private Planeta planetaColidir;

	private static boolean Pausa = false;

	private static boolean Venceu = false;
	private static boolean VenceuJogo = false;
	public static boolean Perdeu = false;
	private static boolean MenuVenceu = false;
	private static boolean MenuPerdeu = false;
	private static boolean MenuFase = false;
	
	private static boolean AtingiuMelhorTempo = false;

	//posiçoes do mouse
	private int x, y;

	//auxilio para giro de cenário
	private static double theta;
	private static int xnave, ynave;

	public static String planetaAtual = "string1", planetaAnterior = "string2";
	
	public CenarioSingle(){

		//carga da imagem de fundo em um ícone (primitivo da imagem)
		fundo = Toolkit.getDefaultToolkit().getImage("imagens/milkway.png");
		fundo = fundo.getScaledInstance(
				Janela.LARGURA, Janela.ALTURA, Image.SCALE_DEFAULT);

		vidas3 = Toolkit.getDefaultToolkit().getImage("imagens/vidas3.png");
		vidas3 = vidas3.getScaledInstance(50, 20, Image.SCALE_DEFAULT);

		vidas2 = Toolkit.getDefaultToolkit().getImage("imagens/vidas2.png");
		vidas2 = vidas2.getScaledInstance(50, 20, Image.SCALE_DEFAULT);

		vidas1 = Toolkit.getDefaultToolkit().getImage("imagens/vidas1.png");
		vidas1 = vidas1.getScaledInstance(50, 20, Image.SCALE_DEFAULT);

		explosaoSprite = Toolkit.getDefaultToolkit().getImage("imagens/explosaosprite.png");

		sub_menu = Toolkit.getDefaultToolkit().getImage("imagens/menu-pausa.png");
		menu_venceu = Toolkit.getDefaultToolkit().getImage("imagens/menu-venceu.png");
		menu_venceu_2 = Toolkit.getDefaultToolkit().getImage("imagens/menu-venceu-2.png");
		menu_perdeu = Toolkit.getDefaultToolkit().getImage("imagens/menu-perdeu.png");


		imagemEspaconave = Toolkit.getDefaultToolkit().getImage("imagens/ranger.png");
		imagemEspaconave = imagemEspaconave.getScaledInstance(20, 30, Image.SCALE_DEFAULT);

		imagemPlanetaInicial = Toolkit.getDefaultToolkit().getImage("imagens/inicial.png");


		imagemLua = Toolkit.getDefaultToolkit().getImage("imagens/lua.png");
		imagemLua = imagemLua.getScaledInstance(30, 30, Image.SCALE_DEFAULT);

		planetaFinal = Toolkit.getDefaultToolkit().getImage("imagens/final.png");
		planeta1 = Toolkit.getDefaultToolkit().getImage("imagens/planeta1.png");
		planeta2 = Toolkit.getDefaultToolkit().getImage("imagens/planeta2.png");
		planeta3 = Toolkit.getDefaultToolkit().getImage("imagens/planeta3.png");
		planeta4 = Toolkit.getDefaultToolkit().getImage("imagens/planeta4.png");
		planeta5 = Toolkit.getDefaultToolkit().getImage("imagens/planeta5.png");
		planeta6 = Toolkit.getDefaultToolkit().getImage("imagens/planeta6.png");

		//configuração do timer
		timer = new Timer();
		timer.scheduleAtFixedRate(new Ciclo(), ATRASO_INICIAL, INTERVALO);

		musica_single = new ReprodutorDeMusica1("sons/musica-1-loop.wav");
		musica_single.start();
		musica_single.pausar();

		//configura os listeners de mouse

		this.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent event) {

						x = event.getX();
						y = event.getY();

						if(Pausa){
							//BOTAO 1 DESPAUSA
							if(x > 295 && x < 520 && y > 280 && y < 310){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Pausa = false;
								musica_single.recomeca();
								musica_single.retomar();
							}
							//BOTAO 2 ABRE COMO JOGAR
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioComoJogar);
								musica_single.pausar();
								CenarioComoJogar.musica_como_jogar.recomeca();
								CenarioComoJogar.musica_como_jogar.retomar();

							}
							//BOTAO 3 VOLTA PRO MENU
							if(x > 295 && x < 520 && y > 375 && y < 405){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								reiniciaFase();
								musica_single.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();
								Pausa = false;


							}
						}
						if(MenuFase){
							//BOTAO 1 PROXIMA FASE
							if(x > 295 && x < 520 && y > 280 && y < 310){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								reiniciaFase();
								faseAtual++;
								Single.selecionaFase(faseAtual);
							}
							//BOTAO 2 VOLTA PRO INICIO
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								reiniciaFase();
								musica_single.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();
								Pausa = false;

							}
							//BOTAO 3 SAI DO JOGO
							if(x > 295 && x < 520 && y > 375 && y < 405){
								Funcoes.fecharJogo();
							}
						}

						if(MenuVenceu && !CenarioInicio.jogaFree){
							//BOTAO 1 COMECA DE NOVO
							if(x > 295 && x < 520 && y > 280 && y < 310){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								reiniciaJogo();
							}
							//BOTAO 2 VOLTA PRO MENU
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								reiniciaJogo();
								musica_single.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();
								Pausa = false;
							}
							//BOTAO 3 SAI DO JOGO
							if(x > 295 && x < 520 && y > 375 && y < 405){
								reiniciaJogo();
								Funcoes.fecharJogo();
							}
						}
						
						if(MenuVenceu && CenarioInicio.jogaFree){
							//BOTAO 1 COMECA DE NOVO
							if(x > 295 && x < 520 && y > 280 && y < 310){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								reiniciaFase();
							}
							//BOTAO 2 VOLTA PRO MENU
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								
								musica_single.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();
								CenarioInicio.jogaFree = false;
							}
							//BOTAO 3 SAI DO JOGO
							if(x > 295 && x < 520 && y > 375 && y < 405){
								Funcoes.fecharJogo();
							}
						}
						
						if(MenuPerdeu){
							//BOTAO 1 COMECA DE NOVO 
							if(x > 295 && x < 520 && y > 280 && y < 310){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								reiniciaFase();
							}
							
							//BOTAO 2 COMO JOGAR
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioComoJogar);
								musica_single.pausar();
								CenarioComoJogar.musica_como_jogar.recomeca();
								CenarioComoJogar.musica_como_jogar.retomar();
							}
							
							//BOTAO 3 VOLTA PRO MENU
							if(x > 295 && x < 520 && y > 375 && y < 405){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								reiniciaFase();
								musica_single.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();
							}
						}
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

				});


		//configura os listeners de teclado
		this.addKeyListener(
				new KeyListener(){
					@Override
					public void keyPressed(KeyEvent evento){

						if(!Venceu && !Perdeu)
							//PAUSA DESPAUSA
							if(evento.getKeyCode() == KeyEvent.VK_P || evento.getKeyCode() == KeyEvent.VK_ESCAPE){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								if(Pausa){
									Pausa = false;
									musica_single.retomar();
								}
								else{
									Pausa = true;
									musica_single.pausar();
								}
							}


						if(!Venceu && !Perdeu)
							if (evento.getKeyCode() == KeyEvent.VK_SPACE || evento.getKeyCode() == KeyEvent.VK_W
							|| evento.getKeyCode() == KeyEvent.VK_UP){

								if(!Single.Espaconave.isMorta())
									if(!Single.Espaconave.isVagando()){
										ReprodutorDeSom.getInstancia().reproduzir("propulsao");
									} else{
										ReprodutorDeSom.getInstancia().reproduzir("prop_falhou");
									}


								if(Single.Espaconave.isLancando()){
									Single.Espaconave.setLancando(false);
									Single.Espaconave.setVagando(true);
								}

								if(Single.Espaconave.isOrbitando()){
									Single.Espaconave.setOrbitando(false);
									Single.Espaconave.setVagando(true);
								}
							}

						if(Single.Espaconave.isLancando() && !Perdeu &&!Venceu){


							if (evento.getKeyCode() == KeyEvent.VK_LEFT || evento.getKeyCode() == KeyEvent.VK_A){
								Single.Espaconave.setAndandoEsquerda(true);


							}

							if (evento.getKeyCode() == KeyEvent.VK_RIGHT || evento.getKeyCode() == KeyEvent.VK_D){
								Single.Espaconave.setAndandoDireita(true);

							}

						}
					}

					@Override
					public void keyReleased(KeyEvent evento){

						if (evento.getKeyCode() == KeyEvent.VK_LEFT || evento.getKeyCode() == KeyEvent.VK_A){
							Single.Espaconave.setAndandoEsquerda(false);
						}

						if (evento.getKeyCode() == KeyEvent.VK_RIGHT || evento.getKeyCode() == KeyEvent.VK_D){
							Single.Espaconave.setAndandoDireita(false);
						}
					}

					@Override
					public void keyTyped(KeyEvent evento){
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

		//calcula o novo FPS se passou um segundo desde o último cálculo
		if((System.currentTimeMillis() - tempoAnterior) >= 1000L){
			fps = quantidadeDeChamadas;
			quantidadeDeChamadas = 0;
			tempoAnterior = System.currentTimeMillis();
		}else{
			quantidadeDeChamadas++;
		}
		//desenha o FUNDO
		g2d.drawImage(fundo, 0, 0, null);

		//escreve o FPS
		g2d.drawString("FPS: " +fps, X_FPS, Y_FPS);
		g2d.drawString(tempoFase, X_FPS, Y_FPS - 15);


		// renderiza planeta inicial
		Graphics2D drawPlanetaInicial = (Graphics2D) g;
		drawPlanetaInicial.drawImage(
				imagemPlanetaInicial, 
				(int)Single.PlanetaInicial.getDrawX(), 
				(int)Single.PlanetaInicial.getDrawY(), null);

		Graphics2D drawOrbPlanetaInicial = (Graphics2D) g;
		drawOrbPlanetaInicial.setColor(Color.WHITE);
		drawOrbPlanetaInicial.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				10.0f, tracejado, 0.0f));


		drawOrbPlanetaInicial.drawOval(
				(int)Single.PlanetaInicial.getDrawOrbX(), 
				(int)Single.PlanetaInicial.getDrawOrbY(), 
				(int)Single.PlanetaInicial.getOrbRaio() * 2, 
				(int)Single.PlanetaInicial.getOrbRaio()* 2);

		//Renderiza os planetas
		Graphics2D drawOrbita = (Graphics2D) g;
		drawOrbita.setColor(Color.WHITE);
		drawOrbita.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				10.0f, tracejado, 0.0f));

		for(int i = 0; i < Single.vetorPlanetas.size(); i++){
			Planeta planeta = (Planeta) Single.vetorPlanetas.elementAt(i);
			if(planeta.getNome().equals("final")){
				drawOrbita.setColor(Color.CYAN);
			}
			drawOrbita.drawOval((int)planeta.getDrawOrbX(), 
					(int)planeta.getDrawOrbY(), 
					(int)planeta.getOrbRaio()* 2, 
					(int)planeta.getOrbRaio()* 2);

			g2d.drawImage(exibePlaneta(planeta), 
					(int)planeta.getDrawX(), 
					(int)planeta.getDrawY(), 
					(int)planeta.getRaio() * 2, 
					(int)planeta.getRaio() * 2, null);
			drawOrbita.setColor(Color.white);
		}


		//Renderiza as luas
		for(int i = 0; i < Single.vetorLuas.size(); i++){
			Lua lua = (Lua) Single.vetorLuas.elementAt(i);
			g2d.drawImage(imagemLua, (int)lua.getDrawX(),
					(int)lua.getDrawY(), null);
		}



		//renderiza as espaçonaves
		Graphics2D drawRanger = (Graphics2D) g;


		Espaconave ranger = (Espaconave) Single.Espaconave;

		//TODO RENDERIZA O AUXILIO DE LANÇAMENTO
		if(ranger.isAuxilioRota() && !ranger.isMorta()){

			drawRanger.setColor(Color.CYAN);
			drawRanger.setStroke(new BasicStroke(1.0f,
					BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER,
					10.0f, tracejadoAuxilio, 0.0f));

			theta = ranger.getTheta();
			xnave = (int)ranger.getCentroX();
			ynave = (int)ranger.getCentroY();
			//gira o cenario
			if(ranger.isSentidoHorario()){
				drawRanger.rotate(theta + Math.toRadians(180), xnave, ynave);
				drawRanger.drawLine(xnave, ynave, xnave, ynave - COMPRIMENTO_AUXILIO_ROTA);
				drawRanger.rotate(Math.toRadians(540) - theta, xnave, ynave);
			}
			else{
				drawRanger.rotate(theta, xnave, ynave);
				drawRanger.drawLine(xnave, ynave, xnave, ynave - COMPRIMENTO_AUXILIO_ROTA);
				drawRanger.rotate(Math.toRadians(360) - theta, xnave, ynave);

			}
		}	
		//RENDERIZA A NAVE NO LANÇAMENTO OU ENQUANTO ESTA VAGANDO
		if(ranger.isLancando() || ranger.isVagando()){
			theta = ranger.getTheta();
			xnave = (int) ranger.getCentroX();
			ynave = (int) ranger.getCentroY();
			if(ranger.isSentidoHorario()){
				drawRanger.rotate(theta + Math.toRadians(180), xnave, ynave);
				drawRanger.drawImage(imagemEspaconave, (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
				drawRanger.rotate(Math.toRadians(540) - theta, xnave, ynave);

			}
			else{
				drawRanger.rotate(theta, xnave, ynave);
				drawRanger.drawImage(imagemEspaconave, (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
				drawRanger.rotate(Math.toRadians(360) - theta, xnave, ynave);
			}

		}
		//RENDERIZA A NAVE ENQUANTO ESTA ORBITANDO
		if(ranger.isOrbitando()){
			/** De acordo com o sentido de rotação, é preciso incrementar
			 * 180 ao angulo para que a nave fique na direção correta*/
			theta = ranger.getTheta();
			xnave = (int) ranger.getCentroX();
			ynave = (int) ranger.getCentroY();
			if(ranger.isSentidoHorario()){
				drawRanger.rotate(theta + Math.toRadians(180), xnave, ynave);
				drawRanger.drawImage(imagemEspaconave, (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
				drawRanger.rotate(Math.toRadians(540) - theta, xnave, ynave);
			}
			else{
				drawRanger.rotate(theta, xnave, ynave);
				drawRanger.drawImage(imagemEspaconave, (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
				drawRanger.rotate(Math.toRadians(360) - theta, xnave, ynave);
			}

		}

		// exibir explosao em morte
		if(ranger.isMorta() && !ranger.isLancando() && !ranger.isOrbitando() && !ranger.isVagando()){	
			g2d.drawImage(explosaoSprite, POSICAO_X_EXPLOSAO, POSICAO_Y_EXPLOSAO, POSICAO_X_EXPLOSAO+50, POSICAO_Y_EXPLOSAO+50, 
					sx1, sy1, sx2, sy2, null);
		}


		//RENDERIZA INFORMACOES DO JOGO
		if(!Venceu && !Perdeu){
			g2d.setColor(Color.white);
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			g2d.drawString("Fase " + (faseAtual+1) + ".", 20, 30);
		

			Graphics2D drawVidas = (Graphics2D) g;
			if(Single.Espaconave.getVidasRestantes() == 3)
				drawVidas.drawImage(vidas3, 10, 490, null);
			if(Single.Espaconave.getVidasRestantes() == 2)
				drawVidas.drawImage(vidas2, 10, 490, null);
			if(Single.Espaconave.getVidasRestantes() == 1)
				drawVidas.drawImage(vidas1, 10, 490, null);
		}

		//TODO RENDERIZA MENUS DO JOGO
		if(Pausa){
			Graphics2D menu_pausa = (Graphics2D) g;
			menu_pausa.drawImage(sub_menu, 230, 100, null);

			menu_pausa.setFont(new Font("Serif", Font.BOLD, 40));
			menu_pausa.setColor(Color.white);
			menu_pausa.drawString("Jogo pausado", 295, 200);

			menu_pausa.setFont(new Font("Serif", Font.BOLD, 20));
			menu_pausa.setColor(Color.BLACK);
			menu_pausa.drawString("Resumir", 380, 300);
			menu_pausa.drawString("Como jogar", 365, 350);
			menu_pausa.drawString("Inicio", 390, 398);

			//				menu_pausa.setColor(Color.white);
			//				menu_pausa.setStroke(new BasicStroke(1));
			//				menu_pausa.drawRect(295, 280, 225, 30);
			//				menu_pausa.drawRect(295, 330, 225, 30);
			//				menu_pausa.drawRect(295, 375, 225, 30);


		}

		if((System.currentTimeMillis() - tempoVenceu) >= 500L)
			if(Venceu && !VenceuJogo && !CenarioInicio.jogaFree){
				MenuFase = true;
				Graphics2D menuvenceu = (Graphics2D) g;
				menuvenceu.drawImage(menu_venceu, 230, 100, null);

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 40));
				menuvenceu.setColor(Color.white);
				menuvenceu.drawString("Fase " + (faseAtual+1) + " concluída.", 260, 200);
				
				
				menuvenceu.setFont(new Font("Arial", Font.PLAIN, 15));
				
				if(AtingiuMelhorTempo){
					menuvenceu.drawString("Novo melhor tempo!", 260, 230);
					menuvenceu.drawString("Venceu em " + Funcoes.converteTempo(tempoFaseCorrido) + " segundos.", 260, 250);
					
					
				}
				else{
					menuvenceu.drawString("Melhor tempo: " 
				+ Funcoes.converteTempo(Fases.vetorFases.elementAt(faseAtual).getTime()), 260, 230);
					menuvenceu.drawString("Tempo atual: " + Funcoes.converteTempo(tempoFaseCorrido), 260, 250);
				}
					

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 20));
				menuvenceu.setColor(Color.BLACK);

				menuvenceu.drawString("Próxima fase", 360, 300);
				menuvenceu.drawString("Inicio", 385, 350);
				menuvenceu.drawString("Sair", 395, 398);
			}

		if((System.currentTimeMillis() - tempoVenceu) >= 500L)
			if(VenceuJogo){
				MenuVenceu = true;
				Graphics2D menuvenceu = (Graphics2D) g;
				menuvenceu.drawImage(menu_venceu, 230, 100, null);

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 40));
				menuvenceu.setColor(Color.white);
				menuvenceu.drawString("Você Venceu!", 290, 200);
				
				menuvenceu.setFont(new Font("Arial", Font.PLAIN, 15));
				
				if(AtingiuMelhorTempo){
					menuvenceu.drawString("Novo melhor tempo!", 260, 230);
					menuvenceu.drawString("Venceu em " + Funcoes.converteTempo(tempoFaseCorrido) + " segundos.", 260, 250);
					
					
				}
				else{
					menuvenceu.drawString("Melhor tempo: " 
				+ Funcoes.converteTempo(Fases.vetorFases.elementAt(faseAtual).getTime()), 260, 230);
					menuvenceu.drawString("Tempo atual: " + Funcoes.converteTempo(tempoFaseCorrido), 260, 250);
				}

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 20));
				menuvenceu.setColor(Color.BLACK);
				menuvenceu.drawString("Jogar novamente", 340, 300);
				menuvenceu.drawString("Inicio", 390, 350);
				menuvenceu.drawString("Sair", 395, 398);
			}
		
		if((System.currentTimeMillis() - tempoVenceu) >= 500L)
			if(Venceu && CenarioInicio.jogaFree){
				MenuVenceu = true;
				Graphics2D menuvenceu = (Graphics2D) g;
				menuvenceu.drawImage(menu_venceu_2, 230, 100, null);

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 40));
				menuvenceu.setColor(Color.white);
				menuvenceu.drawString("Você Venceu!", 290, 200);
				
				menuvenceu.setFont(new Font("Arial", Font.PLAIN, 15));
				
				if(AtingiuMelhorTempo){
					menuvenceu.drawString("Novo melhor tempo!", 260, 230);
					menuvenceu.drawString("Venceu em " + Funcoes.converteTempo(tempoFaseCorrido) + " segundos.", 260, 250);
					
					
				}
				else{
					menuvenceu.drawString("Melhor tempo: " 
				+ Funcoes.converteTempo(Fases.vetorFases.elementAt(faseAtual).getTime()), 260, 230);
					menuvenceu.drawString("Tempo atual: " + Funcoes.converteTempo(tempoFaseCorrido), 260, 250);
				}

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 20));
				menuvenceu.setColor(Color.BLACK);
				menuvenceu.drawString("Jogar novamente", 340, 300);
				menuvenceu.drawString("Inicio", 390, 350);
				menuvenceu.drawString("Sair", 395, 398);
			}

		if(System.currentTimeMillis() - tempoPerdeu >= 1000L)
			if(Perdeu){
				MenuPerdeu = true;
				Graphics2D menuperdeu = (Graphics2D) g;
				menuperdeu.drawImage(menu_perdeu, 230, 100, null);

				menuperdeu.setFont(new Font("Serif", Font.BOLD, 40));
				menuperdeu.setColor(Color.white);
				menuperdeu.drawString("Que pena!", 310, 200);
				menuperdeu.setFont(new Font("Arial", Font.PLAIN, 20));
				menuperdeu.drawString("Você não atingiu o objetivo.", 290, 230);
				
				

				menuperdeu.setFont(new Font("Serif", Font.BOLD, 20));
				menuperdeu.setColor(Color.BLACK);
				menuperdeu.drawString("Tentar novamente", 330, 300);
				menuperdeu.drawString("Como jogar", 365, 350);
				menuperdeu.drawString("Inicio", 390, 398);
			}

		


		Toolkit.getDefaultToolkit().sync(); /* garante atualização da tela
        nos variados sistemas de janela */
		g.dispose(); /* libera os recursos gráficos */


	}


	/**CICLO DO JOGO*/
	private class Ciclo extends TimerTask{

		//método com animações e lógica do jogo
		//será invocado no intervalo definido para invocação da tarefa



		@Override
		public void run(){


			//TODO tempo separado pa modi comparar melhor tempo de uma fase com o tempo ganho
			if(!Venceu && !Perdeu && !Pausa){
				tempoFaseCorrido = System.currentTimeMillis() - tempoInicialFase;
				tempoFase = Funcoes.converteTempo(tempoFaseCorrido);
			}
			/**ATUALIZA SPRITE EXPLOSAO*/


			if(sx1 < LARGURA_SPRITE - 100){
				sx1 += DESLOCAMENTO_X_EXPLOSAO;
				sx2 += DESLOCAMENTO_X_EXPLOSAO;
			}
			else{
				sx1 = X1_EXPLOSAO;
				sx2 = X2_EXPLOSAO;

				sy1 += DESLOCAMENTO_Y_EXPLOSAO;
				sy2 += DESLOCAMENTO_Y_EXPLOSAO; 	
			}


			//SE AS VIDAS ACABAM O JOGO TERMINA
			if(!Perdeu)
				if(Single.Espaconave.getVidasRestantes() == 0){
					Perdeu = true;
					tempoPerdeu = System.currentTimeMillis();

				}


			/*Atualiza movimento das luas*/
			if(!Pausa)
				for(int i = 0; i < Single.vetorLuas.size(); i++){
					Lua lua = Single.vetorLuas.elementAt(i);
					lua.setMovimento();
				}

			/**movimenta o planeta*/
			//        	Vetores.vetorPlanetas.elementAt(1).setCentroX();


			/* atualiza movimento da espaconave */

			//se a nave está lançando usa o método setLancamento()
			if(Single.Espaconave.isLancando()){

				Single.Espaconave.setLancamento(
						Single.PlanetaInicial);
			}

			//MOVIMENTA AS NAVES NO LANÇAMENTO
			if(Single.Espaconave.isAndandoDireita()){
				if(Single.Espaconave.getAlpha() <= Math.toRadians(310)){
					Single.Espaconave.incrementaAlpha();
				}

			}
			if(Single.Espaconave.isAndandoEsquerda()){
				if(Single.Espaconave.getAlpha() >= Math.toRadians(230)){
					Single.Espaconave.decrementaAlpha();
				}

			}


			//se a nave está vagando usa o método setMovimentoRet()
			if(!Pausa)
				if(Single.Espaconave.isVagando()){
					Single.Espaconave.setMovimentoRet();
				}

			//se a nave está vagando mas esta em rota de colisao com planeta
			if(Single.Espaconave.isRotaColisao()){
				if(Funcoes.testaColisaoPlaneta(planetaColidir, Single.Espaconave) 
						&& !Single.Espaconave.isMorta()){
					Single.Espaconave.setMorta();
					setaExplosao();
					Single.Espaconave.setVagando(false);

				}

			}
			//se a nave esta vagando testa colisao com luas
			if(Single.Espaconave.isVagando()){
				Espaconave ranger = (Espaconave) Single.Espaconave;
				for(int j = 0; j < Single.vetorLuas.size(); j++){
					Lua lua = Single.vetorLuas.elementAt(j);
					if(Funcoes.testaColisao(lua, ranger) && !ranger.isMorta()){
						Single.Espaconave.setMorta();
						setaExplosao();
						Single.Espaconave.setVagando(false);

					}
				}

			}			
			//se a nave esta vagando é testada a entrada em orbita aos planetas
			if(Single.Espaconave.isVagando() && !Single.Espaconave.isRotaColisao()){
				Espaconave ranger = (Espaconave) Single.Espaconave;
				for(int j = 0; j < Single.vetorPlanetas.size(); j++){
					Planeta planeta = (Planeta) Single.vetorPlanetas.elementAt(j);


					//TESTA ENTRADA NA ORBITA
					if(Funcoes.testaColisao(planeta, ranger)){

						//SE A NAVE TOCOU NA ORBITA COM ANGULO DE COLISAO, ENTAO COLIDIR
						if(Funcoes.colideOrbita(ranger, planeta)){
							Single.Espaconave.setRotaColisao(true);
							planetaColidir = planeta;
							break;
						}

						//TODO VENCEU O JOGO
						//TESTA SE O PLANETA COLIDIDO É O PLANETA FINAL
						if(!Venceu)
							if(Single.vetorPlanetas.elementAt(j).getNome().equals("final")){
								Venceu = true;
								// seta atributos da fase: vencida, jogar livre, melhor tempo
								Fases.vetorFases.elementAt(faseAtual).setFaseVencida();
								Fases.vetorFases.elementAt(faseAtual).setFaseJogarLivre();
								if(Fases.vetorFases.elementAt(faseAtual).setMelhorTempo(tempoFaseCorrido)){
									AtingiuMelhorTempo = true;
								}
								
								// fase atual é um indice, e começa em zero. precisa ser incrementado
								// para comparação com o tamanho do vetor fases.
								if((faseAtual + 1) == Fases.vetorFases.size())
									VenceuJogo = true;
								tempoVenceu = System.currentTimeMillis();
								ReprodutorDeSom.getInstancia().reproduzir("venceu");
							}
						ranger.setVagando(false);
						ranger.setOrbitando(true);
						planetaOrbitado = planeta;

						//TODO EASTER EGG
						if(planetaAnterior.equals(planetaOrbitado.getNome())){
							ReprodutorDeSom.getInstancia().reproduzir("auxilio");
							if(!ranger.isAuxilioRota()){
								Single.Espaconave.setAuxilioRota(true);
							}
							else{
								COMPRIMENTO_AUXILIO_ROTA += 50;
							}
						}
						planetaAnterior = planetaAtual;
						planetaAtual = planetaOrbitado.getNome();

						//DETERMINA EM QUE PONTO O MOVIMENTO CIRCULAR DEVE COMEÇAR
						ranger.setTheta(Funcoes.anguloCentral(ranger, planeta));
						//DETERMINA EM QUE SENTIDO GIRAR
						if(Funcoes.sentidoH(ranger, planeta)){
							Single.Espaconave.setSentidoHorario(true);
						}
						else{
							Single.Espaconave.setSentidoHorario(false);
						}

						ranger.setKZero();

						break;
					}
				}

			}
			/* quando a nave está orbitando, o movimento é setado para
        	 orbitar o planeta atual */
			if(!Pausa)
				if(Single.Espaconave.isOrbitando()){
					Single.Espaconave.setMovimentoCir(planetaOrbitado);
				}

			/* quando tá orbitando testa colisao com as luas */

			if(Single.Espaconave.isOrbitando()){
				Espaconave ranger = Single.Espaconave;

				for(int j = 0; j < Single.vetorLuas.size(); j++){
					Lua lua = Single.vetorLuas.elementAt(j);

					if(Funcoes.testaColisao(lua, ranger) && !ranger.isMorta()){
						Single.Espaconave.setMorta();
						setaExplosao();
						Single.Espaconave.setOrbitando(false);

					}
				}

			}



			//quando a nave esta morta o tempo é contado e ela é resetada
			if(Single.Espaconave.isMorta()){
				Single.Espaconave.reviveEspaconave();
			}

			repaint(); /* atualiza as coisas na tela */


		}


	}



	private void setaExplosao(){
		POSICAO_X_EXPLOSAO = (int) (Single.Espaconave.getCentroX() - 25);
		POSICAO_Y_EXPLOSAO = (int) (Single.Espaconave.getCentroY() - 25);
		sx1 = 0;
		sy1 = 0;
		sx2 = 100;
		sy2 = 100;
		
		ReprodutorDeSom.getInstancia().reproduzir("explosao");

	}

	public static void reiniciaJogo(){
		Fases.resetVencidas();
		setFaseAtual();
		

		Single.Espaconave.setAuxilioRota(false);

	}

	public static void reiniciaFase(){
//		Fases.exibeStatusFaseAtual(faseAtual);
		Single.Espaconave.resetaEspaconave();
		Single.Espaconave.resetVidasRestantes();

		AtingiuMelhorTempo = false;
		Perdeu = false;
		Venceu = false;
		VenceuJogo = false;
		MenuVenceu = false;
		MenuPerdeu = false;
		MenuFase = false;


		planetaAnterior = "string1";
		planetaAtual = "string2";

		tempoInicialFase = System.currentTimeMillis();

	}

	public static boolean isPausa() {
		return Pausa;
	}

	private Image exibePlaneta(Planeta planeta){

		if(planeta.getNome().equals("final"))
			imagem = planetaFinal;
		if(planeta.getNome().equals("planeta1"))
			imagem = planeta1;
		if(planeta.getNome().equals("planeta2"))
			imagem = planeta2;
		if(planeta.getNome().equals("planeta3"))
			imagem = planeta3;
		if(planeta.getNome().equals("planeta4"))
			imagem = planeta4;
		if(planeta.getNome().equals("planeta5"))
			imagem = planeta5;
		if(planeta.getNome().equals("planeta6"))
			imagem = planeta6;
		return imagem;
	}

	public static int getFaseAtual() {
		return faseAtual;
	}

	public static void setFaseAtual() {
		faseAtual = Fases.selecionaFaseNaoVencida();
		Single.selecionaFase(faseAtual);
		Single.Espaconave.resetaEspaconave();
		reiniciaFase();
	}
	
	public static void setFaseAtual(int indice) {
		faseAtual = indice;
		Single.selecionaFase(faseAtual);
		Single.Espaconave.resetaEspaconave();
		reiniciaFase();
	}

}

