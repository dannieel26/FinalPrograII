
package com.mycompany.proyectofinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
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
    
    // Método para colocar los metadatos de la música en la tabla
    public void agregarMetadatosTabla(File archivo, DefaultTableModel modeloTabla) {
        String artista = "";
        String album = "";
        String genero = "";
        String año = "";
        String duracion = "";

        // Verificar si el archivo es de tipo audio
        if (archivo.getName().endsWith(".mp3") || archivo.getName().endsWith(".wma")) {
            artista = obtenerArtista(archivo.getAbsolutePath());
            album = obtenerAlbum(archivo.getAbsolutePath());
            genero = obtenerGenero(archivo.getAbsolutePath());
            año = obtenerAño(archivo.getAbsolutePath());
            duracion = obtenerDuracion(archivo.getAbsolutePath());
        }

        modeloTabla.addRow(new Object[]{
            archivo.getName(),
            artista,
            album,
            genero,
            año,
            duracion,
            obtenerTamañoEnMB(archivo),
            archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1),
            archivo.getAbsolutePath()
        });
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
        int segundos = duracionSegundos % 60; // Cambiar & a %
        
        return String.format("%d:%02d", minutos, segundos);
    } catch (Exception e){
        e.printStackTrace();
        return "00:00";
        }
    }

    public static String obtenerTamañoEnMB(File archivo) {
        DecimalFormat formato = new DecimalFormat("#.0");
        double tamañoEnMB = archivo.length() / (1024.0 * 1024.0);
        return formato.format(tamañoEnMB) + " MB";
    }
    
    private long buscarArchivos(File carpeta){
    long espacioTotal = 0;
    File[] archivos = carpeta.listFiles();
    if (archivos != null){
        for (File archivo : archivos){
            if(archivo.isDirectory()){
                espacioTotal += buscarArchivos(archivo); //acumula tamaño en subcarpetas
            } else if (archivo.isFile() && 
                       (archivo.getName().endsWith(".mp3") || 
                        archivo.getName().endsWith(".wma") || 
                        archivo.getName().endsWith(".mp4") || 
                        archivo.getName().endsWith(".flv"))) {
                long tamañoArchivo = archivo.length();
                espacioTotal += tamañoArchivo;
                //Agregar los archivos de música a la tabla
                agregarMetadatosTabla(archivo, (DefaultTableModel) tablaArchivos.getModel());
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
    
    public void mostrarArchivosMasGrandes(){
    DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
    List<File> archivosOrdenados = new ArrayList<>();
    
    //Recorrer la tabla
    for (int i = 0; i < modeloTabla.getRowCount(); i++){
        String rutaArchivo = (String) modeloTabla.getValueAt(i, 8);
        archivosOrdenados.add(new File(rutaArchivo));
    }
    
    //Ordenar los archivos por tamaño de mayor a menor
    archivosOrdenados = archivosOrdenados.stream().sorted((a ,b) -> Long.compare(b.length(), a.length())).collect(Collectors.toList());
    
    //Limpiar la tabla y mostrar los archivos ordenados
    modeloTabla.setRowCount(0);
    for (File archivo : archivosOrdenados){
        agregarMetadatosTabla(archivo, modeloTabla);
        }
    }
    
    public void buscarEnTabla(String termino, int columna) {
        if (termino == null || termino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un término de búsqueda válido.");
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        DefaultTableModel modeloFiltrado = new DefaultTableModel(new String[]{"Nombre", "Artista", "Album", "Genero", "Año", "Duracion", "Tamaño", "Extension", "Ruta"}, 0);

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String valor = (String) modeloTabla.getValueAt(i, columna);
            if (valor != null && valor.toLowerCase().contains(termino.toLowerCase())) {
                Object[] fila = new Object[modeloTabla.getColumnCount()];
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    fila[j] = modeloTabla.getValueAt(i, j);
                }
                modeloFiltrado.addRow(fila);
            }
        }

        if (modeloFiltrado.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados para la búsqueda");
        } else {
            tablaArchivos.setModel(modeloFiltrado);
        }
    }
    
    public static String obtenerLetra(File archivo) {
        try {
            AudioFile audioFile = AudioFileIO.read(archivo);
            Tag tag = audioFile.getTag();
            return tag.getFirst(FieldKey.LYRICS); // Extrae la letra
        } catch (Exception e) {
            e.printStackTrace();
            return null; // En caso de error, retorna null
        }
    }
}
