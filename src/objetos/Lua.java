package objetos;

import java.io.Serializable;

public class Lua implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6404095942948020792L;
	

	private int diametroLua;
	private double CentroX, CentroY, drawX, drawY;
	private double i = 0, inicial = 0;
	private String nome;
	
	private boolean sentidoHorario;
	private double deslocamento = 0.0;
	private Planeta planetaDestaLua;
	
	public Lua(String nome, Planeta planeta, int diametroLua, boolean sentidoHorario, double deslocamento){
		this.nome = nome;
		this.diametroLua = diametroLua;
		this.sentidoHorario = sentidoHorario;
		this.deslocamento = deslocamento;
		this.planetaDestaLua = planeta;
		
	}
	
	public String getNome(){
		return nome;
	}
	


	public double getDrawX() {
		drawX = CentroX - diametroLua/2;
		return drawX;
	}

	public double getDrawY() {
		drawY = CentroY - diametroLua/2;
		return drawY;
	}

	public int getDiametro() {
		return diametroLua;
	}
	
	public void setMovimento(){
		CentroX = planetaDestaLua.getCentroX() + planetaDestaLua.getOrbRaio() * Math.cos(Math.toRadians(i));
		CentroY = planetaDestaLua.getCentroY() + planetaDestaLua.getOrbRaio() * Math.sin(Math.toRadians(i));
		
		if(sentidoHorario){
			i += deslocamento;
		}else{
			i -= deslocamento;
		}
		
		if(i == 360 || i == -360){
			i = 0;
		}
	}

	public double getCentroX() {
		return CentroX;
	}
	
	public double getCentroY() {
		return CentroY;
	}

	public double getRaio() {
		return diametroLua/2;
	}
	
	public void setPosicaoI(int novaPosicao){
		this.inicial = novaPosicao;
		this.i = novaPosicao;
	}

	public void setPlanetaDestaLua(Planeta planeta){
		this.planetaDestaLua = planeta;
	}
	
	public double getDeslocamento(){
		return deslocamento;
	}
	
	public void aumentaDeslocamento(){
		this.deslocamento += 0.1;
	}
	
	public void diminuiDeslocamento(){
		if(deslocamento >= 0.1)
			this.deslocamento -= 0.1;
	}
	
	public Planeta getPlanetaDestaLua(){
		return planetaDestaLua;
	}
	
	public boolean getSentidoHorario(){
		return sentidoHorario;
	}
	
	public void setSentidoHorario(boolean sentido){
		this.sentidoHorario = sentido;
	}
	
	public void resetLua(){
		this.i = inicial;
	}
}
