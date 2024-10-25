
package com.mycompany.proyectofinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * @author Pablo
 */
public class Eventos implements ActionListener{
    //Atributos de la clase
    private JFileChooser fileChooser;
    private JTextField ctRuta;
    private Busqueda busqueda;
    private Rutas rutas;

    public Eventos(JFileChooser fileChooser, JTextField ctRuta, Busqueda busqueda, Rutas rutas) {
        this.fileChooser = fileChooser;
        this.ctRuta = ctRuta;
        this.busqueda = busqueda;
        this.rutas = rutas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION){
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            ctRuta.setText(carpetaSeleccionada.getAbsolutePath());
            rutas.obtenerRutaSeleccionada(carpetaSeleccionada.getAbsolutePath()); // guarda la ruta
            busqueda.buscarArchivosRaiz(carpetaSeleccionada);
        }
    }
}
