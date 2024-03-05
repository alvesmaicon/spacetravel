package estruturas;

import java.util.Vector;

import objetos.Fase;
import salvamento.Carrega;
import salvamento.Salva;
import util.Funcoes;

public class Fases {
	public static Vector<Fase> vetorFases;

	
	public Fases(){
		vetorFases = new Vector<Fase>();
		Carrega.carregaFases();
		
//		removeTodasFases();
//		adicionaFases();
//		exibeStatusFases();
		Salva.salvaFases();
		

		
		
	}
	

	public void adicionaFases(){
		Fase fase1 = new Fase("Fase 1");
		fase1.setPlanetaInicial(270);
		fase1.addPlanetaFinal(370, 200, 35);
		vetorFases.add(fase1);
		
		Fase fase2 = new Fase("Fase 2");
		fase2.setPlanetaInicial(270);
		fase2.addPlanetaFinal(420, 180, 32);
		fase2.addPlaneta("planeta1", 335, 330, 30);
		vetorFases.add(fase2);
		
		Fase fase3 = new Fase("Fase 3");
		fase3.setPlanetaInicial(270);
		fase3.addPlanetaFinal(600, 200, 30);
		fase3.addPlaneta("planeta1", 415, 380, 30);
		fase3.addPlaneta("planeta2", 445, 230, 28);
		vetorFases.add(fase3);
		
		Fase fase4 = new Fase("Fase 4");
		fase4.setPlanetaInicial(500);
		fase4.addPlanetaFinal(180, 130, 30);
		fase4.addPlaneta("planeta1", 380, 390, 30);
		fase4.addPlaneta("planeta2", 350, 210, 28);
		fase4.addLua("planeta2", 30, false, 1);
		vetorFases.add(fase4);
		
		Fase fase5 = new Fase("Fase 5");
		fase5.setPlanetaInicial(200);
		fase5.addPlanetaFinal(600, 150, 33);
		fase5.addPlaneta("planeta1", 360, 390, 30);
		fase5.addPlaneta("planeta2", 350, 230, 28);
		fase5.addPlaneta("planeta3", 540, 340, 34);
		fase5.addLua("planeta2", 30, false, 1);
		fase5.addLua("planeta3", 30, true, 1);
		vetorFases.add(fase5);
		
		Fase fase6 = new Fase("Fase 6");
		fase6.setPlanetaInicial(600);
		fase6.addPlanetaFinal(180, 150, 30);
		fase6.addPlaneta("planeta1", 380, 390, 30);
		fase6.addPlaneta("planeta2", 350, 230, 28);
		fase6.addPlaneta("planeta3", 540, 340, 32);
		fase6.addLua("planeta1", 30, false, 1);
		fase6.addLua("planeta3", 30, true, 1);
		vetorFases.add(fase6);
		
		Fase fase7 = new Fase("Fase 7");
		fase7.setPlanetaInicial(270);
		fase7.addPlanetaFinal(670, 100, 32);
		fase7.addPlaneta("planeta1", 180, 370, 28);
		fase7.addPlaneta("planeta2", 420, 400, 32);
		fase7.addPlaneta("planeta3", 250, 200, 30);
		fase7.addPlaneta("planeta4", 480, 210, 38);
		fase7.addLua("planeta3", 30, true, 0.7);
		fase7.addLua("planeta4", 30, false, 1);
		vetorFases.add(fase7);
		
		Fase fase8 = new Fase("Fase 8");
		fase8.setPlanetaInicial(150);
		fase8.addPlanetaFinal(650, 400, 33);
		fase8.addPlaneta("planeta1", 240, 370, 30);
		fase8.addPlaneta("planeta2", 400, 400, 28);
		fase8.addPlaneta("planeta3", 520, 280, 32);
		fase8.addLua("planeta2", 30, false, 1);
		fase8.addLua("planeta3", 30, true, 1);
		vetorFases.add(fase8);
		
		Fase fase9 = new Fase("Fase 9");
		fase9.setPlanetaInicial(200);
		fase9.addPlanetaFinal(650, 100, 33);
		fase9.addPlaneta("planeta1", 240, 340, 30);
		fase9.addPlaneta("planeta2", 400, 400, 28);
		fase9.addPlaneta("planeta3", 560, 280, 32);
		fase9.addPlaneta("planeta4", 400, 170, 34);
		fase9.addLua("planeta2", 30, false, 1);
		fase9.addLua("planeta3", 30, true, 0.7);
		fase9.addLua("planeta4", 30, true, 1.2);
		vetorFases.add(fase9);
		
		Fase fase10 = new Fase("Fase 10");
		fase10.setPlanetaInicial(170);
		fase10.addPlanetaFinal(690, 100, 25);
		fase10.addPlaneta("planeta1", 250, 400, 28);
		fase10.addPlaneta("planeta2", 390, 280, 31);
		fase10.addPlaneta("planeta3", 540, 190, 32);
		fase10.addLua("planeta1", 30, true, 0.7);
		fase10.addLua("planeta2", 30, false, 1);
		fase10.addLua("planeta3", 30, true, 1);
		vetorFases.add(fase10);
		
	}
	
	public static void exibeStatusFases(){
		System.out.println("Foram encontradas " + vetorFases.size() + " fases."
				+ "\n-------------------------------------------------------------------");
		for(int i = 0; i < vetorFases.size(); i++){
			Fase fase = (Fase) vetorFases.elementAt(i);
			System.out.println("Nome: " + fase.getNome() + "\t\tVencida: " + fase.isFaseVencida()
			+ "\t\tMelhor tempo: " + Funcoes.converteTempo(fase.getTime()) + 
			 
			"\t\t\nPlanetas: " + vetorFases.elementAt(i).getVetorPlanetas().size() + 
			"\t\tLuas: " + vetorFases.elementAt(i).getVetorLuas().size() +
			"\n-------------------------------------------------------------------");
		}
			
	}
	
	public static void exibeStatusFaseAtual(int IndiceFase){
		System.out.println("****************     Exibindo status da fase " + (IndiceFase)  + ".     ***************"
				+ "\n-------------------------------------------------------------------");
		
			Fase fase = (Fase) vetorFases.elementAt(IndiceFase);
			System.out.println("Nome: " + fase.getNome() + "\t\tVencida: " + fase.isFaseVencida()
			+ "\t\tMelhor tempo: " + Funcoes.converteTempo(fase.getTime()) +  
			"\t\t\nPlanetas: " + fase.getVetorPlanetas().size() + 
			"\t\tLuas: " + fase.getVetorLuas().size() +
			"\n-------------------------------------------------------------------");
		
			
	}
	
	public void removeTodasFases(){
		vetorFases.removeAllElements();
	}
	
	public static int selecionaFaseNaoVencida(){
		for(int i = 0; i < vetorFases.size(); i ++){
			if(!vetorFases.elementAt(i).isFaseVencida()){
				return i;
			}
		}

		return 0;
	}
	
	public static void resetVencidas(){
		for(int i = 0; i < vetorFases.size(); i++){
			vetorFases.elementAt(i).setFaseNaoVencida();
		}
	}
	
	public static void resetJogarLivre(){
		for(int i = 0; i < vetorFases.size(); i++){
			vetorFases.elementAt(i).setFaseJogarLivre(false);
		}
	}
	
	public static void resetTempos(){
		for(int i = 0; i < vetorFases.size(); i++){
			vetorFases.elementAt(i).resetTime();
		}
	}
	
	public static void atualizaNomesFases(){
		for(int i = 0; i < vetorFases.size(); i++){
			vetorFases.elementAt(i).setNome("Fase " + (i + 1));
		}
	}

}
