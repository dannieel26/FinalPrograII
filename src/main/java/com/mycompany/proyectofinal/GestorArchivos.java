
package com.mycompany.proyectofinal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
    
    public void ordenarColumna(DefaultTableModel modelo, int columna) {
        List<Object[]> filas = new ArrayList<>();

        // Extraer filas en una lista
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fila[j] = modelo.getValueAt(i, j);
            }
            filas.add(fila);
        }

        // Ordenar las filas
        Collections.sort(filas, (a, b) -> {
            if (columna == 4) { // Columna de Año
                int yearA = Integer.parseInt(String.valueOf(a[columna]).substring(0, 4));
                int yearB = Integer.parseInt(String.valueOf(b[columna]).substring(0, 4));
                return Integer.compare(yearA, yearB);
            } else if (columna == 5) { // Columna de Duración
                // Convertir la duración de "mm:ss" o "hh:mm:ss" a segundos
                int durationA = convertirDuracionASegundos(String.valueOf(a[columna]));
                int durationB = convertirDuracionASegundos(String.valueOf(b[columna]));
                return Integer.compare(durationA, durationB);
            } else {
                return String.valueOf(a[columna]).compareToIgnoreCase(String.valueOf(b[columna]));
            }
        });

        // Limpiar el modelo y volver a agregar las filas ordenadas
        modelo.setRowCount(0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
    }

    private int convertirDuracionASegundos(String duracion) {
        String[] partes = duracion.split(":");
        int segundos = 0;
        if (partes.length == 2) { // Formato "mm:ss"
            segundos = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
        } else if (partes.length == 3) { // Formato "hh:mm:ss"
            segundos = Integer.parseInt(partes[0]) * 3600 + Integer.parseInt(partes[1]) * 60 + Integer.parseInt(partes[2]);
        }
        return segundos;
    }
}
