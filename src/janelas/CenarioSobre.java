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

import editordefases.EditorDeFase;
import estruturas.Fases;
import sons.ReprodutorDeMusica2;
import sons.ReprodutorDeSom;

public class CenarioSobre extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1118080096477405176L;
	private static Image fundo = null;
	
	public static ReprodutorDeMusica2 musica_sobre = null;
	public static EditorDeFase editorDeFase;
	
	private int x, y;
	
	public CenarioSobre(){
		fundo = Toolkit.getDefaultToolkit().getImage("imagens/fundo-1.png");
		fundo = fundo.getScaledInstance(
				Janela.LARGURA, Janela.ALTURA - 20, Image.SCALE_DEFAULT);
		
		musica_sobre = new ReprodutorDeMusica2("sons/musica-2-loop.wav");
		musica_sobre.start();
		musica_sobre.pausar();
		
		this.addKeyListener(
				new KeyListener(){

					@Override
					public void keyPressed(KeyEvent evento) {
						if(evento.getKeyCode() == KeyEvent.VK_ESCAPE){
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.trocaCenario(Janela.cenarioInicio);
							
							musica_sobre.pausar();
							CenarioInicio.musica_inicio.recomeca();
							CenarioInicio.musica_inicio.retomar();
						}
						
						if(evento.isShiftDown() && evento.isAltDown() && evento.getKeyCode() == KeyEvent.VK_E){
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.cenarioSobre.setVisible(false);
							editorDeFase = new editordefases.EditorDeFase();
							
						}
						
						if(evento.isShiftDown() && evento.isAltDown() && evento.getKeyCode() == KeyEvent.VK_R){
							Fases.resetVencidas();
							JOptionPane.showMessageDialog(getParent(), "O progresso do jogo foi resetado.");
							
						}
						
						if(evento.isShiftDown() && evento.isAltDown() && evento.getKeyCode() == KeyEvent.VK_T){
							Fases.resetTempos();
							JOptionPane.showMessageDialog(getParent(), "Os records de tempo foram resetados.");
						}
						
						if(evento.isShiftDown() && evento.isAltDown() && evento.getKeyCode() == KeyEvent.VK_F){
							Fases.resetJogarLivre();
							JOptionPane.showMessageDialog(getParent(), "As fases para jogar em modo free foram resetadas.");
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
							ReprodutorDeSom.getInstancia().reproduzir("click");
							Janela.trocaCenario(Janela.cenarioInicio);
							
							musica_sobre.pausar();
							CenarioInicio.musica_inicio.recomeca();
							CenarioInicio.musica_inicio.retomar();
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
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 15));
		g2d.drawString("Editar fases:", 30, 450);
		g2d.drawString("Resetar progresso:", 30, 465);
		g2d.drawString("Limpar tempos:", 30, 480);
		g2d.drawString("Limpar fases liberadas:", 30, 495);
		
		g2d.drawString("alt + shift + E", 200, 450);
		g2d.drawString("alt + shift + R", 200, 465);
		g2d.drawString("alt + shift + T", 200, 480);
		g2d.drawString("alt + shift + F", 200, 495);
		
		
//		g2d.drawRect(335, 442, 180, 30);
		
		Toolkit.getDefaultToolkit().sync(); /* garante atualização da tela
        nos variados sistemas de janela */
		g.dispose(); /* libera os recursos gráficos */
		
		
		repaint();
		
	}


}
