
package com.mycompany.proyectofinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PlaylistManager {
    private static final String FILE_NAME = "playlists.txt";

    // Método para guardar una nueva playlist
    public void agregarPlaylist(String nombrePlaylist) {
    // Crear un nuevo archivo para la playlist
    File nuevoArchivo = new File("playlists/" + nombrePlaylist + ".txt");
    try {
        if (nuevoArchivo.createNewFile()) { // Esto crea el archivo si no existe
            // Agregar el nombre de la playlist al archivo principal
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(nombrePlaylist);
                writer.newLine(); // Nueva línea para cada playlist
            }
        } else {
            JOptionPane.showMessageDialog(null, "La playlist ya existe");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public List<String> cargarPlaylists() {
    List<String> playlists = new ArrayList<>();
    File directorioPlaylists = new File("playlists"); // Define el directorio

    // Verifica si el directorio existe
    if (directorioPlaylists.exists() && directorioPlaylists.isDirectory()) {
        File[] archivos = directorioPlaylists.listFiles((dir, name) -> name.endsWith(".txt")); // Filtra solo los archivos .txt
        if (archivos != null) {
            for (File archivo : archivos) {
                playlists.add(archivo.getName().replace(".txt", "")); // Agrega solo el nombre sin la extensión
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "El directorio de playlist no existe");
        }
        return playlists;
    }

    // Método para borrar una playlist
    public void borrarPlaylist(String nombrePlaylist) {
        List<String> playlists = cargarPlaylists();

        // Comprobar si la playlist existe
        if (playlists.contains(nombrePlaylist)) {
            // Eliminar la playlist de la lista
            playlists.remove(nombrePlaylist);

            // Eliminar el archivo de la playlist
            File archivoPlaylist = new File("playlists/" + nombrePlaylist + ".txt");
            if (archivoPlaylist.delete()) {
                // Guardar las playlists actualizadas en el archivo
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                    for (String playlist : playlists) {
                        writer.write(playlist);
                        writer.newLine();
                    }
                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(null, "Playlist eliminada con éxito");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el archivo de la playlist");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La playlist no existe");
        }
    }

    
    public boolean agregarCancionAPlaylist(String nombrePlaylist, String nombreArchivo) {
        try {
            File directorioPlaylists = new File("playlists");
            if (!directorioPlaylists.exists()) {
                directorioPlaylists.mkdirs(); // Crea la carpeta "playlists" si no existe
            }

            File archivoPlaylist = new File(directorioPlaylists, nombrePlaylist + ".txt");
            if (!archivoPlaylist.exists()) {
                archivoPlaylist.createNewFile();
            }

            try (FileWriter writer = new FileWriter(archivoPlaylist, true)) { // Modo "append" para agregar sin sobrescribir
                writer.write(nombreArchivo + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public List<String> obtenerCancionesDePlaylist(String nombrePlaylist) {
    List<String> canciones = new ArrayList<>();
    File archivo = new File("playlists/" + nombrePlaylist + ".txt");

    if (!archivo.exists()) {
        return canciones; // Devuelve una lista vacía si no existe
    }

    // Lógica para leer las canciones del archivo
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            canciones.add(linea);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return canciones;
    }
}
