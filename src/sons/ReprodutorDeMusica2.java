package sons;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import janelas.CenarioInicio;

/*
 * NÃO SUPORTA MP3
 */

/**
 *
 * @author jeancheiran, maiconalves
 * 
 */
public class ReprodutorDeMusica2 extends Thread {

	private AudioFormat formato;
	private byte[] amostras;
	private static boolean recomeca = false;


	public ReprodutorDeMusica2(String arquivo){
		try{
			//abre o fluxo (stream) para o arquivo de audio
			AudioInputStream fluxo = AudioSystem.getAudioInputStream(new File(arquivo));
			//carrega formato e amostras (os dados do som em si)
			this.carregarSom(fluxo);
		}catch(UnsupportedAudioFileException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public byte[] getAmostras(){
		return this.amostras;
	}


	public void recomeca(){
		recomeca = true;
	}

	public void carregarSom(AudioInputStream fluxoDeAudio){
		//carrega o formato do som
		this.formato = fluxoDeAudio.getFormat();

		//pega numero de bytes para ler
		int tamanho = (int) (fluxoDeAudio.getFrameLength() * this.formato.getFrameSize());

		//lê o fluxo inteiro
		byte[] amostrasLidas = new byte[tamanho];
		DataInputStream fluxoDeDados = new DataInputStream(fluxoDeAudio);
		try{
			fluxoDeDados.readFully(amostrasLidas);
		}catch(IOException e){
			e.printStackTrace();
		}

		//carregaas as amostras da musica
		this.amostras = amostrasLidas;
	}

	public void play(InputStream fonte){
		// usar valores abaixo de 100ms no buffer garante tempo real
		// muda para o fluxo de som
		int tamanhoDoBuffer = formato.getFrameSize() * Math.round(formato.getSampleRate() / 10);
		byte[] buffer = new byte[tamanhoDoBuffer];

		SourceDataLine line;
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, formato);
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(formato, tamanhoDoBuffer);
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}

		// start the line
		line.start();
		// controla o volume
		FloatControl controleVolume = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
				
		// copy data to the line
		try {
			int numBytesRead = 0;
			controleVolume.setValue(CenarioInicio.audio.getVolume());
			while (true) {
				if(CenarioInicio.audio.isMusica()){
					if(recomeca){
						fonte.reset();
						recomeca = false;
					}
					numBytesRead = fonte.read(buffer, 0, buffer.length);
					if (numBytesRead != -1) {
						line.write(buffer, 0, numBytesRead);
					}
					if(numBytesRead == -1){
						fonte.reset();
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// wait until all data is played
		line.drain();

		// close the line
		line.close();
	}

	@Override
	public void run(){
		// cria fluxo de reprodução
		InputStream fluxo = new ByteArrayInputStream(this.amostras);

		// toca o som
		this.play(fluxo);

	}



}
