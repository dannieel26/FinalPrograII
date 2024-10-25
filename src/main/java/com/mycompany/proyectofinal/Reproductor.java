
package com.mycompany.proyectofinal;

import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

/**
 *
 * @author Pablo
 */
public class Reproductor {
    private AudioPlayerComponent player;

    public Reproductor() {
        player = new AudioPlayerComponent();
    }
    
    //Método para reproducir un archivo de audio
    public void reproducirArchivo(String rutaArchivo){
        player.mediaPlayer().media().play(rutaArchivo);
    }
    
    public void pausarArchivo(){
        player.mediaPlayer().controls().pause();
    }
    
    public void detenerArchivo(){
        player.mediaPlayer().controls().stop();
    }
}
