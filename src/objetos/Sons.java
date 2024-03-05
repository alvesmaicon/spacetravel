package objetos;

import java.io.Serializable;

public class Sons implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -444604468260524704L;
	private boolean Som = true;
	private boolean Musica = true;
	private float Volume = -10.0f;
	private float VolumeAnterior = this.getVolume();

	public Sons(){
		
	}

	public boolean isSom() {
		return Som;
	}

	public void setSom(boolean som) {
		Som = som;
		//se os dois audios estão desligados, o volume é zerado
		if(!this.Som && !this.Musica){
			VolumeAnterior = this.getVolume();
			this.setVolume(-80.0f);
		}
	
		//se ativou o sou porem a musica esta desativada, volume volta pro anterior
		if(this.Som && !this.Musica){
			this.setVolume(VolumeAnterior);
		}
	}

	public boolean isMusica() {
		return Musica;
	}

	public void setMusica(boolean musica) {
		Musica = musica;
		//se os dois audios foram desligados o volume é zerado
		if(!this.Som && !this.Musica){
			VolumeAnterior = this.getVolume();
			this.setVolume(-80.0f);
		}
		//se a musica for ativada o volume é setado para valor anterior
		if(this.Musica && !this.Som){
			this.setVolume(VolumeAnterior);
		}
	}

	public float getVolume() {
		return Volume;
	}

	//enquanto o volume for menor que -10 ele é incrementado em 14 unidades
	//minimo -80.0 e maximo -10.0
	//repartidos em cinco niveis de volume.
	public void setVolumeAumenta() {
		if(Volume < -10.0f)
			Volume += 14.0f;
		
		//quando aumenta o volume do zero pra cima, os sons sao ativados
		if(Volume == -66.0f){
			Som = true;
			Musica = true;
		}
	}
	
	//o volume é decrementado até o minimo
	public void setVolumeDiminui() {
		if(Volume > -80.0f)
			Volume -= 14.0f;
		
		//se o volume passa do menor nivel, o som é desativado
		if(Volume < -66.0f){
			Som = false;
			Musica = false;
			
		}
	}
	
	public void setVolume(float volume){
		Volume = volume;
	}
	
	
}
