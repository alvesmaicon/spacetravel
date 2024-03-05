
package estruturas;

import java.util.Vector;

import objetos.Espaconave;
import objetos.Lua;
import objetos.Planeta;
import objetos.PlanetaInicial;

public class Single {


	public static Vector<Planeta> vetorPlanetas;
	public static Vector<Lua> vetorLuas;
	public static PlanetaInicial PlanetaInicial;
	public static Espaconave Espaconave;
	

	public Single(){
		PlanetaInicial = new PlanetaInicial("inicial", 500, 595, 100);
		Espaconave = new Espaconave("ranger", PlanetaInicial);
		vetorPlanetas = new Vector<Planeta>();
		vetorLuas = new Vector<Lua>();
		
		

		
		Planeta planetaFinal = new Planeta("final", 180, 130, 30);
		Planeta planeta1 = new Planeta("planeta1", 380, 390, 30);
		Planeta planeta2 = new Planeta("planeta2", 350, 210, 28);
		Lua lua1 = new Lua("lua", planeta2, 30, false, 1);
		
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorLuas.add(lua1);
		vetorPlanetas.add(planetaFinal);
		
	}
	
	public static void selecionaFase(int indice){

		
		PlanetaInicial = Fases.vetorFases.elementAt(indice).getPlanetaInicial();
		Espaconave.setLocalInicial(PlanetaInicial);
		
		
		
		vetorPlanetas = Fases.vetorFases.elementAt(indice).getVetorPlanetas();
		vetorLuas = Fases.vetorFases.elementAt(indice).getVetorLuas();
		

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	private static void fase1(){
		
		limpaVetores();
		
		PlanetaInicial.setCentroX(270);
		Espaconave.setLocalInicial(PlanetaInicial);
		
		Planeta planetaFinal = new Planeta("final", 370, 200, 35);
		
		vetorPlanetas.add(planetaFinal);
	}
	
	private static void fase2(){
		limpaVetores();
		PlanetaInicial.setCentroX(270);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 420, 180, 32);
		Planeta planeta1 = new Planeta("planeta1", 335, 330, 30);
	
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planetaFinal);
	}
	
	private static void fase3(){
		limpaVetores();
		
		
		PlanetaInicial.setCentroX(270);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 600, 200, 30);
		Planeta planeta1 = new Planeta("planeta1", 415, 380, 30);
		Planeta planeta2 = new Planeta("planeta2", 445, 230, 28);
		
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planetaFinal);
	}
	
	private static void fase4(){
		limpaVetores();
		PlanetaInicial.setCentroX(500);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 180, 130, 30);
		Planeta planeta1 = new Planeta("planeta1", 380, 390, 30);
		Planeta planeta2 = new Planeta("planeta2", 350, 210, 28);
		Lua lua1 = new Lua("lua", planeta2, 30, false, 1);
		
		
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorLuas.add(lua1);
		vetorPlanetas.add(planetaFinal);

	}
	
	private static void fase5(){
		
		limpaVetores();
		
		PlanetaInicial.setCentroX(200);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 600, 150, 33);
		Planeta planeta1 = new Planeta("planeta1", 360, 390, 30);
		Planeta planeta2 = new Planeta("planeta2", 350, 230, 28);
		Planeta planeta3 = new Planeta("planeta3", 540, 340, 34);
		Lua lua1 = new Lua("lua", planeta2, 30, false, 1);
		Lua lua2 = new Lua("lua", planeta3, 30, true, 1);
		

		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorLuas.add(lua1);
		vetorLuas.add(lua2);
		vetorPlanetas.add(planetaFinal);
		
	}
	
	private static void fase6(){
		limpaVetores();
		PlanetaInicial.setCentroX(600);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 180, 150, 30);
		Planeta planeta1 = new Planeta("planeta1", 380, 390, 30);
		Planeta planeta2 = new Planeta("planeta2", 350, 230, 28);
		Planeta planeta3 = new Planeta("planeta3", 540, 340, 32);
		Lua lua1 = new Lua("lua", planeta1, 30, false, 1);
		Lua lua2 = new Lua("lua", planeta3, 30, true, 1);

		
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorLuas.add(lua1);
		vetorLuas.add(lua2);
		vetorPlanetas.add(planetaFinal);


	}
	
	private static void fase7(){
		
		limpaVetores();
		PlanetaInicial.setCentroX(270);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planeta1 = new Planeta("planeta1", 180, 370, 28);
		Planeta planeta2 = new Planeta("planeta2", 420, 400, 32);
		Planeta planeta3 = new Planeta("planeta3", 250, 200, 30);
		Planeta planeta4 = new Planeta("planeta4", 480, 210, 38);
		Planeta planetaFinal = new Planeta("final", 670, 100, 32);

		Lua lua1 = new Lua("lua", planeta3, 30, true, 0.7);
		Lua lua2 = new Lua("lua", planeta4, 30, false, 1);


		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorPlanetas.add(planeta4);
		vetorPlanetas.add(planetaFinal);
		vetorLuas.add(lua1);
		vetorLuas.add(lua2);
		
	}
	
	private static void fase8(){
		limpaVetores();
		
		PlanetaInicial.setCentroX(150);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 650, 400, 33);
		Planeta planeta1 = new Planeta("planeta1", 240, 370, 30);
		Planeta planeta2 = new Planeta("planeta2", 400, 400, 28);
		Planeta planeta3 = new Planeta("planeta3", 520, 280, 32);
		Lua lua1 = new Lua("lua", planeta2, 30, false, 1);
		Lua lua2 = new Lua("lua", planeta3, 30, true, 1);

		
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorLuas.add(lua1);
		vetorLuas.add(lua2);
		vetorPlanetas.add(planetaFinal);
		
	}
	
	
	private static void fase9(){
		
		limpaVetores();
		PlanetaInicial.setCentroX(200);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planetaFinal = new Planeta("final", 650, 100, 33);
		Planeta planeta1 = new Planeta("planeta1", 240, 340, 30);
		Planeta planeta2 = new Planeta("planeta2", 400, 400, 28);
		Planeta planeta3 = new Planeta("planeta3", 560, 280, 32);
		Planeta planeta4 = new Planeta("planeta4", 400, 170, 34);
		Lua lua1 = new Lua("lua", planeta2, 30, false, 1);
		Lua lua2 = new Lua("lua", planeta3, 30, true, 0.7);
		Lua lua3 = new Lua("lua", planeta4, 30, true, 1.2);
		
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorPlanetas.add(planeta4);
		vetorLuas.add(lua1);
		vetorLuas.add(lua2);
		vetorLuas.add(lua3);
		vetorPlanetas.add(planetaFinal);

	}
	
	private static void fase10(){
		limpaVetores();
		PlanetaInicial.setCentroX(170);
		Espaconave.setLocalInicial(PlanetaInicial);
		Planeta planeta1 = new Planeta("planeta1", 250, 400, 28);
		Planeta planeta2 = new Planeta("planeta2", 390, 280, 31);
		Planeta planeta3 = new Planeta("planeta3", 540, 190, 32);
		Planeta planetaFinal = new Planeta("final", 690, 100, 25);

		Lua lua1 = new Lua("lua", planeta1, 30, true, 0.7);
		Lua lua2 = new Lua("lua", planeta2, 30, false, 1);
		Lua lua3 = new Lua("lua", planeta3, 30, true, 1);
	
		vetorPlanetas.add(planeta1);
		vetorPlanetas.add(planeta2);
		vetorPlanetas.add(planeta3);
		vetorPlanetas.add(planetaFinal);
		vetorLuas.add(lua1);
		vetorLuas.add(lua2);
		vetorLuas.add(lua3);

	}
	
	
	
	public static void selecionaFase3(int numero){
		if(numero == 1){
			fase1();
		}
		if(numero == 2){
			fase2();
		}
		
		if(numero == 3){
			fase3();
		}
		if(numero == 4){
			fase4();
		}
		
		if(numero == 5){
			fase5();
		}
		if(numero == 6){
			fase6();
		}
		
		if(numero == 7){
			fase7();
		}
		if(numero == 8){
			fase8();
		}
		
		if(numero == 9){
			fase9();
		}
		if(numero == 10){
			fase10();
		}
		
	}
*/
}
