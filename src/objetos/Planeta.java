package objetos;

import java.io.Serializable;

public class Planeta implements Serializable, Cloneable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -766640209164700480L;
	private double raio, centroX, centroY, drawX, drawY, orbRaio, 
	drawOrbX, drawOrbY, centroXInicial;
	private String nome;

	private  int altura, largura;
	private double t = 1;
	public Planeta(String nome, double centroX, double centroY, double raio){
		super();
		
		this.nome = nome;
		this.centroX = centroX;
		this.centroXInicial = centroX;
		this.centroY = centroY;
		this.raio = raio;
		
		
		drawX = centroX - raio;
		drawY = centroY - raio;
		
		orbRaio = 2 * raio;
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
		this.orbRaio = NovoRaio * 2;
	}

	public double getDrawOrbX() {
		this.drawOrbX = (getCentroX() - getOrbRaio());
		return drawOrbX;
	}

	public double getDrawOrbY() {
		this.drawOrbY = (getCentroY() - getOrbRaio());
		return drawOrbY;
	}

	public void setCentroX(){
		this.centroX = centroXInicial + (60 * Math.cos(t));
		t+= Math.toRadians(1);
		drawX = centroX;
	}
	
	public void setCentroPlaneta(int x, int y){
		this.centroX = x;
		this.centroY = y;
		this.drawX = centroX - raio;
		this.drawY = centroY - raio;
	}
	
	public void aumentaRaio(){
		raio += 1.0;
		setOrbRaio(raio);
	}

	public void diminuiRaio(){
		if(raio >= 11){
			raio -= 1.0;
			setOrbRaio(raio);
		}
	}
}
