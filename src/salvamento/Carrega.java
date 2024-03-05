package salvamento;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

import objetos.Fase;
import objetos.Sons;


public class Carrega {

	public Carrega() {
		
	}
	
	public static void carregaDados(){
		carregaConfigAudio();
		carregaFases();
	}
	
	public static void carregaConfigAudio(){
		try {
			ObjectInputStream in= new ObjectInputStream(new FileInputStream(
					"salvo/somconfig.dat"));
			
			janelas.CenarioInicio.audio = (Sons) in.readObject();			
			in.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nao foi possivel carregar config de som.",
					"Erro!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void carregaFases(){
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"salvo/fases.dat"));
			
			estruturas.Fases.vetorFases = (Vector<Fase>) in.readObject();			
			in.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nao existe jogo salvo ainda.",
					"Erro!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
