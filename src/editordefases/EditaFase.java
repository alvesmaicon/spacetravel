package editordefases;

import java.awt.Container;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import estruturas.Fases;
import janelas.Janela;
import objetos.Fase;
import objetos.Lua;
import objetos.Planeta;

public class EditaFase extends JFrame implements ActionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 9022894014583224540L;
	

	private JButton botaoSalvarEdicao, botaoMoveInicial, 
	botaoMoverPlaneta, botaoAumentaRaio, botaoDiminuiRaio;

	private static JButton botaoSelecionarLua;

	private static JButton botaoSentidoRotacaoLua;
	
	private static JButton botaoSelecionarPlaneta;

	private JButton botaoAumentaDeslocamentoLua;

	private JButton botaoDiminuiDeslocamentoLua;

	private JButton botaoReiniciaLuas;

	private JLabel moverPlaneta, nomePlaneta, editarPlanetaInicial, posicaoPlaneta, tamanhoRaio,
	editarLuas, nomeLua, deslocamentoLua;

	public static JTextField fieldNomePlaneta, fieldPosicaoPlaneta, fieldRaio, fieldNomeLua,
	fieldDeslocamentoLua;


	Image iconeJanela = Toolkit.getDefaultToolkit().getImage("imagens/icon.png");


	private static Planeta planetaSelecionado;
	private static Lua luaSelecionada;
	private static String labelAnterior;

	private Container content;
	public static Fase fase;

	


	public static boolean Editando = false;

	public static boolean movendoPlanetaInicial = false, selecionandoPlaneta = false, 
			movendoPlaneta = false, selecionandoLua = false;

	public EditaFase(int indice){
		super("Editar");

		content = getContentPane();
		content.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(iconeJanela);
		this.setSize(220, 570);
		this.setLocation(810, 5);
		this.setResizable(false);
		this.setVisible(true);




		fase = new Fase("Fase " + (indice + 1));
		fase = (Fase) Fases.vetorFases.elementAt(indice);
		
		
		CenarioEdicao.planetaInicial = fase.getPlanetaInicial();
		atualizaLuas();




		editarPlanetaInicial = new JLabel("Editar planeta inicial");
		editarPlanetaInicial.setBounds(new Rectangle(10, 10, 185, 25));
		content.add(editarPlanetaInicial, null);


		botaoMoveInicial = new JButton("Mover planeta inicial");
		botaoMoveInicial.setBounds(new Rectangle(10, 40, 185, 25));
		botaoMoveInicial.setActionCommand("INICIAL");
		botaoMoveInicial.addActionListener(this);
		content.add(botaoMoveInicial, null);

		moverPlaneta = new JLabel("Editar planetas orbitáveis");
		moverPlaneta.setBounds(new Rectangle(10, 90, 185, 25));
		content.add(moverPlaneta, null);

		botaoSelecionarPlaneta = new JButton("Selecionar planeta");
		botaoSelecionarPlaneta.setBounds(new Rectangle(10, 120, 185, 25));
		content.add(botaoSelecionarPlaneta, null);
		botaoSelecionarPlaneta.setActionCommand("SELECIONARP");
		botaoSelecionarPlaneta.addActionListener(this);		

		nomePlaneta = new JLabel("Nome:");
		nomePlaneta.setBounds(new Rectangle(10, 150, 40, 25));
		content.add(nomePlaneta);

		fieldNomePlaneta = new JTextField();
		fieldNomePlaneta.setBounds(new Rectangle(70, 150, 125, 25));
		fieldNomePlaneta.setEditable(false);
		content.add(fieldNomePlaneta);

		botaoMoverPlaneta = new JButton("Mover planeta");
		botaoMoverPlaneta.setBounds(new Rectangle(10, 180, 185, 25));
		content.add(botaoMoverPlaneta, null);
		botaoMoverPlaneta.setActionCommand("MOVERP");
		botaoMoverPlaneta.addActionListener(this);

		posicaoPlaneta = new JLabel("Posição: ");
		posicaoPlaneta.setBounds(new Rectangle(10, 210, 53, 25));
		content.add(posicaoPlaneta, null);

		fieldPosicaoPlaneta = new JTextField();
		fieldPosicaoPlaneta.setBounds(new Rectangle(70, 210, 125, 25));
		fieldPosicaoPlaneta.setEditable(false);
		content.add(fieldPosicaoPlaneta, null);

		tamanhoRaio = new JLabel ("Raio (px):");
		tamanhoRaio.setBounds(10, 240, 60, 25);
		content.add(tamanhoRaio, null);

		fieldRaio = new JTextField();
		fieldRaio.setBounds(new Rectangle(70, 240, 33, 25));
		fieldRaio.setEditable(false);
		content.add(fieldRaio, null);

		botaoAumentaRaio = new JButton("+");
		botaoAumentaRaio.setBounds(new Rectangle(154, 240, 41, 25));
		content.add(botaoAumentaRaio, null);
		botaoAumentaRaio.setActionCommand("AUMENTAR");
		botaoAumentaRaio.addActionListener(this);

		botaoDiminuiRaio = new JButton("-");
		botaoDiminuiRaio.setBounds(new Rectangle(108, 240, 41, 25));
		content.add(botaoDiminuiRaio, null);
		botaoDiminuiRaio.setActionCommand("DIMINUIR");
		botaoDiminuiRaio.addActionListener(this);

		editarLuas = new JLabel("Editar luas");
		editarLuas.setBounds(new Rectangle(10, 280, 185, 25));
		content.add(editarLuas, null);

		botaoSelecionarLua = new JButton("Selecionar lua");
		botaoSelecionarLua.setBounds(new Rectangle(10, 310, 185, 25));
		content.add(botaoSelecionarLua, null);
		botaoSelecionarLua.setActionCommand("SELECIONARL");
		botaoSelecionarLua.addActionListener(this);		

		nomeLua = new JLabel("Nome:");
		nomeLua.setBounds(new Rectangle(10, 340, 40, 25));
		content.add(nomeLua);

		fieldNomeLua = new JTextField();
		fieldNomeLua.setBounds(new Rectangle(70, 340, 125, 25));
		fieldNomeLua.setEditable(false);
		content.add(fieldNomeLua);

		botaoSentidoRotacaoLua = new JButton("Sentido deslocamento");
		botaoSentidoRotacaoLua.setBounds(new Rectangle(10, 370, 185, 25));
		content.add(botaoSentidoRotacaoLua, null);
		botaoSentidoRotacaoLua.setActionCommand("SENTIDOL");
		botaoSentidoRotacaoLua.addActionListener(this);

		deslocamentoLua = new JLabel("Desloc.:");
		deslocamentoLua.setBounds(new Rectangle(10, 400, 70, 25));
		content.add(deslocamentoLua);

		fieldDeslocamentoLua = new JTextField();
		fieldDeslocamentoLua.setBounds(new Rectangle(70, 400, 33, 25));
		fieldDeslocamentoLua.setEditable(false);
		content.add(fieldDeslocamentoLua, null);

		botaoAumentaDeslocamentoLua = new JButton("+");
		botaoAumentaDeslocamentoLua.setBounds(new Rectangle(154, 400, 41, 25));
		content.add(botaoAumentaDeslocamentoLua, null);
		botaoAumentaDeslocamentoLua.setActionCommand("RAPIDO");
		botaoAumentaDeslocamentoLua.addActionListener(this);

		botaoDiminuiDeslocamentoLua = new JButton("-");
		botaoDiminuiDeslocamentoLua.setBounds(new Rectangle(108, 400, 41, 25));
		content.add(botaoDiminuiDeslocamentoLua, null);
		botaoDiminuiDeslocamentoLua.setActionCommand("DEVAGAR");
		botaoDiminuiDeslocamentoLua.addActionListener(this);

		botaoReiniciaLuas = new JButton("Reiniciar luas");
		botaoReiniciaLuas.setBounds(new Rectangle(10, 430, 185, 25));
		content.add(botaoReiniciaLuas, null);
		botaoReiniciaLuas.setActionCommand("REINICIAL");
		botaoReiniciaLuas.addActionListener(this);

		botaoSalvarEdicao = new JButton("Salvar edição");
		botaoSalvarEdicao.setBounds(new Rectangle(10, 490, 185, 25));
		content.add(botaoSalvarEdicao, null);
		botaoSalvarEdicao.setActionCommand("SALVA");
		botaoSalvarEdicao.addActionListener(this);



	}
	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = (String) event.getActionCommand();

		/** MOVE PLANETA INICIAL*/
		if(comando.equals("INICIAL")){
			// só ativa movimento do planeta inicial se não existir edição ativa
			if(!Editando || movendoPlanetaInicial){
				// altera o texto do botao. Se for Mover planeta Inicial, muda para OK e visse-versa
				// se o texto do botao estiver OK, desativa edição, se não, ativa edição
				if(botaoMoveInicial.getText().equals("OK")){
					//se no botão estiver OK,  desativa edicao e desativa movimento
					movendoPlanetaInicial = false;
					Editando = false;
				}
				// se no botao estiver escrito Mover planeta inicial, ativa edicao e ativa movimento
				else{
					movendoPlanetaInicial = true;
					Editando = true;
				}
				// atualiza o texto do botao apos o clique
				atualizaLabel(botaoMoveInicial);

			}
			// se existir edicao ativa, e não for do planeta inicial é exibida mensagem
			else{
				JOptionPane.showMessageDialog(getParent(), "Termine uma edição primeiro.");
			}
		}

		if(comando.equals("SELECIONARP")){
			if(!Editando || selecionandoPlaneta){
				if(!botaoSelecionarPlaneta.getText().equals("OK")){
					selecionandoPlaneta = true;
					Editando = true;
				}
				else{
					selecionandoPlaneta = false;
					Editando = false;
				}
				atualizaLabel(botaoSelecionarPlaneta);
			}

			else{
				JOptionPane.showMessageDialog(getParent(), "Termine uma edição primeiro.");
			}
		}

		if(comando.equals("MOVERP")){
			if(!Editando || movendoPlaneta){
				if(!botaoMoverPlaneta.getText().equals("OK")){
					if(fieldNomePlaneta.getText().equals("") || fieldNomePlaneta.getText() == null){
						JOptionPane.showMessageDialog(getParent(), "Selecione um planeta para editar.");
					}
					else{
						movendoPlaneta = true;
						Editando = true;
						atualizaLabel(botaoMoverPlaneta);
					}

				}
				else{
					movendoPlaneta = false;
					Editando = false;
					atualizaLabel(botaoMoverPlaneta);
				}
			}
			else{
				JOptionPane.showMessageDialog(getParent(), "Termine uma edição primeiro.");
			}
		}

		if(comando.equals("AUMENTAR")){
			if(fieldNomePlaneta.getText().equals("") || fieldNomePlaneta.getText() == null){
				JOptionPane.showMessageDialog(getParent(), "Selecione um planeta para editar.");
			}
			else{
				aumentaPlaneta();
				atualizaPlanetaDaLua();
			}

		}

		if(comando.equals("DIMINUIR")){
			if(fieldNomePlaneta.getText().equals("") || fieldNomePlaneta.getText() == null){
				JOptionPane.showMessageDialog(getParent(), "Selecione um planeta para editar.");
			}
			else{
				diminuiPlaneta();
				atualizaPlanetaDaLua();
			}
		}



		if(comando.equals("SELECIONARL")){
			// só ativa seleção de lua se não existir edição ativa
			if(!Editando || selecionandoLua){
				// altera o texto do botao. Se for Selecionar lua, muda para OK e visse-versa
				// se o texto do botao estiver OK, desativa edição, se não, ativa edição
				if(botaoSelecionarLua.getText().equals("OK")){
					//se no botão estiver OK,  desativa edicao e desativa movimento
					selecionandoLua = false;
					Editando = false;
				}
				// se no botao estiver escrito Mover planeta inicial, ativa edicao e ativa movimento
				else{
					selecionandoLua = true;
					Editando = true;
				}
				// atualiza o texto do botao apos o clique
				atualizaLabel(botaoSelecionarLua);
			}
			// se existir edicao ativa, e não for da lua é exibida mensagem
			else{
				JOptionPane.showMessageDialog(getParent(), "Termine uma edição primeiro.");
			}
		}

		if(comando.equals("RAPIDO")){
			// só usa os comandos de aumentar ou diminuir velocidade da lua se estiver selecionada
			if(fieldNomeLua.getText().equals("") || fieldNomeLua.getText() == null){
				JOptionPane.showMessageDialog(getParent(), "Selecione uma lua para editar.");
			}
			else{
				luaSelecionada.aumentaDeslocamento();
				fieldDeslocamentoLua.setText(" " + luaSelecionada.getDeslocamento());
			}

		}

		if(comando.equals("DEVAGAR")){
			if(fieldNomeLua.getText().equals("") || fieldNomeLua.getText() == null){
				JOptionPane.showMessageDialog(getParent(), "Selecione uma lua para editar.");
			}
			else{
				luaSelecionada.diminuiDeslocamento();
				fieldDeslocamentoLua.setText(" " + luaSelecionada.getDeslocamento());
			}
		}

		if(comando.equals("SENTIDOL")){
			if(botaoSentidoRotacaoLua.getText().equals("Sentido deslocamento")){
				JOptionPane.showMessageDialog(getParent(), "Selecione uma lua para editar.");
			}
			else{
				if(botaoSentidoRotacaoLua.getText().equals("Sentido horário")){
					luaSelecionada.setSentidoHorario(false);
					botaoSentidoRotacaoLua.setText("Sentido anti-horário");
				}
				else{
					luaSelecionada.setSentidoHorario(true);
					botaoSentidoRotacaoLua.setText("Sentido horário");
				}
			}

		}

		if(comando.equals("REINICIAL")){
			for(int i = 0; i < fase.getVetorLuas().size(); i++){
				fase.getVetorLuas().elementAt(i).resetLua();
			}
		}

		if(comando.equals("SALVA")){
			movendoPlanetaInicial = false;
			janelas.CenarioSobre.editorDeFase.setVisible(true);
			Janela.cenarioEdicao.setVisible(false);
			resetaBooleans();

			this.dispose();
		}


	}


	/** FUNCOES EXTRAS*/
	private static void atualizaLabel(JButton botao){
		if(botao.getText().equals("OK")){
			botao.setText(labelAnterior);
		}
		else{
			labelAnterior = botao.getText();
			botao.setText("OK");
		}
	}

	private void atualizaLuas(){
		CenarioEdicao.vetorLuas = fase.getVetorLuas();
	}

	public static void setPlanetaSelecionado(Planeta planeta){
		planetaSelecionado = planeta;
		
		// clique do mouse desativa edição
		atualizaLabel(botaoSelecionarPlaneta);
		selecionandoPlaneta = false;
		Editando = false;

		// atualiza os fields do planeta
		fieldNomePlaneta.setText(" " + planeta.getNome());			
		fieldPosicaoPlaneta.setText(" (" + (int) planeta.getCentroX() + ", " + (int) planeta.getCentroY() + ")");
		fieldRaio.setText(" " + planeta.getRaio());		
	}

	public static void setLuaSelecionada(Lua lua){
		luaSelecionada = lua;
		
		// o clique do mouse desativa edição
		atualizaLabel(botaoSelecionarLua);
		selecionandoLua = false;
		Editando = false;

		// seta o botao de sentido de rotacao
		if(lua.getSentidoHorario()){
			botaoSentidoRotacaoLua.setText("Sentido horário");
		}
		else{
			botaoSentidoRotacaoLua.setText("Sentido anti-horário");
		}

		// atualiza os fields da lua
		fieldNomeLua.setText(" " + lua.getNome() + " do " + lua.getPlanetaDestaLua().getNome());			
		fieldDeslocamentoLua.setText(" " + lua.getDeslocamento());		
	}

	public static Lua getLuaSelecionada(){
		return luaSelecionada;
	}

	public static Planeta getPlanetaSelecionado(){
		return planetaSelecionado;
	}

	private void aumentaPlaneta(){
		planetaSelecionado.aumentaRaio();
		fieldRaio.setText(" " + planetaSelecionado.getRaio());
	}

	private void diminuiPlaneta(){
		planetaSelecionado.diminuiRaio();
		fieldRaio.setText(" " + planetaSelecionado.getRaio());
	}

	public static void atualizaPlanetaDaLua(){

		for(int i = 0; i < fase.getVetorLuas().size(); i++){
			Lua lua = fase.getVetorLuas().elementAt(i);
			if(lua.getPlanetaDestaLua().getNome().equals(EditaFase.getPlanetaSelecionado())){

				for(int j = 0; j < fase.getVetorPlanetas().size(); j++){
					Planeta planeta = fase.getVetorPlanetas().elementAt(j);
					if(planeta.getNome().equals(planetaSelecionado)){
						fase.getVetorLuas().elementAt(i).setPlanetaDestaLua(planeta);
						break;
					}
				}

			}

		}
	}

	private void resetaBooleans(){
		Editando = false;
		movendoPlaneta = false;
		movendoPlanetaInicial = false;
		selecionandoPlaneta = false;
		movendoPlanetaInicial = false;
		selecionandoLua = false;
	}

	
}
