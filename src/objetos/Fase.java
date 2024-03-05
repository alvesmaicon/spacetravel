package objetos;

import java.io.Serializable;
import java.util.Vector;

public class Fase implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4071879875423132095L;

	private boolean faseJogarLivre = false;
	private boolean faseVencida = false;
	private long time = 0;
	private String Nome;


	private Vector<Planeta> vetorPlanetas = new Vector<Planeta>();
	private Vector<Lua> vetorLuas = new Vector<Lua>();;
	private PlanetaInicial PlanetaInicial;


	public Fase(String nome){
		this.setNome(nome);

		
		PlanetaInicial = new PlanetaInicial("inicial", 300, 595, 100);
	


		//TODO SETAR LOCAL ESPACONAVE QUANDO MOVER PLANETA INICIAL


	}



	public String getNome() {
		return Nome;
	}



	public void setNome(String nome) {
		Nome = nome;
	}



	public boolean isFaseVencida() {
		return faseVencida;
	}
	
	public void setFaseNaoVencida(){
		faseVencida = false;
	}
	

	public boolean isFaseJogarLivre() {
		return faseJogarLivre;
	}

	
	public void setFaseJogarLivre() {
		this.faseJogarLivre = true;
	}



	public void setFaseVencida() {
		this.faseVencida = true;
	}

	public long getTime() {
		return time;
	}

	public boolean setMelhorTempo(long time) {
		if(this.time == 0){
			this.time = time;
			return true;
		}
		else
			if(time < this.time){
				this.time = time;
				return true;
			}
			
		return false;
	}

	public Vector<Planeta> getVetorPlanetas() {
		return vetorPlanetas;
	}

	public Vector<Lua> getVetorLuas() {
		return vetorLuas;
	}

	public PlanetaInicial getPlanetaInicial() {
		return PlanetaInicial;
	}



	/**ADICIONA PLANETA NA FASE*/
	public void addPlaneta(String nome, int x, int y, int raio){
		Planeta planeta = new Planeta(nome, x, y, raio);
		vetorPlanetas.add(planeta);	
	}

	/**ADICIONA UMA LUA EM UM PLANETA*/
	public boolean addLua(String nomePlaneta, int diametroLua, boolean sentidoHorario, double deslocamento){

		for(int i = 0; i < vetorPlanetas.size(); i++){
			if(vetorPlanetas.elementAt(i).getNome().equals(nomePlaneta)){ 
				Lua lua = new Lua("lua", vetorPlanetas.elementAt(i), diametroLua, sentidoHorario, deslocamento);
				vetorLuas.add(lua);
				return true;

			}		
		}

		System.out.println("Nao foi encontrado planeta com nome " + nomePlaneta + ".");
		return false;
	}

	/**ADICIONA PLANETAINICIAL GERADO E SETA ESPACONAVE NESSE PLANETA*/
	public void setPlanetaInicial(int setX){

		PlanetaInicial.setCentroX(setX);	

	}
	/**ADICIONA PLANETA FINAL NA FASE*/
	public void addPlanetaFinal(int x, int y, int raio){
		Planeta planeta = new Planeta("final", x, y, raio);
		vetorPlanetas.add(planeta);	
	}

	/**RETORNA O PLANETA FINAL*/
	public Planeta getPlanetaFinal(){
		Planeta planetaFinal = null;
		for(int i = 0; i < vetorPlanetas.size(); i ++){
			if(vetorPlanetas.elementAt(i).getNome().equals("final")){
				planetaFinal = vetorPlanetas.elementAt(i);
			}
		}
		return planetaFinal;
	}

	public void resetTime() {
		this.time = 0;
		
	}



	public void setFaseJogarLivre(boolean jogarFree) {
		this.faseJogarLivre = jogarFree;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}


}
