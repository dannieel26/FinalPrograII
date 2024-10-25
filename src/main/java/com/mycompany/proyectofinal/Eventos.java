
package com.mycompany.proyectofinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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
    private JLabel lblEspacioArchivos;

    public Eventos(JFileChooser fileChooser, JTextField ctRuta, Busqueda busqueda, Rutas rutas, JLabel lblEspacioArchivos) {
        this.fileChooser = fileChooser;
        this.ctRuta = ctRuta;
        this.busqueda = busqueda;
        this.rutas = rutas;
        this.lblEspacioArchivos = lblEspacioArchivos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION){
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            ctRuta.setText(carpetaSeleccionada.getAbsolutePath());
            rutas.obtenerRutaSeleccionada(carpetaSeleccionada.getAbsolutePath()); // guarda la ruta
            long espacioTotal = busqueda.buscarArchivosRaiz(carpetaSeleccionada);
            lblEspacioArchivos.setText("Espacio total ocupado: " + (espacioTotal / (1024 * 1024)) + " MB");
        }
    }
}
