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

import javax.swing.JPanel;

import sons.ReprodutorDeMusica2;
import sons.ReprodutorDeSom;

public class CenarioComoJogar extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1118080096477405176L;
	private static Image fundo = null;

	public static ReprodutorDeMusica2 musica_como_jogar = null;

	private int x, y;
	
	public CenarioComoJogar(){
		fundo = Toolkit.getDefaultToolkit().getImage("imagens/fundo-2.png");
		fundo = fundo.getScaledInstance(
				Janela.LARGURA, Janela.ALTURA - 20, Image.SCALE_DEFAULT);



		musica_como_jogar = new ReprodutorDeMusica2("sons/musica-2-loop.wav");
		musica_como_jogar.start();
		musica_como_jogar.pausar();

		this.addKeyListener(
				new KeyListener(){

					@Override
					public void keyPressed(KeyEvent event) {
						if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
							if(CenarioSingle.isPausa()){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioSingle);

								musica_como_jogar.pausar();
							}

							if(!CenarioSingle.Perdeu && !CenarioSingle.isPausa()){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);

								musica_como_jogar.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();

							}
							if(CenarioSingle.Perdeu){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioSingle);

								musica_como_jogar.pausar();
								CenarioSingle.musica_single.retomar();
							}

						}


					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

				});



		this.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent event) {
						x = event.getX();
						y = event.getY();

						if(x > 335 && x < 515 && y > 442 && y < 472){

							if(CenarioSingle.isPausa()){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioSingle);

								musica_como_jogar.pausar();
							}

							if(!CenarioSingle.Perdeu && !CenarioSingle.isPausa()){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioInicio);

								musica_como_jogar.pausar();
								CenarioInicio.musica_inicio.recomeca();
								CenarioInicio.musica_inicio.retomar();

							}
							if(CenarioSingle.Perdeu){
								ReprodutorDeSom.getInstancia().reproduzir("click");
								Janela.trocaCenario(Janela.cenarioSingle);

								musica_como_jogar.pausar();
								CenarioSingle.musica_single.retomar();
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
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		desenharCoisas(g);

	}

	public void desenharCoisas(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(fundo, -10, -12, null);


		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		g2d.drawString("Voltar", 397, 464);


		//		g2d.drawRect(335, 442, 180, 30);
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawRect(10, 10, 770, 400);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawLine(430, 10, 430, 410);


		Toolkit.getDefaultToolkit().sync(); /* garante atualização da tela
        nos variados sistemas de janela */
		g.dispose(); /* libera os recursos gráficos */


		repaint();

	}


}
