/**SPACE TRAVEL
 * 
 * @author MaiconAlves
 * */

package janelas;

import java.awt.EventQueue;

import salvamento.Carrega;

public class Jogo {
	
    
    public static Janela janela = null;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
            	new estruturas.Single();
            	new estruturas.Multi();
            	new estruturas.Fases();
            	
            	Carrega.carregaDados();
            	
                Janela janela = new Janela();
                
                janela.setVisible(true);
                
            }
        });
    }
    
}

