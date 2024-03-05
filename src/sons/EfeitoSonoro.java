package sons;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import janelas.CenarioInicio;

public class EfeitoSonoro {
    
    private File arquivo;
    private AudioInputStream fluxo;
    private AudioFormat formato;
    private Clip audio;
    
    public EfeitoSonoro(String caminho){
        //abre o arquivo de som e identifica seu formato
        arquivo = new File(caminho);
        try{
            fluxo = AudioSystem.getAudioInputStream(arquivo);
        }catch(UnsupportedAudioFileException ex){
            System.out.println("Audio da musica nao suportado");
        }catch(IOException e){
            System.out.println("Erro para abrir arquivo de musica");
        }
        formato = fluxo.getFormat();
        
        //gera um clipe de audio reproduzivel e tenta deixalo aberto para reproducao
        DataLine.Info info = new DataLine.Info(Clip.class, formato);
        try {
            audio = (Clip) AudioSystem.getLine(info);
        } catch (LineUnavailableException ex) {
            System.out.println("Problema na carga da musica");
        }
        try {
            audio.open(fluxo);
        } catch (LineUnavailableException ex) {
            System.out.println("Problema na abertura de fluxo para reproducao da musica");
        } catch (IOException ex) {
            System.out.println("Problema na entrada e saida para reproducao da musica");
        }
     
    }
    
    public void reproduzir(){
        //se estiver reproduzindo, interrompe
        if(audio.isRunning()){
            audio.stop();
        }
        
        
        // controle de volume
        FloatControl controleVolume = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
        controleVolume.setValue(CenarioInicio.audio.getVolume()+ 5.0f);
        
      //volta pro começo do arquivo e reproduz
        audio.setFramePosition(0);
        audio.start();
        
        
        
    }
    
}
