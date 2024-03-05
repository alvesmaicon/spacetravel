package editordefases;

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
import java.util.Vector;

import javax.swing.JPanel;

import janelas.Janela;
import objetos.Espaconave;
import objetos.Lua;
import objetos.Planeta;
import objetos.PlanetaInicial;
import sons.ReprodutorDeSom;
import util.Funcoes;


public class CenarioEdicao extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4494864457834575601L;

	private static final float[] tracejado = {5.0f};

	private static final int ATRASO_INICIAL = 0;
	private static final int INTERVALO = 10;
	private Timer timer;

	public static Vector<Lua> vetorLuas = new Vector<Lua>();


	private Espaconave espaconave;
	private Planeta planetaOrbitado;
	private Planeta planetaColidir;
	public static PlanetaInicial planetaInicial;


	private Image imagemEspaconave = null, imagemLua = null, imagemPlanetaInicial = null, 
			planetaFinal = null, planeta1 = null, planeta2 = null, fundo = null, 
			planeta3 = null, planeta4 = null, planeta5 = null, planeta6 = null;

	private Image imagem = null;
	private Image explosaoSprite = null;

	private static int POSICAO_X_EXPLOSAO = 300;
	private static int POSICAO_Y_EXPLOSAO = 300;

	private static final int DESLOCAMENTO_X_EXPLOSAO = 100;
	private static final int DESLOCAMENTO_Y_EXPLOSAO = 100;
	private static final int X1_EXPLOSAO = 0;
	private static final int Y1_EXPLOSAO = 0;
	private static final int X2_EXPLOSAO = 100;
	private static final int Y2_EXPLOSAO = 100;
	private static final int LARGURA_SPRITE = 900;

	private int sx1 = X1_EXPLOSAO, sy1 = Y1_EXPLOSAO,
			sx2 = X2_EXPLOSAO, sy2 = Y2_EXPLOSAO;

	// auxilio para giro de cenário
	private static double theta;
	private static int xnave, ynave;

	private boolean Venceu = false;
	private long tempoVenceu = 0;

	public CenarioEdicao() {

		planetaInicial = new PlanetaInicial("inicial", 300, 595, 100);
		espaconave = new Espaconave("espaconave", planetaInicial);


		fundo = Toolkit.getDefaultToolkit().getImage("imagens/milkway.png");
		fundo = fundo.getScaledInstance(
				Janela.LARGURA, Janela.ALTURA, Image.SCALE_DEFAULT);

		imagemEspaconave = Toolkit.getDefaultToolkit().getImage("imagens/ranger.png");
		imagemEspaconave = imagemEspaconave.getScaledInstance(20, 30, Image.SCALE_DEFAULT);

		imagemPlanetaInicial = Toolkit.getDefaultToolkit().getImage("imagens/inicial.png");

		explosaoSprite = Toolkit.getDefaultToolkit().getImage("imagens/explosaosprite.png");

		imagemLua = Toolkit.getDefaultToolkit().getImage("imagens/lua.png");
		imagemLua = imagemLua.getScaledInstance(30, 30, Image.SCALE_DEFAULT);

		planetaFinal = Toolkit.getDefaultToolkit().getImage("imagens/final.png");
		planeta1 = Toolkit.getDefaultToolkit().getImage("imagens/planeta1.png");
		planeta2 = Toolkit.getDefaultToolkit().getImage("imagens/planeta2.png");
		planeta3 = Toolkit.getDefaultToolkit().getImage("imagens/planeta3.png");
		planeta4 = Toolkit.getDefaultToolkit().getImage("imagens/planeta4.png");
		planeta5 = Toolkit.getDefaultToolkit().getImage("imagens/planeta5.png");
		planeta6 = Toolkit.getDefaultToolkit().getImage("imagens/planeta6.png");

		timer = new Timer();
		timer.scheduleAtFixedRate(new Ciclo(), ATRASO_INICIAL, INTERVALO);



		this.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent evento) {

						// move planeta inicial
						if(EditaFase.movendoPlanetaInicial){
							EditaFase.fase.setPlanetaInicial(evento.getX());
							espaconave.setLocalInicial(EditaFase.fase.getPlanetaInicial());
						}

						// seleciona planeta
						if(EditaFase.selecionandoPlaneta){

							for(int i = 0; i < EditaFase.fase.getVetorPlanetas().size(); i++){
								Planeta planeta = (Planeta) EditaFase.fase.getVetorPlanetas().elementAt(i);
								// se o click do mouse for sobre um planeta
								if(Funcoes.distAB(planeta, evento.getX(), evento.getY()) < planeta.getOrbRaio()){
									EditaFase.setPlanetaSelecionado(planeta);

									break;
								}
							}
						}

						// move planeta selecionado
						if(EditaFase.movendoPlaneta){
							movePlaneta(evento.getX(), evento.getY());
						}

						// seleciona lua
						if(EditaFase.selecionandoLua){

							for(int i = 0; i < EditaFase.fase.getVetorLuas().size(); i++){
								Lua  lua = (Lua) EditaFase.fase.getVetorLuas().elementAt(i);
								// se o click do mouse for sobre uma lua
								if(Funcoes.distAB(lua, evento.getX(), evento.getY()) < lua.getRaio()){
									EditaFase.setLuaSelecionada(lua);

									break;
								}
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


						if(!Venceu && !EditaFase.Editando)
							if (evento.getKeyCode() == KeyEvent.VK_SPACE || evento.getKeyCode() == KeyEvent.VK_W || evento.getKeyCode() == KeyEvent.VK_UP){

								if(!espaconave.isMorta())
									if(!espaconave.isVagando()){
										ReprodutorDeSom.getInstancia().reproduzir("propulsao");
									} else{
										ReprodutorDeSom.getInstancia().reproduzir("prop_falhou");
									}

								if(espaconave.isLancando()){
									espaconave.setLancando(false);
									espaconave.setVagando(true);
								}

								if(espaconave.isOrbitando()){
									espaconave.setOrbitando(false);
									espaconave.setVagando(true);
								}
							}

						if(espaconave.isLancando() &&!Venceu && !EditaFase.Editando){
							if (evento.getKeyCode() == KeyEvent.VK_LEFT || evento.getKeyCode() == KeyEvent.VK_A){
								espaconave.setAndandoEsquerda(true);
							}

							if (evento.getKeyCode() == KeyEvent.VK_RIGHT || evento.getKeyCode() == KeyEvent.VK_D){
								espaconave.setAndandoDireita(true);
							}
						}
					}

					@Override
					public void keyReleased(KeyEvent evento) {
						if (evento.getKeyCode() == KeyEvent.VK_LEFT || evento.getKeyCode() == KeyEvent.VK_A){
							espaconave.setAndandoEsquerda(false);
						}

						if (evento.getKeyCode() == KeyEvent.VK_RIGHT || evento.getKeyCode() == KeyEvent.VK_D){
							espaconave.setAndandoDireita(false);
						}

					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

				}
				);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		desenharCoisas(g);

	}

	/** Renderizador do jogo */

	public void desenharCoisas(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(fundo, 0, 0, null);


		// renderiza o planeta Inicial
		Graphics2D drawPlanetaInicial = (Graphics2D) g;
		drawPlanetaInicial.drawImage(
				imagemPlanetaInicial, 
				(int)EditaFase.fase.getPlanetaInicial().getDrawX(), 
				(int)EditaFase.fase.getPlanetaInicial().getDrawY(), null);

		Graphics2D drawOrbPlanetaInicial = (Graphics2D) g;
		drawOrbPlanetaInicial.setColor(Color.WHITE);
		drawOrbPlanetaInicial.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				10.0f, tracejado, 0.0f));

		drawOrbPlanetaInicial.drawOval(
				(int)EditaFase.fase.getPlanetaInicial().getDrawOrbX(), 
				(int)EditaFase.fase.getPlanetaInicial().getDrawOrbY(), 
				(int)EditaFase.fase.getPlanetaInicial().getOrbRaio() * 2, 
				(int)EditaFase.fase.getPlanetaInicial().getOrbRaio()* 2);

		// renderiza os planetas
		Graphics2D drawOrbita = (Graphics2D) g;
		drawOrbita.setColor(Color.WHITE);
		drawOrbita.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER,
				10.0f, tracejado, 0.0f));

		for(int i = 0; i < EditaFase.fase.getVetorPlanetas().size(); i++){
			Planeta planeta = (Planeta) EditaFase.fase.getVetorPlanetas().elementAt(i);
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
		for(int i = 0; i < EditaFase.fase.getVetorLuas().size(); i++){
			Lua lua = (Lua) EditaFase.fase.getVetorLuas().elementAt(i);
			g2d.drawImage(imagemLua, (int)lua.getDrawX(),
					(int)lua.getDrawY(), null);
		}

		// informacao de fase
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		g2d.drawString(EditaFase.fase.getNome() + ".", 20, 30);


		/******************** RENDERIZA ESPACONAVE *************************/

		//renderiza as espaçonaves
		Graphics2D drawRanger = (Graphics2D) g;


		Espaconave ranger = (Espaconave) espaconave;


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
		/*******************************************************************/

		Toolkit.getDefaultToolkit().sync(); /* garante atualização da tela
        nos variados sistemas de janela */
		g.dispose(); /* libera os recursos gráficos */


		repaint();

	}

	/** CICLO DO JOGO */
	private class Ciclo extends TimerTask {





		@Override
		public void run() {


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

			/*Atualiza movimento das luas*/
			if(!EditaFase.selecionandoLua)
				for(int i = 0; i < vetorLuas.size(); i++){
					Lua lua = vetorLuas.elementAt(i);
					lua.setMovimento();
				}

			/* atualiza movimento da espaconave */

			// se a nave está lançando usa o método setLancamento()
			if(espaconave.isLancando()){
				espaconave.setLancamento(planetaInicial);
			}

			// movimenta a nave no lancamento
			if(espaconave.isAndandoDireita()){
				if(espaconave.getAlpha() <= Math.toRadians(310)){
					espaconave.incrementaAlpha();
				}
			}
			if(espaconave.isAndandoEsquerda()){
				if(espaconave.getAlpha() >= Math.toRadians(230)){
					espaconave.decrementaAlpha();
				}
			}

			// se a nave está vagando usa o método setMovimentoRet()
			if(espaconave.isVagando()){
				espaconave.setMovimentoRet();
			}

			//se a nave está vagando mas esta em rota de colisao com planeta
			if(espaconave.isRotaColisao()){
				if(Funcoes.testaColisaoPlaneta(planetaColidir, espaconave) && !espaconave.isMorta()){
					espaconave.setMorta();
					setaExplosao();
					espaconave.setVagando(false);
				}
			}

			//se a nave esta vagando testa colisao com luas
			if(espaconave.isVagando()){
				Espaconave ranger = (Espaconave) espaconave;
				for(int j = 0; j < vetorLuas.size(); j++){
					Lua lua = vetorLuas.elementAt(j);
					if(Funcoes.testaColisao(lua, ranger) && !ranger.isMorta()){
						espaconave.setMorta();
						setaExplosao();
						espaconave.setVagando(false);
					}
				}
			}

			//se a nave esta vagando é testada a entrada em orbita aos planetas
			if(espaconave.isVagando() && !espaconave.isRotaColisao()){
				Espaconave ranger = (Espaconave) espaconave;
				for(int j = 0; j < EditaFase.fase.getVetorPlanetas().size(); j++){
					Planeta planeta = (Planeta) EditaFase.fase.getVetorPlanetas().elementAt(j);


					//TESTA ENTRADA NA ORBITA
					if(Funcoes.testaColisao(planeta, ranger)){

						//SE A NAVE TOCOU NA ORBITA COM ANGULO DE COLISAO, ENTAO COLIDIR
						if(Funcoes.colideOrbita(ranger, planeta)){
							espaconave.setRotaColisao(true);
							planetaColidir = planeta;
							break;
						}


						//TESTA SE O PLANETA COLIDIDO É O PLANETA FINAL
						if(!Venceu)
							if(planeta.getNome().equals("final")){
								Venceu = true;
								tempoVenceu = System.currentTimeMillis();
								ReprodutorDeSom.getInstancia().reproduzir("venceu");
							}
						ranger.setVagando(false);
						ranger.setOrbitando(true);
						planetaOrbitado = planeta;


						//DETERMINA EM QUE PONTO O MOVIMENTO CIRCULAR DEVE COMEÇAR
						ranger.setTheta(Funcoes.anguloCentral(ranger, planeta));
						//DETERMINA EM QUE SENTIDO GIRAR
						if(Funcoes.sentidoH(ranger, planeta)){
							espaconave.setSentidoHorario(true);
						}
						else{
							espaconave.setSentidoHorario(false);
						}

						ranger.setKZero();

						break;
					}
				}

			}
			/* quando a nave está orbitando, o movimento é setado para
        	 orbitar o planeta atual */

			if(espaconave.isOrbitando()){
				espaconave.setMovimentoCir(planetaOrbitado);
			}

			/* quando tá orbitando testa colisao com as luas */

			if(espaconave.isOrbitando()){
				Espaconave ranger = espaconave;

				for(int j = 0; j < vetorLuas.size(); j++){
					Lua lua = vetorLuas.elementAt(j);

					if(Funcoes.testaColisao(lua, ranger) && !ranger.isMorta()){
						espaconave.setMorta();
						setaExplosao();
						espaconave.setOrbitando(false);
					}
				}
			}


			//quando a nave esta morta o tempo é contado e ela é resetada
			if(espaconave.isMorta()){
				espaconave.reviveEspaconave();
			}


			if(Venceu){
				if((System.currentTimeMillis() - tempoVenceu) >= 2000L){
					espaconave.resetaEspaconave();
					Venceu = false;
				}
			}

		}

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

	// funcao para mover o planeta e mover a lua, caso exista uma
	private void movePlaneta(int x, int y){
		for(int i = 0; i < EditaFase.fase.getVetorPlanetas().size(); i++){
			Planeta planeta = (Planeta) EditaFase.fase.getVetorPlanetas().elementAt(i);

			if (planeta.getNome().equals(EditaFase.getPlanetaSelecionado().getNome())){
				EditaFase.fase.getVetorPlanetas().elementAt(i).setCentroPlaneta(x, y);
				planeta.setCentroPlaneta(x, y);
				EditaFase.fieldPosicaoPlaneta.setText(" (" + (int) planeta.getCentroX() + ", " + (int) planeta.getCentroY() + ")");
				EditaFase.atualizaPlanetaDaLua();

			}
		}
	}



	private void setaExplosao(){
		POSICAO_X_EXPLOSAO = (int) (espaconave.getCentroX() - 25);
		POSICAO_Y_EXPLOSAO = (int) (espaconave.getCentroY() - 25);
		sx1 = 0;
		sy1 = 0;
		sx2 = 100;
		sy2 = 100;
		ReprodutorDeSom.getInstancia().reproduzir("explosao");

	}



}
