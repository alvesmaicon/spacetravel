package objetos;

import java.io.Serializable;

import janelas.CenarioMulti;
import janelas.CenarioSingle;

public class Espaconave implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2516650899698593816L;
	
	private static int altura = 30;
	private static int largura = 20;
	public static int deslocamento = 2;
	public static int deslocamentoCir = 1;
	public static double deslocamentoLan = 0.3;

	private boolean vagando = false;
	private boolean orbitando = false;
	private boolean lancando = true;
	private boolean morta = false;
	private boolean rotaColisao = false;

	private boolean auxilioRota = false;

	private boolean andandoDireita = false;
	private boolean andandoEsquerda = false;

	private boolean sentidoHorario = false;
	private long tempoMorte = 0;



	//	int segundos = 0;

	public double CentroXInicial, CentroYInicial;
	public double CentroX, CentroY, drawX, drawY;
	public double k = 1;
	public double Alpha = 4.71239, Theta = 0;  /*270 graus em radianos*/


	public double vx = 0, vy = 0, Px = 0, Py = 0;
	public int  i = 0;

	public String nome;
	public int vidasRestantes = 3;

	public Espaconave(String nome, PlanetaInicial PlanetaI){

		this.nome = nome;

		this.CentroXInicial = (PlanetaI.getDrawOrbX() + PlanetaI.getOrbRaio());
		this.CentroYInicial = (PlanetaI.getDrawOrbY());

		this.setCentroX(CentroXInicial);
		this.setCentroY(CentroYInicial);


		drawX = CentroX - largura/2;
		drawY = CentroY - altura/2;


		
	}

	public void setLocalInicial(PlanetaInicial PlanetaI){
		this.CentroXInicial = (PlanetaI.getDrawOrbX() + PlanetaI.getOrbRaio());
		this.CentroYInicial = (PlanetaI.getDrawOrbY());
	}

	/*Configura o centro da espaconave. Se ela passa dos limites 
	 * da tela, aparece do outro lado com a mesma direção*/
	public void setCentroX(double centroX) {
		CentroX = centroX;
		drawX = centroX - largura/2;

		if(CentroX >= 784){
			this.setMorta();
			this.setVagando(false);
			//			setCentroX(-40);
			//			setKZero();
			//			Px = 0;
			//			Py = CentroY;
		}
		if (CentroX <= -50){
			this.setMorta();
			this.setVagando(false);
			//			setCentroX(780);
			//			setKZero();
			//			Px = 780;
			//			Py = CentroY;
		}
	}

	/*Configura o centro Y da espaconave. Se ela alcanca
	 * os limites da tela uma vida é decrementada*/
	public void setCentroY(double centroY) {
		CentroY = centroY;
		drawY = centroY - altura/2;

		if(CentroY <= -25){
			this.setMorta();
			this.setVagando(false);
		}
		if(CentroY >= 560){
			this.setMorta();
			this.setVagando(false);
		}
	}




	//ajusta vetor direcao da espaconave no lancamento
	public void setVxVy(PlanetaInicial planeta) {

		double i = 0, j = 0, modulo = 0;

		i = CentroX - planeta.getCentroX();
		j = CentroY - planeta.getCentroY();

		modulo = Math.sqrt(i*i + j*j);

		this.vx = i/modulo ;
		this.vy = j/modulo ;
	}
	//ajusta o vetor direcao da espaconave na saida de orbita
	public void setVxVy(Planeta planeta) {

		double i = 0, j = 0, modulo = 0;

		i = CentroX - planeta.getCentroX();
		j = CentroY - planeta.getCentroY();

		modulo = Math.sqrt(i*i + j*j);

		//vetor ortogonal ao vetor central ^^ 
		/*gambiarra para o vetor ficar com modulo menor que 1*/
		this.vx = j/(modulo + 10) ;  
		this.vy =  -i/(modulo + 10);
	}

	/*seta o vetor direção da nave para movimento retilineo
	 * no sentido horario e direção oposta  ao vetor acima*/
	public void setVxVyH(Planeta planeta) {

		double i = 0, j = 0, modulo = 0;

		i = CentroX - planeta.getCentroX();
		j = CentroY - planeta.getCentroY();

		modulo = Math.sqrt(i*i + j*j);

		/* vetor ortogonal ao vetor central ^^ 
		 * gambiarra para o vetor ficar com modulo menor que 1 */
		this.vx = -j/(modulo + 10) ;  
		this.vy =  i/(modulo + 10);
	}

	/* Move a nave e ajusta para lancamento de acordo com
	 * o pressionar dos controles*/
	public void setLancamento(PlanetaInicial planeta){
		Theta = Alpha + Math.toRadians(90);

		setCentroX(planeta.getCentroX() + (planeta.getOrbRaio() * Math.cos(Alpha)));
		setCentroY(planeta.getCentroY() + (planeta.getOrbRaio() * Math.sin(Alpha)));

		setVxVy(planeta);

		Px = CentroX;
		Py = CentroY;

	}

	/* Executa movimento circular ao planeta passado*/
	/**	x(t) = cx + r * cos(t)
	 * 	y(t) = cy + r * sen(t) */
	public void setMovimentoCir(Planeta planeta){
		setCentroX(planeta.getCentroX() + (planeta.getOrbRaio() * Math.cos(Theta)));
		setCentroY(planeta.getCentroY() + (planeta.getOrbRaio() * Math.sin(Theta)));

		Px = CentroX;
		Py = CentroY;

		if(sentidoHorario){
			incrementaTheta(); //atualiza t da equação paramétrica do círculo 
			setVxVyH(planeta);
		}
		else{
			decrementaTheta();
			setVxVy(planeta);
		}
	}

	/*Configura o movimento em linha reta de acordo 
	 * com os vetores setados no lancamento ou órbita*/
	/**	x(t) = px + vx * (t) 
	 * 	y(t) = py + vy * (t) */
	public void setMovimentoRet(){

		setCentroX(Px + (vx * k));
		setCentroY(Py + (vy * k));

		k += deslocamento; //atualiza o t da equação paramétrica da reta
	}

	/* Reinicia o k para 1, assim não ocorre colisão ao sair
	 * da órbita e o movimento inicia do ponto de saída*/
	public void setKZero(){
		this.k = 1;
	}


	public double getVy() {
		return vy;
	}

	public double getVx() {
		return vx;
	}







	//Revive a Espaconave depois de uma pausa
	public void reviveEspaconave(){
		if((System.currentTimeMillis() - tempoMorte) >= 1000L){
			this.resetaEspaconave(); 
			this.setViva();
		}
	}

	public void setMorta() {
		this.decrementaVidas();
		this.morta = true;
		this.tempoMorte = System.currentTimeMillis();
		CenarioSingle.planetaAnterior = "string1";
		CenarioSingle.planetaAtual = "string2";
		CenarioMulti.planetaAnterior[0] = "string1";
		CenarioMulti.planetaAnterior[1] = "string2";
		CenarioMulti.planetaAtual[0] = "string3";
		CenarioMulti.planetaAtual[1] = "string4";

		//TODO Pausa de movimento ao morrer para voltar ao inicio


	}

	public boolean isMorta() {
		return morta;
	}

	public void setViva() {
		this.morta = false;

	}

	public boolean isAuxilioRota() {
		return auxilioRota;
	}


	public void setAuxilioRota(boolean auxilioRota) {
		this.auxilioRota = auxilioRota;
	}

	public boolean isSentidoHorario() {
		return sentidoHorario;
	}


	public void setSentidoHorario(boolean sentidoHorario) {
		this.sentidoHorario = sentidoHorario;
	}



	public boolean isAndandoDireita() {
		return andandoDireita;
	}


	public void setAndandoDireita(boolean andandoDireita) {
		this.andandoDireita = andandoDireita;
	}


	public boolean isAndandoEsquerda() {
		return andandoEsquerda;
	}


	public void setAndandoEsquerda(boolean andandoEsquerda) {
		this.andandoEsquerda = andandoEsquerda;
	}


	/*Reseta todos atributos variaveis do objeto espaconave*/
	public void resetaEspaconave(){
		this.Alpha = 4.71239;

		this.vagando = false;
		this.orbitando = false;
		this.lancando = true;
		this.morta = false;
		this.sentidoHorario = false;
		this.rotaColisao = false;
		this.i = 0;
		this.k = 1;
		vx = 0;
		vy = 0;
		Px = 0;
		Py = 0;
		this.setCentroX(CentroXInicial);
		this.setCentroY(CentroYInicial);
	}


	public int getVidasRestantes() {
		return vidasRestantes;
	}
	public void decrementaVidas(){
		vidasRestantes -= 1;
	}

	public void resetVidasRestantes() {
		this.vidasRestantes = 3;
	}

	public double getDrawX() {
		return drawX;
	}


	public double getDrawY() {
		return drawY;
	}


	public double getCentroX() {
		return this.CentroX;
	}


	public double getCentroY() {
		return this.CentroY;
	}





	public double getAlpha() {
		return Alpha;
	}


	public void incrementaAlpha(){
		this.Alpha += Math.toRadians(deslocamentoLan);
	}


	public void decrementaAlpha(){
		this.Alpha -= Math.toRadians(deslocamentoLan);
	}


	public double getTheta() {
		return Theta;
	}


	public void setTheta(double theta) {
		this.Theta = (theta);
	}


	public void incrementaTheta(){
		this.Theta += Math.toRadians(deslocamentoCir);
	}


	public void decrementaTheta(){
		this.Theta -= Math.toRadians(deslocamentoCir);
	}


	public String getNome() {
		return nome;
	}


	public int getAltura(){
		return altura;
	}

	public int getLargura(){
		return largura;
	}


	public boolean isVagando() {
		return vagando;
	}

	public void setVagando(boolean vagando) {
		this.vagando = vagando;
	}



	public boolean isOrbitando() {
		return orbitando;
	}


	public void setOrbitando(boolean orbitando) {
		this.orbitando = orbitando;
	}


	public boolean isLancando() {
		return lancando;
	}


	public void setLancando(boolean lancando) {
		this.lancando = lancando;
	}


	public boolean isRotaColisao() {
		return rotaColisao;
	}


	public void setRotaColisao(boolean rotaColisao) {
		this.rotaColisao = rotaColisao;
	}

	/**CENARIO*/
	/* O cenario tem 800 pixels de largura e 550 pixels de altura.
	 * Por enquanto eh estatico. A lateral eh continua, dando a 
	 * possibilidade de o player sair de um lado e aparecer do outro
	 * lado da tela.*/

	/**EXIBICAO DE PLAYER E PLANETAS*/
	/* Todos objetos tem posicao descrita por Centro X e centro Y.
	 * O ponto de renderizacao eh o superior esquerdo, e eh configurado
	 * de acordo com o centro do objeto, sendo igual ao ponto central
	 * menos o raio (metade da largura ou altura).*/

	/** ESTILO DE CARREGAMENTO DE IMAGENS PARA USO EM .JAR*/
	/* As imagens foram movidas para uma subpasta da pasta raiz.
	 * Porem gostaria de inclui-las no .jar*/

	/**MOVIMENTO DO PLAYER E LEITOR DE TECLADO*/
	/* A iteracao do jogador com o jogo eh composta por tres botoes.
	 * O movimento do player eh dado pela incrementacao ou decementacao
	 * da variavel ALPHA ou k. A alpha eh o instante t para o movimento circular,
	 * e o k eh o instante t para o movimento reto.
	 * Alpha incrementado com a seta direita e decrementado com a seta esquerda,
	 * e k eh implementado automaticamente para descrever o movimento reto.
	 * A barra de espaco inicia o movimento reto de acordo com o angulo dado
	 * pela origem no centro do planeta inicial, e o ponto da circunferencia
	 * com instante alpha. O vetor de lancamento tem origem no centro e direcao 
	 * do ponto alpha, e o vetor de saida de orbita eh o vetor ortogonal ao vetor
	 * centro partida. O sentido do vetor tangente eh dado por horario ou 
	 * anti-horario*/

	/**MOVIMENTO DAS LUAS*/
	/* As luas giram com ambos sentidos e com deslocamento configuravel.
	 * Ao criar a lua eh informado qual objeto planeta orbitar, e o movimento
	 * eh descrito pela paranormal do circulo com instante t*/

	/**DETECCAO DE COLISAO E TRATAMENTO*/
	/* A entrada na orbita eh dada pela distancia dos centros entre o
	 * centro da nave e o centro do planeta. Se a distancia for menor que 
	 * o raio da orbita, entao o planeta eh detectado como planeta para orbitar
	 * ou colidir.*/

	/**PONTO EXATO EM QUE A NAVE COMEÇA ORBITAR
	 * O PLANETA E O SENTIDO DE ACORDO COM O ANGULO DE ENTRADA*/
	/* Concluido com angulo interno da circunferencia entre vetores.
	 * O calculo do angulo interno gerava um angulo menor que 180 
	 * graus, então foi usada a posicao dos vetores para identificar um 
	 * angulo concavo (maior que 180 graus)
	 * O sentido de rotacao eh dado pelo angulo de entrada a orbita, sendo
	 * sentido horario para angulo maior que 130 graus, e anti horario para 
	 * menor que 50 graus. Se o angulo de entrada a orbita estiver entre os
	 * angulos citados anteriormente, eh detectada rota de colisao com o 
	 * planeta.*/

	/**SAIDA DE ORBITA */
	/* A direcao de saida da orbita do planeta eh dada pelo vetor
	 * tangente ao ponto de partida.*/

	/**TODO IMPLEMENTAR MELHOR SIMULACAO DE ATRACAO DE GRAVIDADE*/

	/**CHOQUE COM LUAS*/
	/* O choque com as luas eh detectado se a distancia entre o centro
	 * da nave e o centro da lua for menor que a soma dos raios.*/

	/**CHEGADA AO PLANETA FINAL*/
	/* A chegada ao planeta final é um teste para cada planeta orbitado.
	 * Se o nome do planeta for "final", entao o planeta eh o planeta final,
	 * e o objetivo foi alcandado.*/

	/**ESQUEMA DE VIDAS E RETROCEÇO AO LANCAMENTO*/
	/* Quando a nave eh dada como "morta", uma vida eh perdida e os atributos
	 * variaveis de posicao da nave sao resetados.*/

	/**TODO melhorar a descricao de tempo. Como inicio da contagem e precisao*/
	/**DESCRIÇÕES DE STATUS ATUAL. COMO TEMPO ATUAL E NUMERO DE VIDAS*/
	/* As vidas sao exibidas no canto inferior esquerdo da tela.
	 * O tempo de jogo eh dado com tempo atual da conclusao menos o tempo
	 * inicial de jogo.*/

	/** TODO IMPLEMENTAR OS BURACOS NEGROS*/
	/* Puxam a nave sem a possibilidade de saida de orbita.
	 * Algo como diminuir o raio de orbita ate a nave girar no proprio eixo
	 * e ser setada como morta.*/

	/** ANIMACAO DE MORTE*/
	/* Um sprite de explosao é exibido quando a espaconave esta morta.*/

	/**TODO IMPLEMENTAR MUSICAS DO JOGO*/
	/* As musicas do jogo serao trechos de Claudia Lewis - M83 e
	 * In Front of Me - Infected Mushroom.*/

	/** TODO IMPLEMENTAR EFEITOS SONOROS*/

	/**MULTI-PLAYER*/
	/* O modo multi-player foi implementado com a adicao de mais um
	 * planeta inicial e mais uma nave aos vetores de objetos, e adicao
	 * de leitores de teclado. Suas posicoes podem variar e sao configuradas
	 * na criacao de cada objeto.*/

	/**LEITURA DE TECLA MULTI-PLAYER*/
	/* Quando tecla for pressionada andar, quando tecla for soltada parar*/

	/** TODO IMPLEMENTAR MENUS E CARCACA DO JOGO*/ 
	/*TA QUASE CARAIS*/
	/* Fluxo de telas e estados do jogo*/

	/**TODO IMPLEMENTAR NIVEIS DE DIFICULDADE*/
	/* De acordo com o código atual, um vetor de elementos do jogo
	 * para cada dificuldade. com descricao de posicao e atualizacao 
	 * dos objetos. Os desafios do jogo estao vinculados a distancia e 
	 * tamanho dos planetas, sentido de rotacao e quantidade de luas*/

	/**TODO IMPLEMENTAR SERIALIZAÇÃO E DESCRIÇÃO DE NIVEL DE JOGO*/
	/* Um menu com os niveis e a descricao com informacoes de jogo
	 * como: se o nivel foi concluido, melhor tempo, quantidade de mortes*/

	/**TODO IMPLEMENTAR MODO CARREIRA E CADASTRO DE SALVAMENTO*/
	/* Possivelmente um objeto serializavel salvamento.*/

	/**TODO IMPLEMENTAR SAVE E LOAD DE STATUS DO JOGADOR*/
	/* Possivelmente um objeto serializavel com status do jogador */

	/**TODO IMPLEMENTAR SURPRESA NO JOGO*/
	/* Atualmente a surpresa do jogo pode ser definida com um tempo
	 * de conclusao da fase e uma nova fase eh desbloqueada.*/

	/**TODO ALTERAR ESQUEMA DE CARREGAMENTO DOS MAPAS*/
	/* Ideia de atualizar para vetores serializados ou ler de arqui
	 * vos de texto o nivel inteiro. Acho que esse é mais dificil.*/
}

