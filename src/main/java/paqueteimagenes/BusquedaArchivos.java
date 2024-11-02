
package paqueteimagenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class BusquedaArchivos {

    private List<File> listaArchivosImagen;

    public BusquedaArchivos() {
        listaArchivosImagen = new ArrayList<>();
    }

    public void buscarArchivosImagen(File directorio) {
        listaArchivosImagen.clear();
        buscarRecursivamente(directorio);
    }

    private void buscarRecursivamente(File directorio) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    buscarRecursivamente(archivo); // Busca en subcarpetas
                } else if (esArchivoImagen(archivo)) {
                    listaArchivosImagen.add(archivo);
                }
            }
        }
    }

    private boolean esArchivoImagen(File archivo) {
        String extension = obtenerExtension(archivo).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }

    private String obtenerExtension(File archivo) {
        String nombre = archivo.getName();
        int i = nombre.lastIndexOf('.');
        return (i > 0) ? nombre.substring(i + 1) : "";
    }

    public List<File> getListaArchivosImagen() {
        return listaArchivosImagen;
    }

    public long calcularEspacioTotalImagenes() {
        return listaArchivosImagen.stream().mapToLong(File::length).sum();
    }
    
    // Método para buscar archivos duplicados
    public Map<String, List<File>> buscarDuplicados() {
        Map<String, List<File>> duplicados = new HashMap<>();

        for (File archivo : listaArchivosImagen) {
            try {
                String hash = calcularHashArchivo(archivo);
                duplicados.putIfAbsent(hash, new ArrayList<>());
                duplicados.get(hash).add(archivo);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace(); // Manejo de excepciones, considera registrar el error
            }
        }

        // Filtramos solo aquellos con más de un archivo
        duplicados.values().removeIf(lista -> lista.size() < 2);
        return duplicados;
    }

    // Método para calcular el hash de un archivo
    private String calcularHashArchivo(File archivo) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(archivo)) {
            byte[] byteBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(byteBuffer)) != -1) {
                digest.update(byteBuffer, 0, bytesRead);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : digest.digest()) {
            sb.append(String.format("%02x", b)); // Convierte bytes a hexadecimal
        }
        return sb.toString();
    }

    // Método para agregar metadatos a la tabla
    public void agregarMetadatosTabla(File archivo, DefaultTableModel modelo) {
        Object[] fila = new Object[7]; // Suponiendo 7 columnas
        fila[0] = archivo.getName();
        fila[1] = obtenerExtension(archivo);
        fila[2] = archivo.getAbsolutePath();
        fila[3] = ""; // Fecha creación (puedes usar una función para obtenerla)
        fila[4] = new SimpleDateFormat("dd/MM/yyyy").format(new Date(archivo.lastModified())); // Fecha modificación
        fila[5] = String.format("%.2f MB", archivo.length() / (1024.0 * 1024));
        fila[6] = ""; // Otros metadatos que se deseen agregar

        modelo.addRow(fila); // Agrega la fila al modelo de la tabla
    }

}
