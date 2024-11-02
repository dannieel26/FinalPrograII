
package com.mycompany.proyectofinal;

import javax.swing.JComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author Pablo
 */
public class Reproductor {
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private boolean isPaused = false; // estado para controlar la pausa

    public Reproductor() {
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    }
    
    public void reproducir(String archivo){
        if (isPaused){
            mediaPlayerComponent.mediaPlayer().controls().play(); // reanudar
            isPaused = false;
        } else {
            mediaPlayerComponent.mediaPlayer().media().play(archivo); //reproducir desde el inicio
            isPaused = false;
        }
    }
    
    public void pausar(){
        mediaPlayerComponent.mediaPlayer().controls().pause();
        isPaused = true;
    }
    
    public void detener(){
        mediaPlayerComponent.mediaPlayer().controls().stop();
        isPaused = false;
    }
    
    //Método para obtener el componente de reproduccion para integrar a la interfaz
    public JComponent getMediaPlayerComponent(){
        return mediaPlayerComponent;
    }
}
