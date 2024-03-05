
package estruturas;

import java.util.Vector;

import objetos.Espaconave;
import objetos.Lua;
import objetos.Planeta;
import objetos.PlanetaInicial;

public class Multi {

	public static Vector<Planeta> vetorPlanetas;
	public static Vector<Lua> vetorLuas;
	public static Vector<PlanetaInicial> vetorPlanetaInicial;
	public static Vector<Espaconave> vetorEspaconaves;
	
	public static Vector<Planeta> planetaColidir;
	public static Vector<Planeta> planetaOrbitado;

	


	public Multi(){
		vetorPlanetas = new Vector<Planeta>();
		vetorLuas = new Vector<Lua>();
		vetorEspaconaves = new Vector<Espaconave>();
		vetorPlanetaInicial = new Vector<PlanetaInicial>();
		planetaColidir = new Vector<Planeta>();
		planetaOrbitado = new Vector<Planeta>();
		
		



//		Planeta planeta1 = new Planeta("planeta1", 260, 355, 28);
		Planeta planeta1 = new Planeta("planeta1", 300, 340, 30);
//		Planeta planeta2 = new Planeta("planeta2", 395, 380, 25);
		Planeta planeta2 = new Planeta("planeta2", 485, 340, 30);
		Planeta planeta3 = new Planeta("planeta3", 395, 215, 30);
		Planeta planeta4 = new Planeta("planeta4", 220, 170, 35);
//		Planeta planeta5 = new Planeta("planeta5", 530, 355, 28);
		Planeta planeta6 = new Planeta("planeta6", 565, 170, 35);
		
		Planeta planetaFinal = new Planeta("final", 395, 70, 25);
		
		Lua lua1 = new Lua("lua", planeta4, 30, false, 1);
		Lua lua2 = new Lua("lua", planeta6, 30, true, 1);


		PlanetaInicial planetaI = new PlanetaInicial("inicial", 260, 595, 100);
		PlanetaInicial planetaI2 = new PlanetaInicial("inicial", 540, 595, 100);
		
		Espaconave ranger1 = new Espaconave("ranger1", planetaI);
		Espaconave ranger2 = new Espaconave("ranger2", planetaI2);

		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorPlanetas.add(planeta4);
//		vetorPlanetas.add(planeta5);
		vetorPlanetas.add(planeta6);
		vetorPlanetas.add(planetaFinal);

		vetorLuas.add(lua1);
		vetorLuas.add(lua2);

		vetorPlanetaInicial.add(planetaI);
		vetorPlanetaInicial.add(planetaI2);
		
		vetorEspaconaves.add(ranger1);
		vetorEspaconaves.add(ranger2);
		
		planetaColidir.add(planeta1);
		planetaColidir.add(planeta1);
		planetaOrbitado.add(planeta1);
		planetaOrbitado.add(planeta1);
	}	

}