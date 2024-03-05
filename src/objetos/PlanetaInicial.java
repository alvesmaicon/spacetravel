package objetos;

import java.io.Serializable;

public class PlanetaInicial implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -37957076549120086L;
	private double[] pontoCentral, drawPonto, vetAnguloInicial;
	private double raio, centroX, centroY, drawX, drawY, orbRaio, 
	drawOrbX, drawOrbY;
	private String nome;
	private  int altura, largura;
	
	public PlanetaInicial(String nome, double centroX, double centroY, double raio){
		super();
		
		this.pontoCentral = new double[2];
		this.drawPonto = new double[2];
		this.vetAnguloInicial =  new double[2];
		
		this.nome = nome;
		this.centroX = centroX;
		this.centroY = centroY;
		this.raio = raio;
		
		pontoCentral[0] = centroX;
		pontoCentral[1] = centroY;
		
		drawPonto[0] = centroX - raio;
		drawPonto[1] = centroY - raio;
		
		drawX = centroX - raio;
		drawY = centroY - raio;
		
		orbRaio = raio + 10;
		drawOrbX = drawX - orbRaio;
		drawOrbY = drawY - orbRaio;
		
		largura = (int)raio * 2;
		altura = (int)raio * 2;
		


		
	}
	
	



	public int getAltura() {
		return altura;
	}



	public int getLargura() {
		return largura;
	}
	public void setCentroX(int novoCentroX){
		centroX = novoCentroX;
		drawX = centroX - raio;
	}
	
	
	
	public void setPontoCentral(double[] pontoCentral) {
		this.pontoCentral = pontoCentral;
		
		centroX = pontoCentral[0];
		centroY = pontoCentral[1];
		
		drawPonto[0] = centroX - raio;
		drawPonto[1] = centroY - raio;
		
		drawX = centroX - raio;
		drawY = centroY - raio;
		
		
		
		orbRaio = 2 * raio;
		drawOrbX = drawX - raio;
		drawOrbY = drawY - raio;
		
	}


	public double[] getPontoCentral() {
		this.pontoCentral[0] =  getCentroX();
		this.pontoCentral[1] = getCentroY();
		return pontoCentral;
	}

	public double[] getDrawPonto() {
		this.drawPonto[0] = getDrawX();
		this.drawPonto[1] = getDrawY();
		return drawPonto;
	}

	public double[] getAnguloInicial() {
		this.vetAnguloInicial[0] = drawOrbX;
		this.vetAnguloInicial[1] = 0;
		return vetAnguloInicial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getCentroX() {
		return centroX;
	}


	public double getCentroY() {
		return centroY;
	}


	public double getRaio() {
		return raio;
	}

	public double getDrawX() {
		this.drawX = getCentroX() - getRaio();
		return drawX;
	}

	public double getDrawY() {
		this.drawY = getCentroY() - getRaio();
		return drawY;
	}
	

	public double getOrbRaio() {
		return orbRaio;
	}
	
	public void setOrbRaio(double NovoRaio){
		this.orbRaio = NovoRaio;
	}

	public double getDrawOrbX() {
		this.drawOrbX = (getCentroX() - getOrbRaio());
		return drawOrbX;
	}

	public double getDrawOrbY() {
		this.drawOrbY = (getCentroY() - getOrbRaio());
		return drawOrbY;
	}

}
