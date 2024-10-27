
package com.mycompany.proyectofinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

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
    
    public String obtenerArtista(String rutaArchivo){
        try {
            AudioFile archivoAudio = AudioFileIO.read(new File(rutaArchivo));
            Tag etiqueta = archivoAudio.getTag();
            return etiqueta.getFirst(FieldKey.ARTIST);
        } catch (Exception e){
            e.printStackTrace();
            return "Desconocido";
        }
    }
    
    public String obtenerAlbum(String rutaArchivo){
        try {
            AudioFile archivoAudio = AudioFileIO.read(new File(rutaArchivo));
            Tag etiqueta = archivoAudio.getTag();
            return etiqueta.getFirst(FieldKey.ALBUM);
        } catch (Exception e){
            e.printStackTrace();
            return "Desconocido";
        }
    }
    
    public String obtenerGenero(String rutaArchivo){
        try {
            AudioFile archivoAudio = AudioFileIO.read(new File(rutaArchivo));
            Tag etiqueta = archivoAudio.getTag();
            return etiqueta.getFirst(FieldKey.GENRE);
        } catch (Exception e){
            e.printStackTrace();
            return "Desconocido";
        }
    }
    
    public String obtenerAño(String rutaArchivo){
        try {
            AudioFile archivoAudio = AudioFileIO.read(new File(rutaArchivo));
            Tag etiqueta = archivoAudio.getTag();
            return etiqueta.getFirst(FieldKey.YEAR);
        } catch (Exception e){
            e.printStackTrace();
            return "Desconocido";
        }
    }
    
    public String obtenerDuracion(String rutaArchivo){
        try {
            AudioFile archivoAudio = AudioFileIO.read(new File(rutaArchivo));
            int duracionSegundos = archivoAudio.getAudioHeader().getTrackLength();
            
            int minutos = duracionSegundos / 60;
            int segundos = duracionSegundos & 60;
            
            return String.format("%d:%02d", minutos, segundos);
        } catch (Exception e){
            e.printStackTrace();
            return "00:00";
        }
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
                        archivo.getName(),
                        obtenerArtista(archivo.getAbsolutePath()),
                        obtenerAlbum(archivo.getAbsolutePath()),
                        obtenerGenero(archivo.getAbsolutePath()),
                        obtenerAño(archivo.getAbsolutePath()),
                        obtenerDuracion(archivo.getAbsolutePath()),
                        archivo.length() / (1024 * 1024) + " MB",
                        archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1),
                        archivo.getAbsolutePath()
                    });
                }
            }
        }
        return espacioTotal;
    }
    
    public List<File> buscarDuplicados() {
        Map<String, List<File>> archivosMap = new HashMap<>();
        List<File> archivosDuplicados = new ArrayList<>();

        // Recorrer la tabla y calcular el hash de cada archivo
        DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String rutaArchivo = (String) modelo.getValueAt(i, 8);   // Columna de ruta
            File archivo = new File(rutaArchivo);

            // Obtener el hash del archivo
            try {
                String hash = calcularHash(archivo);

                // Guardar el archivo en el mapa según su hash
                archivosMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(archivo);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        // Identificar duplicados
        for (List<File> archivos : archivosMap.values()) {
            if (archivos.size() > 1) {
                archivosDuplicados.addAll(archivos);
            }
        }

        return archivosDuplicados;
    }

    // Método para calcular el hash MD5 del archivo
    private String calcularHash(File archivo) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(archivo)) {
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesLeidos);
            }
        }
        byte[] digest = md.digest();

        // Convertir el hash a una cadena hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
