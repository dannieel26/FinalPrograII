
package paqueteimagenes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo
 */
public class BusquedaArchivos {

    private final List<File> listaArchivosImagen;

    public BusquedaArchivos() {
        listaArchivosImagen = new ArrayList<>();
    }

    //buscar archivos hasta el último nivel de profundidad. directorioInicial es La ruta inicial donde comienza la búsqueda
    public void buscarArchivosImagen(File directorioInicial) {
        if (directorioInicial.isDirectory()) {
            File[] archivos = directorioInicial.listFiles();
            
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        buscarArchivosImagen(archivo); // Llamada recursiva para buscar en subdirectorios
                    } else if (esImagen(archivo)) {
                        listaArchivosImagen.add(archivo);
                    }
                }
            }
        }
    }

    //verificacion si es imagen
    private boolean esImagen(File archivo) {
        String nombreArchivo = archivo.getName().toLowerCase();
        return nombreArchivo.endsWith(".png") || nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".jpeg");
    }


    //Devuelve la lista de archivos de imagen encontrados
    public List<File> getListaArchivosImagen() {
        return listaArchivosImagen;
    }
}
