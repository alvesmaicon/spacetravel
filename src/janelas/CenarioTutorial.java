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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class CenarioTutorial extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3073427062415017622L;
	
	private static final int ATRASO_INICIAL = 0;
	private static final int INTERVALO = 10;
	private static final int X_FPS = 40;
	private static final int Y_FPS = 100;
	
	private static long tempoAnterior = System.currentTimeMillis();
	private static int quantidadeDeChamadas = 0;
	private static int fps = 0;
	private Image fundo;
	
	private Timer timer;

	public CenarioTutorial() {
		
		//configuração do timer
				timer = new Timer();
				timer.scheduleAtFixedRate(new Ciclo(), ATRASO_INICIAL, INTERVALO);
		
		//carga da imagem de fundo em um ícone (primitivo da imagem)
				fundo = Toolkit.getDefaultToolkit().getImage("imagens/milkway.png");
				fundo = fundo.getScaledInstance(
						Janela.LARGURA, Janela.ALTURA, Image.SCALE_DEFAULT);

		// configura os listeners de teclado
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent evento) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

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

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		desenharCoisas(g);

	}

	/** Renderizador do jogo */

	public void desenharCoisas(Graphics g) {
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

		Toolkit.getDefaultToolkit().sync(); /*
											 * garante atualização da tela nos
											 * variados sistemas de janela
											 */
		g.dispose(); /* libera os recursos gráficos */
	}
	
	/**CICLO DO JOGO*/
	private class Ciclo extends TimerTask{

		//método com animações e lógica do jogo
		//será invocado no intervalo definido para invocação da tarefa



		@Override
		public void run(){
			repaint(); /* atualiza as coisas na tela */
			
			
		}
	}

}
