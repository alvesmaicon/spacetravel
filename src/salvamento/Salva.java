package salvamento;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

public class Salva {

	public Salva() {
	
	}
	
	public static void salvaDados(){
		
		salvaConfigAudio();
		salvaFases();
		
	}
	
	public static void salvaConfigAudio(){
		try {

			File pasta = new File("salvo");
			File arquivo = new File("salvo/somconfig.dat");

			if (!arquivo.exists() || !pasta.exists()) {
				pasta.mkdirs();
				arquivo.createNewFile();
			}

			OutputStream out = new FileOutputStream(arquivo);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

			objectOutputStream.writeObject(janelas.CenarioInicio.audio);
			objectOutputStream.close();
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível salvar a config de som.",
					"Ocorreu um problema!", JOptionPane.ERROR_MESSAGE);
			
		}
	}
	
	public static void salvaFases(){
		try {

			File pasta = new File("salvo");
			File arquivo = new File("salvo/fases.dat");

			if (!arquivo.exists() || !pasta.exists()) {
				pasta.mkdirs();
				arquivo.createNewFile();
			}

			OutputStream out = new FileOutputStream(arquivo);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

			objectOutputStream.writeObject(estruturas.Fases.vetorFases);
			objectOutputStream.close();
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problema ao salvar progresso do jogo.",
					"Ocorreu um problema!", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
}