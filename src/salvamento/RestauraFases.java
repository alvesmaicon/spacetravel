package salvamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

import objetos.Fase;

public class RestauraFases {
	
	
	public RestauraFases(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static void RestauraLayoutFases(){
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"salvo/fases_backup.dat"));
			
			estruturas.Fases.vetorFases = (Vector<Fase>) in.readObject();			
			in.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nao existe jogo salvo ainda.",
					"Erro!", JOptionPane.ERROR_MESSAGE);
		}
		
		
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
		
		JOptionPane.showMessageDialog(null, "O layout das fases foi restaurado.", "Pronto", JOptionPane.INFORMATION_MESSAGE);
		
	}

}
