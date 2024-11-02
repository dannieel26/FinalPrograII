
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 *
 * @author Pablo
 */
public class BusquedaArchivos {

    private List<File> listaArchivosImagen;
    private List<File> resultadosBusqueda;

    public BusquedaArchivos() {
        this.listaArchivosImagen = new ArrayList<>();
        this.resultadosBusqueda = new ArrayList<>();
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

        //Filtramos solo aquellos con más de un archivo
        duplicados.values().removeIf(lista -> lista.size() < 2);
        return duplicados;
    }

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

    public void agregarMetadatosTabla(File archivo, DefaultTableModel modelo) {
        Object[] fila = new Object[8];
        fila[0] = archivo.getName();
        fila[1] = obtenerExtension(archivo);
        fila[2] = archivo.getAbsolutePath();

        // Obtener fecha de creación y modificación
        try {
            Path path = archivo.toPath();
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

            Date fechaCreacion = new Date(attrs.creationTime().toMillis());
            Date fechaModificacion = new Date(attrs.lastModifiedTime().toMillis());

            fila[3] = new SimpleDateFormat("dd/MM/yyyy").format(fechaCreacion); // Fecha creación
            fila[4] = new SimpleDateFormat("dd/MM/yyyy").format(fechaModificacion); // Fecha modificación
        } catch (IOException e) {
            // Manejo de excepciones
            fila[3] = "Desconocida"; // Valor por defecto si no se puede obtener
            fila[4] = "Desconocida";
        }

        fila[5] = String.format("%.2f MB", archivo.length() / (1024.0 * 1024)); // Tamaño en MB

        // intentar Extraer metadatos del archivo
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(archivo);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (directory != null) {
                // Obtener nombre del aparato (cámara)
                String nombreAparato = directory.getString(ExifIFD0Directory.TAG_MAKE);
                String modeloAparato = directory.getString(ExifIFD0Directory.TAG_MODEL);
                fila[6] = nombreAparato != null ? nombreAparato : "Desconocido"; // Nombre del aparato
                fila[7] = modeloAparato != null ? modeloAparato : "Desconocido";
            } else {
                fila[6] = "Desconocido"; // Valor por defecto
                fila[7] = "Desconocido";
            }
        } catch (Exception e) {
            // Manejo de excepciones
            fila[6] = "Desconocido"; // Valor por defecto si no se puede obtener
            fila[7] = "Desconocido";
        }

        modelo.addRow(fila);
    }
    
    public List<File> obtenerArchivosMasGrandes(int limite) {
        listaArchivosImagen.sort((f1, f2) -> Long.compare(f2.length(), f1.length())); // Ordenar de mayor a menor
        return listaArchivosImagen.subList(0, Math.min(limite, listaArchivosImagen.size())); // Devuelve hasta 'limite' archivos
    }
    
    public void buscarEnTabla(String terminoBusqueda, int columna, DefaultTableModel modelo) {
        // Verificar si terminoBusqueda es null o está vacío
        if (terminoBusqueda == null || terminoBusqueda.trim().isEmpty()) {
            return; // No realizar la búsqueda si terminoBusqueda es null o vacío
        }

        resultadosBusqueda.clear();
        modelo.setRowCount(0); // Limpiar el modelo de la tabla

        for (File archivo : listaArchivosImagen) {
            boolean coincide = false; // Bandera para verificar si hay coincidencia

            switch (columna) {
                case 0: // Buscar por nombre
                    if (archivo.getName().toLowerCase().contains(terminoBusqueda.toLowerCase())) {
                        coincide = true;
                    } break;
                case 3: // Buscar por fecha de creación
                    try {
                        Path path = archivo.toPath();
                        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                        Date fechaCreacion = new Date(attrs.creationTime().toMillis());
                        String fechaCreacionFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fechaCreacion);

                        if (fechaCreacionFormateada.contains(terminoBusqueda)) {
                            coincide = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } break;
                default: break;
            }

            if (coincide) {
                resultadosBusqueda.add(archivo);
                agregarMetadatosTabla(archivo, modelo);
            }
        }
    }
}
