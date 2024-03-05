package sons;

import janelas.CenarioInicio;

public class ReprodutorDeSom extends Thread {

	//para implementacao do padrao singleton da classe
	private static ReprodutorDeSom instancia = null;

	private EfeitoSonoro[] sons = null;

	private ReprodutorDeSom(){
		this.sons = new EfeitoSonoro[7];
		sons[0] = new EfeitoSonoro("sons/propulsao.wav");
		sons[1] = new EfeitoSonoro("sons/explosao.wav");
		sons[2] = new EfeitoSonoro("sons/propulsao_falhou.wav");
		//        sons[3] = new EfeitoSonoro("sons/lose.wav");
		sons[4] = new EfeitoSonoro("sons/win.wav");
		sons[5] = new EfeitoSonoro("sons/click_botao.wav");
		sons[6] = new EfeitoSonoro("sons/click.wav");
	}

	//obtem o controlador de som (cria um se nao existir)
	public static ReprodutorDeSom getInstancia(){
		if(instancia == null){
			instancia = new ReprodutorDeSom();
		}
		return instancia;
	}

	//reproduz um som
	public void reproduzir(String som){
		if(CenarioInicio.audio.isSom()){
			if(som.equals("propulsao")){
				sons[0].reproduzir();
			}
			if(som.equals("explosao")){
				sons[1].reproduzir();
			}
			if(som.equals("prop_falhou")){
				sons[2].reproduzir();
			}
			//        if(som.equals("perdeu")){
			//            sons[3].reproduzir();
			//        }

			if(som.equals("venceu")){
				sons[4].reproduzir();
			}

			if(som.equals("click")){
				sons[5].reproduzir();
			}

			if(som.equals("auxilio")){
				sons[6].reproduzir();
			}
		}
	}


}
