
package com.mycompany.proyectofinal;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class GestorArchivos {

    public boolean moverArchivo(File archivoSeleccionado) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    int opcion = fileChooser.showOpenDialog(null);
    if (opcion == JFileChooser.APPROVE_OPTION) {
        File carpetaDestino = fileChooser.getSelectedFile();
        File nuevoArchivo = new File(carpetaDestino, archivoSeleccionado.getName());

        if (archivoSeleccionado.renameTo(nuevoArchivo)) {
            JOptionPane.showMessageDialog(null, "Archivo movido con éxito a " + carpetaDestino.getAbsolutePath());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo mover el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    return false;
}
}