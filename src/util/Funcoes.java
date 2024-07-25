package util;

import java.util.concurrent.TimeUnit;

import objetos.Espaconave;
import objetos.Lua;
import objetos.Planeta;
import salvamento.Salva;
import sons.ReprodutorDeSom;

public class Funcoes {

	protected Funcoes(){

	}

	//TODO algumas funções não estão sendo usadas.
	//usando
	public static boolean testaColisao(Planeta planeta, Espaconave ranger){
		return distAB(planeta, ranger) <= planeta.getOrbRaio();
	}

	public static boolean testaColisaoPlaneta(Planeta planeta, Espaconave ranger){
		return distAB(planeta, ranger) <= planeta.getRaio() + ranger.getAltura()/2;
	}

	public static boolean testaColisao(Lua lua, Espaconave ranger){
		return distAB(lua, ranger) <= lua.getRaio() + ranger.getAltura()/2;
	}

	// usando para calcular distancia entre planeta e espaconave
	public static double distAB(Planeta planeta, Espaconave ranger){
		return Math.sqrt(
				(ranger.getCentroX() - planeta.getCentroX()) *
				(ranger.getCentroX() - planeta.getCentroX()) + 
				(ranger.getCentroY() - planeta.getCentroY()) * 
				(ranger.getCentroY() - planeta.getCentroY()));
	}
	//usando para calcular distancia entre lua e espaconave
	public static double distAB(Lua lua, Espaconave ranger){
		return Math.sqrt(
				(ranger.getCentroX() - lua.getCentroX()) *
				(ranger.getCentroX() - lua.getCentroX()) + 
				(ranger.getCentroY() - lua.getCentroY()) * 
				(ranger.getCentroY() - lua.getCentroY()));
	}
	
	// usando para selecionar planeta na edicao de fase
	public static double distAB(Planeta planeta, int x, int y){		
		return Math.sqrt(
				(x - planeta.getCentroX()) *
				(x - planeta.getCentroX()) + 
				(y - planeta.getCentroY()) * 
				(y - planeta.getCentroY()));
	}
	
	public static double distAB(Lua lua, int x, int y){		
		return Math.sqrt(
				(x - lua.getCentroX()) *
				(x - lua.getCentroX()) + 
				(y - lua.getCentroY()) * 
				(y - lua.getCentroY()));
	}
			

	/**DETERMINA O PONTO EM QUE O MOVIMENTO CIRCULAR DEVE COMECAR*/
	public static double anguloCentral(Espaconave ranger, Planeta planeta){
		double angulo = 0;
		double ux, uy, vx, vy;
		double pVetorial = 0;


		vx = ranger.getCentroX() - planeta.getCentroX();
		vy = ranger.getCentroY() - planeta.getCentroY();

		ux = planeta.getRaio();
		uy = 0;

		//   produto escalar dividido pelo produto dos modulos
		angulo = (ux*vx + uy*vy)/(moduloV(vx, vy) * moduloV(ux, uy));
		angulo = Math.acos(angulo);

		//produto vetorial u v
		pVetorial = (ux * vy - vx * uy);

		/*se o produto vetorial entre o vetor ponto de colisao e
		 * o angulo inicial for < 0 quer dizer que o vetor u está
		 * do lado direito, então o angulo é concavo.
		 * ou seja, maior que 180. usa-se o complemento.
		 */
		if(pVetorial < 0){
			return Math.toRadians(360) - angulo;
		}
		else{
			return angulo;
		}
	}

	/**FUNCAO QUE INDICA EM QUAL SENTIDO A NAVE DEVE ORBITAR*/
	public static boolean sentidoH(Espaconave ranger, Planeta planeta){
		double angulo = 0;
		double ux, uy, vx, vy;

		//	vetor V é o sentido da espaconave
		vx = ranger.getVx();
		vy = ranger.getVy();

		//	vetor U ortogonal ao vetor colisao x centro planeta
		ux = ranger.getCentroY() - planeta.getCentroY();
		uy = -(ranger.getCentroX() - planeta.getCentroX());
		
		//   produto escalar dividido pelo produto dos modulos
		angulo = (ux*vx + uy*vy)/(moduloV(vx, vy) * moduloV(ux, uy));
		angulo = Math.acos(angulo);


		return angulo > Math.toRadians(90);
		
	}
	
	/**DETERMINA SE A NAVE DEVE COLIDIR OU NÃO COM O PLANETA*/
	public static boolean colideOrbita(Espaconave ranger, Planeta planeta){
		double angulo = 0;
		double ux, uy, vx, vy;

		vx = ranger.getVx();
		vy = ranger.getVy();

		ux = ranger.getCentroY() - planeta.getCentroY();
		uy = -(ranger.getCentroX() - planeta.getCentroX());

		angulo = (ux*vx + uy*vy)/(moduloV(vx, vy) * moduloV(ux, uy));
		angulo = Math.acos(angulo);

		//		System.out.println("Angulo é: " + (int)Math.toDegrees(angulo));

		return !(angulo > Math.toRadians(130) || angulo < Math.toRadians(50));
	}

	//usando
	public static double moduloV(double x0, double y0){
		double modulo = 0;
		modulo = Math.sqrt((x0*x0) + (y0*y0));
		return modulo;
	}

	// transforma System.currentTimeMillis() em minuto e segundo
	public static String converteTempo(long tempo){
		int m = (int) TimeUnit.MILLISECONDS.toMinutes(tempo) % 60;
		int s = (int) TimeUnit.MILLISECONDS.toSeconds(tempo) % 60;
		int S = (int) TimeUnit.MILLISECONDS.toMillis(tempo) % 1000;
		return String.format("%02d:%02d.%03d", m, s, S);
	}
	
	
	// realiza os salvamentos antes de fechar o jogo. 
	// deve ser chamada em cada opcao de fechar o jogo
	public static void fecharJogo(){

		ReprodutorDeSom.getInstancia().reproduzir("click");
		Salva.salvaDados();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.exit(0);
	}

}
