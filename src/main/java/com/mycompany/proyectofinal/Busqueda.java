
package com.mycompany.proyectofinal;

import java.io.File;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class Busqueda {
    //atributos de la clase
    private JTable tablaArchivos;

    public Busqueda(JTable tablaArchivos) {
        this.tablaArchivos = tablaArchivos;
    }
    
    //Método para limpiar todo una vez al buscar una nueva carpeta
    public long buscarArchivosRaiz(File carpeta){
        //Limpiar el contenido de anterior de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        modeloTabla.setRowCount(0); //Borra todo lo existente
        return buscarArchivos(carpeta);
    }
    
    //Método recursivo para archivos en carpetas y subcarpetas
    private long buscarArchivos(File carpeta){
        long espacioTotal = 0;
        File[] archivos = carpeta.listFiles();
        if (archivos != null){
            for (File archivo : archivos){
                if(archivo.isDirectory()){
                    espacioTotal += buscarArchivos(archivo); //acumula tamaño en subcarpetas
                } else if (archivo.isFile() && archivo.getName().endsWith(".mp3") || archivo.getName().endsWith(".wma") || archivo.getName().endsWith(".mp4") || archivo.getName().endsWith(".flv")){
                    long tamañoArchivo = archivo.length();
                    espacioTotal += tamañoArchivo;
                    //Agregar los archivos de música a ala tabla
                    ((DefaultTableModel) tablaArchivos.getModel()).addRow(new Object[]{
                        archivo.getName(),"","","","","",archivo.length(), archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1),archivo.getAbsolutePath()
                    });
                }
            }
        }
        return espacioTotal;
    }
}
