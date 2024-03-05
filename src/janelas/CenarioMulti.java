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

import estruturas.Multi;
import objetos.Espaconave;
import objetos.Lua;
import objetos.Planeta;
import objetos.PlanetaInicial;
import sons.ReprodutorDeMusica3;
import sons.ReprodutorDeSom;
import util.Funcoes;


public class CenarioMulti extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3023445802850613990L;

	private static final int ATRASO_INICIAL = 0;
	private static final int INTERVALO = 10;
	private static final int X_FPS = 730;
	private static final int Y_FPS = 510;

	private static final int COMPRIMENTO_AUXILIO_ROTA = 230;

	public static ReprodutorDeMusica3 musica_multi = null;


	private static int POSICAO_X_EXPLOSAO = 300;
	private static int POSICAO_Y_EXPLOSAO = 300;

	private static final int DESLOCAMENTO_X_EXPLOSAO = 100;
	private static final int DESLOCAMENTO_Y_EXPLOSAO = 100;
	private static final int X1_EXPLOSAO = 0;
	private static final int Y1_EXPLOSAO = 0;
	private static final int X2_EXPLOSAO = 100;
	private static final int Y2_EXPLOSAO = 100;
	private static final int LARGURA_SPRITE = 900;

	private static final float[] tracejadoAuxilio = {5.0f, 20.0f};
	


	private int sx1 = X1_EXPLOSAO, sy1 = Y1_EXPLOSAO,
			sx2 = X2_EXPLOSAO, sy2 = Y2_EXPLOSAO;

	private float[] tracejado = {5.0f};

	private static long tempoVenceu = 0;

	private static long tempoInicialFase = System.currentTimeMillis(), tempoCorridoFase = 0;
	private static String tempoFase;
	private static long tempoAnterior = System.currentTimeMillis();
	private static int quantidadeDeChamadas = 0;
	private static int fps = 0;

	private Image fundo = null, sub_menu = null, menu_venceu_azul = null, menu_venceu_vermelho = null;
	private Image explosaoSprite = null;
	
	private Image imagemEspaconave = null,imagemEspaconave2 = null, imagemLua = null, imagemPlanetaInicial = null, 
			planetaFinal = null, planeta1 = null, planeta2 = null, 
			planeta3 = null, planeta4 = null, planeta5 = null, 
			planeta6 = null;
	
	private Image imagem = null;
	private Timer timer;



	private static boolean Venceu = false;
	private static boolean MenuVenceu = false;
	private boolean Pausa = false;
	private static boolean PlayerVermelhoVenceu = false;
	private static boolean PlayerAzulVenceu = false;


	//POSICOES DO MOUSE
	private int x, y;
	
	
	private static double theta;
	private static int xnave, ynave;

	public static String[] planetaAnterior = {"string1", "string2"};
	public static String[] planetaAtual = {"string3", "string4"};



	@SuppressWarnings("deprecation")
	public CenarioMulti(){
		//carga da imagem de fundo em um ícone (primitivo da imagem)
		fundo = Toolkit.getDefaultToolkit().getImage("imagens/milkway.png");
		fundo = fundo.getScaledInstance(
				Janela.LARGURA, Janela.ALTURA, Image.SCALE_DEFAULT);

		explosaoSprite = Toolkit.getDefaultToolkit().getImage("imagens/explosaosprite.png");

		sub_menu = Toolkit.getDefaultToolkit().getImage("imagens/menu-pausa.png");
		menu_venceu_azul = Toolkit.getDefaultToolkit().getImage("imagens/menu-venceu.png");
		menu_venceu_vermelho = Toolkit.getDefaultToolkit().getImage("imagens/menu-venceu-2.png");

		imagemEspaconave = Toolkit.getDefaultToolkit().getImage("imagens/ranger1.png");
		imagemEspaconave = imagemEspaconave.getScaledInstance(20, 30, Image.SCALE_DEFAULT);
		
		imagemEspaconave2 = Toolkit.getDefaultToolkit().getImage("imagens/ranger2.png");
		imagemEspaconave2 = imagemEspaconave2.getScaledInstance(20, 30, Image.SCALE_DEFAULT);
		
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
		
		musica_multi = new ReprodutorDeMusica3("sons/musica-3-loop.wav");
		musica_multi.start();
		musica_multi.suspend();

		//configuração do timer
		timer = new Timer();
		timer.scheduleAtFixedRate(new Ciclo(), ATRASO_INICIAL, INTERVALO);

		//configura os listeners do mouse
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
								musica_multi.resume();
							}
							//BOTAO 2 COMECA DE NOVO
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								reiniciaJogo();
								Pausa = false;
								musica_multi.resume();
							}
							//BOTAO 3 VOLTA PRO MENU
							if(x > 295 && x < 520 && y > 375 && y < 405){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								reiniciaJogo();
								musica_multi.suspend();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.resume();
								Pausa = false;
								Multi.vetorEspaconaves.elementAt(0).setAuxilioRota(false);
								Multi.vetorEspaconaves.elementAt(1).setAuxilioRota(false);
							}
						}

						if(MenuVenceu){
							//BOTAO 1 COMECA DE NOVO
							if(x > 295 && x < 520 && y > 280 && y < 310){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								reiniciaJogo();
							}
							//BOTAO 2 VOLTA PRO INICIO
							if(x > 295 && x < 520 && y > 330 && y < 360){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);
								reiniciaJogo();
								musica_multi.suspend();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.resume();
								Multi.vetorEspaconaves.elementAt(0).setAuxilioRota(false);
								Multi.vetorEspaconaves.elementAt(1).setAuxilioRota(false);
							}
							//BOTAO 3 SAI DO JOGO
							if(x > 295 && x < 520 && y > 375 && y < 405){
								Funcoes.fecharJogo();
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

						if(!Venceu)
							if(evento.getKeyCode() == KeyEvent.VK_P || evento.getKeyCode() == KeyEvent.VK_ESCAPE){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								if(Pausa){
									Pausa = false;
									musica_multi.resume();
								}
								else{
									Pausa = true;
									musica_multi.suspend();
								}
							}

						if(Multi.vetorEspaconaves.elementAt(1).isLancando() && !Venceu){


							if (evento.getKeyCode() == KeyEvent.VK_LEFT){
								Multi.vetorEspaconaves.elementAt(1).setAndandoEsquerda(true);
							}

							if (evento.getKeyCode() == KeyEvent.VK_RIGHT){
								Multi.vetorEspaconaves.elementAt(1).setAndandoDireita(true);
							}

						}

						if(Multi.vetorEspaconaves.elementAt(0).isLancando() && !Venceu){


							if (evento.getKeyCode() == KeyEvent.VK_A){
								Multi.vetorEspaconaves.elementAt(0).setAndandoEsquerda(true);
							}

							if (evento.getKeyCode() == KeyEvent.VK_D){
								Multi.vetorEspaconaves.elementAt(0).setAndandoDireita(true);
							}

						}

						if(!Venceu){
							if (evento.getKeyCode() == KeyEvent.VK_UP){

								if(!Multi.vetorEspaconaves.elementAt(1).isMorta())
								if(!Multi.vetorEspaconaves.elementAt(1).isVagando()){
									ReprodutorDeSom.getInstancia().reproduzir("propulsao");
								} else{
									ReprodutorDeSom.getInstancia().reproduzir("prop_falhou");
								}

								if(Multi.vetorEspaconaves.elementAt(1).isLancando()){
									Multi.vetorEspaconaves.elementAt(1).setLancando(false);
									Multi.vetorEspaconaves.elementAt(1).setVagando(true);
								}

								if(Multi.vetorEspaconaves.elementAt(1).isOrbitando()){
									Multi.vetorEspaconaves.elementAt(1).setOrbitando(false);
									Multi.vetorEspaconaves.elementAt(1).setVagando(true);
								}
							}
							if (evento.getKeyCode() == KeyEvent.VK_W){
								if(!Multi.vetorEspaconaves.elementAt(0).isMorta())
								if(!Multi.vetorEspaconaves.elementAt(0).isVagando()){
									ReprodutorDeSom.getInstancia().reproduzir("propulsao");
								} else{
									ReprodutorDeSom.getInstancia().reproduzir("prop_falhou");

								}
								if(Multi.vetorEspaconaves.elementAt(0).isLancando()){
									Multi.vetorEspaconaves.elementAt(0).setLancando(false);
									Multi.vetorEspaconaves.elementAt(0).setVagando(true);
								}

								if(Multi.vetorEspaconaves.elementAt(0).isOrbitando()){
									Multi.vetorEspaconaves.elementAt(0).setOrbitando(false);
									Multi.vetorEspaconaves.elementAt(0).setVagando(true);
								}
							}
						}
					}

					@Override
					public void keyReleased(KeyEvent evento){

						if (evento.getKeyCode() == KeyEvent.VK_A){
							Multi.vetorEspaconaves.elementAt(0).setAndandoEsquerda(false);
						}

						if (evento.getKeyCode() == KeyEvent.VK_D){
							Multi.vetorEspaconaves.elementAt(0).setAndandoDireita(false);
						}

						if (evento.getKeyCode() == KeyEvent.VK_LEFT){
							Multi.vetorEspaconaves.elementAt(1).setAndandoEsquerda(false);
						}

						if (evento.getKeyCode() == KeyEvent.VK_RIGHT){
							Multi.vetorEspaconaves.elementAt(1).setAndandoDireita(false);
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

		//desenha as imagens
		g2d.drawImage(fundo, 0, 0, null);
		//escreve o FPS
		g2d.drawString("FPS: " +fps, X_FPS, Y_FPS);
		g2d.drawString(tempoFase, X_FPS, Y_FPS - 15);





		Graphics2D drawPlanetaInicial = (Graphics2D) g;


		Graphics2D drawOrbPlanetaInicial = (Graphics2D) g;
		drawOrbPlanetaInicial.setColor(Color.WHITE);
		drawOrbPlanetaInicial.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				10.0f, tracejado, 0.0f));

		for(int i = 0; i < Multi.vetorPlanetaInicial.size(); i++){
			PlanetaInicial inicial = Multi.vetorPlanetaInicial.elementAt(i);
			drawPlanetaInicial.drawImage(imagemPlanetaInicial, (int)inicial.getDrawX(), (int)inicial.getDrawY(), null);
			drawOrbPlanetaInicial.drawOval((int)inicial.getDrawOrbX(), (int)inicial.getDrawOrbY(), 
					(int)inicial.getOrbRaio() * 2, (int)inicial.getOrbRaio()* 2);

		}

		Graphics2D drawOrbita = (Graphics2D) g;
		drawOrbita.setColor(Color.WHITE);
		drawOrbita.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, tracejado, 0.0f));


		//Renderiza os planetas
		for(int i = 0; i < Multi.vetorPlanetas.size(); i++){
			Planeta planeta = (Planeta) Multi.vetorPlanetas.elementAt(i);
			if(planeta.getNome().equals("final")){
				drawOrbita.setColor(Color.CYAN);
			}
			drawOrbita.drawOval((int)planeta.getDrawOrbX(), (int)planeta.getDrawOrbY(), 
					(int)planeta.getOrbRaio()*2, (int)planeta.getOrbRaio()*2);

			g2d.drawImage(exibePlaneta(planeta), (int)planeta.getDrawX(), (int)planeta.getDrawY(), 
					(int)planeta.getLargura(), (int)planeta.getAltura(), null);
		}


		//Renderiza as luas
		for(int i = 0; i < Multi.vetorLuas.size(); i++){
			Lua lua = (Lua) Multi.vetorLuas.elementAt(i);
			g2d.drawImage(imagemLua, (int)lua.getDrawX(),
					(int)lua.getDrawY(), null);
		}




		Graphics2D drawRanger = (Graphics2D) g;
		//renderiza as espaçonaves 
		for(int i = 0; i < 2; i++){

			Espaconave ranger = (Espaconave) Multi.vetorEspaconaves.elementAt(i);

			/**************************************************/
			//RENDERIZA O AUXILIO DE LANÇAMENTO
			if(ranger.isAuxilioRota() && !ranger.isMorta()){
				
				drawRanger.setColor(Color.CYAN);
				drawRanger.setStroke(new BasicStroke(1.0f,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						10.0f, tracejadoAuxilio, 0.0f));
				theta = ranger.getTheta();
				xnave = (int) ranger.getCentroX();
				ynave = (int) ranger.getCentroY();
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
			if(ranger.isLancando() || ranger.isVagando()){
				theta = ranger.getTheta();
				xnave = (int) ranger.getCentroX();
				ynave = (int) ranger.getCentroY();
				if(ranger.isSentidoHorario()){
					drawRanger.rotate(theta + Math.toRadians(180), xnave, ynave);
					drawRanger.drawImage(exibeEspaconave(i), (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
					drawRanger.rotate(Math.toRadians(540) - theta, xnave, ynave);
				}
				else{
					drawRanger.rotate(theta, xnave, ynave);
					drawRanger.drawImage(exibeEspaconave(i), (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
					drawRanger.rotate(Math.toRadians(360) - theta, xnave, ynave);
				}

			}
			if(ranger.isOrbitando()){
				/** De acordo com o sentido de rotação, é preciso incrementar
				 * 180 ao angulo para que a nave fique na direção correta*/
				theta = ranger.getTheta();
				xnave = (int) ranger.getCentroX();
				ynave = (int) ranger.getCentroY();
				if(ranger.isSentidoHorario()){
					drawRanger.rotate(theta + Math.toRadians(180), xnave, ynave);
					drawRanger.drawImage(exibeEspaconave(i), (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
					drawRanger.rotate(Math.toRadians(540) - theta, xnave, ynave);
				}
				else{
					drawRanger.rotate(theta, xnave, ynave);
					drawRanger.drawImage(exibeEspaconave(i), (int)ranger.getDrawX(), (int)ranger.getDrawY(), null);
					drawRanger.rotate(Math.toRadians(360) - theta, xnave, ynave);
				}
			}

			if(ranger.isMorta() ){
				g2d.drawImage(explosaoSprite, POSICAO_X_EXPLOSAO, POSICAO_Y_EXPLOSAO, POSICAO_X_EXPLOSAO + 50, POSICAO_Y_EXPLOSAO + 50, 
						sx1, sy1, sx2, sy2, null);
			}
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
			menu_pausa.drawString("Reiniciar", 377, 350);
			menu_pausa.drawString("Inicio", 390, 398);

//			menu_pausa.setColor(Color.white);
//			menu_pausa.setStroke(new BasicStroke(1));
//			menu_pausa.drawRect(295, 280, 225, 30);
//			menu_pausa.drawRect(295, 330, 225, 30);
//			menu_pausa.drawRect(295, 375, 225, 30);


		}

		if((System.currentTimeMillis() - tempoVenceu) >= 500L)
			if(Venceu){
				MenuVenceu = true;
				Graphics2D menuvenceu = (Graphics2D) g;
				menuvenceu.setFont(new Font("Serif", Font.BOLD, 40));
				menuvenceu.setColor(Color.white);
				if(PlayerVermelhoVenceu){
					menuvenceu.drawImage(menu_venceu_vermelho, 230, 100, null);
					menuvenceu.drawString("Jogador vermelho", 255, 180);
					menuvenceu.drawString("Venceu!", 335, 230);
					
				}
				if(PlayerAzulVenceu){
					menuvenceu.drawImage(menu_venceu_azul, 230, 100, null);
				menuvenceu.drawString("Jogador azul", 295, 180);
				menuvenceu.drawString("Venceu!", 335, 230);
				}
				

				menuvenceu.setFont(new Font("Serif", Font.BOLD, 20));
				menuvenceu.setColor(Color.BLACK);
				menuvenceu.drawString("Jogar novamente", 340, 300);
				menuvenceu.drawString("Inicio", 390, 350);
				menuvenceu.drawString("Sair", 395, 398);
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
			if(!Venceu && !Pausa){
				tempoCorridoFase = System.currentTimeMillis() - tempoInicialFase;
				tempoFase = Funcoes.converteTempo(tempoCorridoFase);
			}

			/**ATUALIZA SPRITE DE EXPLOSAO*/
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

			/*Atualiza movimento das luas*/
			if(!Pausa)
				for(int i = 0; i < Multi.vetorLuas.size(); i++){
					Lua lua = Multi.vetorLuas.elementAt(i);
					lua.setMovimento();
				}

			//quando a nave esta morta o tempo é contado e ela é resetada

			for(int i = 0; i < Multi.vetorEspaconaves.size(); i++){

				if(Multi.vetorEspaconaves.elementAt(i).isMorta()){
					Multi.vetorEspaconaves.elementAt(i).reviveEspaconave();
				}
			}


			/**movimenta o planeta*/
			//Vetores.vetorPlanetas.elementAt(1).setCentroX();

			/* atualiza movimento da espaconave */

			//se a nave está lançando usa o método setLancamento()
			if(!Pausa)
				for(int i = 0; i < Multi.vetorEspaconaves.size(); i++){

					if(Multi.vetorEspaconaves.elementAt(i).isLancando()){

						Multi.vetorEspaconaves.elementAt(i).setLancamento(
								Multi.vetorPlanetaInicial.elementAt(i));
					}

					//TODO SETA O MOVIMENTO DA NAVE DURANTE LANCAMENTO ELIMINANDO GHOST
					if(Multi.vetorEspaconaves.elementAt(i).isAndandoDireita()){
						if(Multi.vetorEspaconaves.elementAt(i).getAlpha() <= Math.toRadians(310)){
							Multi.vetorEspaconaves.elementAt(i).incrementaAlpha();
						}
					}
					if(Multi.vetorEspaconaves.elementAt(i).isAndandoEsquerda()){
						if(Multi.vetorEspaconaves.elementAt(i).getAlpha() >= Math.toRadians(230)){
							Multi.vetorEspaconaves.elementAt(i).decrementaAlpha();
						}
					}

					//se a nave está vagando usa o método setMovimentoRet()
					if(Multi.vetorEspaconaves.elementAt(i).isVagando()){
						Multi.vetorEspaconaves.elementAt(i).setMovimentoRet();
					}

					/* quando a nave está orbitando, o movimento é setado para
           	 	orbitar o planeta atual */
					if(Multi.vetorEspaconaves.elementAt(i).isOrbitando()){
						Multi.vetorEspaconaves.elementAt(i).setMovimentoCir(Multi.planetaOrbitado.elementAt(i));
					}

					//se a nave está vagando mas esta em rota de colisao com planeta
					if(Multi.vetorEspaconaves.elementAt(i).isRotaColisao() 
							&& !Multi.vetorEspaconaves.elementAt(i).isMorta()){
						if(Funcoes.testaColisaoPlaneta(
								Multi.planetaColidir.elementAt(i), 
								Multi.vetorEspaconaves.elementAt(i))){
							Multi.vetorEspaconaves.elementAt(i).setMorta();
							setaExplosao(i);
							Multi.vetorEspaconaves.elementAt(i).setVagando(false);
						}
					}
					//se a nave esta vagando testa colisao com luas
					if(Multi.vetorEspaconaves.elementAt(i).isVagando() && !Multi.vetorEspaconaves.elementAt(i).isMorta()){

						Espaconave ranger = (Espaconave) Multi.vetorEspaconaves.elementAt(i);
						for(int j = 0; j < Multi.vetorLuas.size(); j++){

							Lua lua = Multi.vetorLuas.elementAt(j);
							if(Funcoes.testaColisao(lua, ranger)){
								Multi.vetorEspaconaves.elementAt(i).setMorta();
								setaExplosao(i);
								Multi.vetorEspaconaves.elementAt(i).setVagando(false);
							}
						}

					}			
					//se a nave esta vagando é testada a entrada em orbita aos planetas
					if(!Venceu)
					if(Multi.vetorEspaconaves.elementAt(i).isVagando() && 
							!Multi.vetorEspaconaves.elementAt(i).isRotaColisao()){

						Espaconave ranger = (Espaconave) Multi.vetorEspaconaves.elementAt(i);
						for(int j = 0; j < Multi.vetorPlanetas.size(); j++){
							Planeta planeta = (Planeta) Multi.vetorPlanetas.elementAt(j);


							//testa entrada na orbita, e não a colisao ao planeta
							if(Funcoes.testaColisao(planeta, ranger)){
								//TODO
								if(Funcoes.colideOrbita(ranger, planeta)){
									Multi.vetorEspaconaves.elementAt(i).setRotaColisao(true);
									Multi.planetaColidir.removeElementAt(i);
									Multi.planetaColidir.insertElementAt(planeta, i);
									break;
								}


								//testa se o planeta colidido é o planeta final
								if(Multi.vetorPlanetas.elementAt(j).getNome().equals("final")){
									Venceu = true;
									if(i == 0){
										PlayerVermelhoVenceu = true;
									}
									else{
										PlayerAzulVenceu = true;
									}
									tempoVenceu = System.currentTimeMillis();
									ReprodutorDeSom.getInstancia().reproduzir("venceu");
								}

								ranger.setVagando(false);
								ranger.setOrbitando(true);
								Multi.planetaOrbitado.removeElementAt(i);
								Multi.planetaOrbitado.insertElementAt(planeta, i); 

								//TODO EASTER EGG
								if(planetaAnterior[i].equals(Multi.planetaOrbitado.elementAt(i).getNome())){
									ReprodutorDeSom.getInstancia().reproduzir("auxilio");
									Multi.vetorEspaconaves.elementAt(i).setAuxilioRota(true);
								}
								planetaAnterior[i] = planetaAtual[i];
								planetaAtual[i] = Multi.planetaOrbitado.elementAt(i).getNome();



								//determina em que ponto o movimento circular deve começar
								ranger.setTheta(Funcoes.anguloCentral(ranger, planeta));
								//determina em que sentido girar
								if(Funcoes.sentidoH(ranger, planeta)){
									Multi.vetorEspaconaves.elementAt(i).setSentidoHorario(true);
								}
								else{
									Multi.vetorEspaconaves.elementAt(i).setSentidoHorario(false);
								}

								ranger.setKZero();

								break;
							}



						}



					}


					/* quando tá orbitando testa colisao com as luas rsssss
					 * POR ENQUANTO A NAVE SÓ PARA AHUIAHUIAUIHA*/
					if(Multi.vetorEspaconaves.elementAt(i).isOrbitando()){
						Espaconave ranger = Multi.vetorEspaconaves.elementAt(i);

						for(int j = 0; j < Multi.vetorLuas.size(); j++){
							Lua lua = Multi.vetorLuas.elementAt(j);

							if(Funcoes.testaColisao(lua, ranger) && !ranger.isMorta()){
								Multi.vetorEspaconaves.elementAt(i).setOrbitando(false);
								Multi.vetorEspaconaves.elementAt(i).setMorta();
								setaExplosao(i);
							}
						}

					}

					

					for(int y = 0; y < Multi.vetorEspaconaves.size(); y++){

						if(Multi.vetorEspaconaves.elementAt(y).isMorta()){
							Multi.vetorEspaconaves.elementAt(y).reviveEspaconave();
						}
					}
				}
			
			repaint(); /* atualiza as coisas na tela */

		}
	}

	//TODO as duas espaçonaves usam a mesma explosao
	private void setaExplosao(int i){
		POSICAO_X_EXPLOSAO = (int) (Multi.vetorEspaconaves.elementAt(i).getCentroX() - 25);
		POSICAO_Y_EXPLOSAO = (int) (Multi.vetorEspaconaves.elementAt(i).getCentroY() - 25);
		sx1 = 0;
		sy1 = 0;
		sx2 = 100;
		sy2 = 100;
		ReprodutorDeSom.getInstancia().reproduzir("explosao");

	}


	public static void reiniciaJogo(){
		Multi.vetorEspaconaves.elementAt(0).resetaEspaconave();
		Multi.vetorEspaconaves.elementAt(1).resetaEspaconave();
		Venceu = false;
		MenuVenceu = false;
		PlayerVermelhoVenceu = false;
		PlayerAzulVenceu = false;
		CenarioMulti.planetaAnterior[0] = "string1";
		CenarioMulti.planetaAnterior[1] = "string2";
		CenarioMulti.planetaAtual[0] = "string3";
		CenarioMulti.planetaAtual[1] = "string4";
		tempoCorridoFase = 0;
		tempoInicialFase = System.currentTimeMillis();
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

	private Image exibeEspaconave(int indice){
		Image imagem = null;
		if(indice == 0)
			imagem = imagemEspaconave;
		else
			imagem = imagemEspaconave2;
			
		return imagem;
	}

}

