
package paqueteimagenes;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.mycompany.proyectofinal.Interfaz;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author Pablo
 */
public class VentanaInterfaz extends JFrame {
    
    private JPanel panel;
    private JTextField ctRuta;
    private JButton btnAbrirInterfaz1, btnBuscarCarpeta, btnVerImagen, btnMostrarEspacio, btnVerDuplicados, btnEliminarArchivo, btnVerMasGrandes,btnMoverArchivo;
    private JTable tablaArchivos;
    private JScrollPane scrollTabla;
    private JFileChooser fileChooser;
    private JLabel lblEspacioArchivos;
    private JComboBox comboBoxBuscar, comboBoxAgrupar;
    private BusquedaArchivos busquedaArchivos;
    private final String archivoRutaDefault = "ultimaRuta.txt";
    
    public VentanaInterfaz(){
        this.setSize(1100, 550);
        setTitle("Administrador multimedia");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        iniciarComponentes();
        cargarRutaDefault();
    }
    
    private void iniciarComponentes(){
        colocarPanel();
        colocarEtiquetas();
        colocarCajaTexto();
        colocarBotones();
        colocarTabla();
        colocarFileChooser();
        colocarEtiquetaEspacio();
        colocarComboBox();
        busquedaArchivos = new BusquedaArchivos();
        agregarEventos();
    }
    
    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(217,250,182));
        this.getContentPane().add(panel);
    }
    
    //Métodos gráficos
    private void colocarEtiquetas(){
        JLabel etiqueta1 = new JLabel("Administrador multimedia");
        etiqueta1.setBounds(425,15,230,20);
        etiqueta1.setFont(new Font("arial",1,18));
        etiqueta1.setForeground(new Color(0,127,0));
        panel.add(etiqueta1);
    }
    
    private void colocarCajaTexto(){
        ctRuta = new JTextField();
        ctRuta.setBounds(195, 50, 685, 30);
        ctRuta.setBackground(Color.WHITE);
        ctRuta.setEditable(false);
        panel.add(ctRuta);
    }
    
    private void colocarBotones(){
        btnBuscarCarpeta = new JButton("Buscar Carpeta");
        btnBuscarCarpeta.setBounds(35, 45, 140, 40);
        btnBuscarCarpeta.setBackground(new Color(0,127,0));
        btnBuscarCarpeta.setForeground(Color.WHITE);
        panel.add(btnBuscarCarpeta);
        
        btnVerImagen = new JButton("Ver imagen");
        btnVerImagen.setBounds(35, 430, 110, 40);
        btnVerImagen.setBackground(new Color(2,63,64));
        btnVerImagen.setForeground(Color.WHITE);
        panel.add(btnVerImagen  );
        
        btnMostrarEspacio = new JButton("Ver espacio");
        btnMostrarEspacio.setBounds(160, 430, 110, 40);
        btnMostrarEspacio.setBackground(new Color(2,63,64));
        btnMostrarEspacio.setForeground(Color.WHITE);
        panel.add(btnMostrarEspacio);
        
        btnVerDuplicados = new JButton("Duplicados");
        btnVerDuplicados.setBounds(285, 430, 110, 40);
        btnVerDuplicados.setBackground(new Color(2,63,64));
        btnVerDuplicados.setForeground(Color.WHITE);
        panel.add(btnVerDuplicados);
        
        btnEliminarArchivo = new JButton("Eliminar");
        btnEliminarArchivo.setBounds(410, 430, 110, 40);
        btnEliminarArchivo.setBackground(new Color(0,130,132));
        btnEliminarArchivo.setForeground(Color.WHITE);
        panel.add(btnEliminarArchivo);
        
        btnVerMasGrandes = new JButton("Ver mas grandes");
        btnVerMasGrandes.setBounds(535,430,135,40);
        btnVerMasGrandes.setBackground(new Color(0,130,132));
        btnVerMasGrandes.setForeground(Color.WHITE);
        panel.add(btnVerMasGrandes);
        
        btnMoverArchivo = new JButton("Mover");
        btnMoverArchivo.setBounds(685, 430, 110, 40);
        btnMoverArchivo.setBackground(new Color(2,63,64));
        btnMoverArchivo.setForeground(Color.WHITE);
        panel.add(btnMoverArchivo);
        
        btnAbrirInterfaz1 = new JButton("Ir a música y video");
        btnAbrirInterfaz1.setBounds(905, 45, 140, 40);
        btnAbrirInterfaz1.setBackground(new Color(0,127,0));
        btnAbrirInterfaz1.setForeground(Color.WHITE);
        panel.add(btnAbrirInterfaz1);
    }
    
    private void colocarTabla(){
        String[] columnas = {"Nombre","Extension","Ruta","Fecha creacion","Fecha modificacion","Tamaño","Aparato obtención","Modelo obtención"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaArchivos = new JTable(modeloTabla);
        
        scrollTabla = new JScrollPane (tablaArchivos);
        scrollTabla.setBounds(35, 110, 1010, 280);
        panel.add(scrollTabla);
    }
    
    private void colocarFileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    
    private void colocarEtiquetaEspacio(){
        lblEspacioArchivos = new JLabel("Espacio total ocupado: 0 MB");
        lblEspacioArchivos.setBounds(35, 400, 300, 20);
        panel.add(lblEspacioArchivos);
    }
    
    private void colocarComboBox(){
        comboBoxBuscar = new JComboBox<>();
        comboBoxBuscar.addItem("Buscar por...");
        comboBoxBuscar.addItem("Nombre");
        comboBoxBuscar.addItem("Fecha");
        comboBoxBuscar.setBounds(810, 430, 105, 40);
        comboBoxBuscar.setBackground(new Color(2,63,64));
        comboBoxBuscar.setForeground(Color.WHITE);
        panel.add(comboBoxBuscar);
        
        comboBoxAgrupar = new JComboBox<>();
        comboBoxAgrupar.addItem("Agrupar por...");
        comboBoxAgrupar.addItem("Año");
        comboBoxAgrupar.addItem("Mes");
        comboBoxAgrupar.addItem("Día");
        comboBoxAgrupar.setBounds(930, 430, 115, 40);
        comboBoxAgrupar.setBackground(new Color(2,63,64));
        comboBoxAgrupar.setForeground(Color.WHITE);
        panel.add(comboBoxAgrupar);
    }
    
    private void agregarEventos(){
        btnBuscarCarpeta.addActionListener(e -> abrirDialogoYBuscarImagenes());
        btnVerImagen.addActionListener(e -> verImagenSeleccionada());
        btnAbrirInterfaz1.addActionListener(e -> abrirOtraInterfaz());
        btnVerDuplicados.addActionListener(e -> mostrarDuplicados());
        btnMostrarEspacio.addActionListener(e -> actualizarEspacioTotal());
        btnEliminarArchivo.addActionListener(e -> eliminarArchivoSeleccionado());
        btnVerMasGrandes.addActionListener(e -> mostrarArchivosMasGrandes());
        comboBoxBuscar.addActionListener(e -> realizarBusqueda());
        btnMoverArchivo.addActionListener(e -> moverArchivoSeleccionado());
        comboBoxAgrupar.addActionListener(e -> agruparPorFecha());
    }
    
    private void abrirDialogoYBuscarImagenes() {
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            ctRuta.setText(ruta);
            guardarRutaDefault(ruta);
            iniciarBusquedaImagenes(ruta);
        }
    }
    
    private void iniciarBusquedaImagenes(String ruta) {
        File directorioInicial = new File(ruta);
        if (!directorioInicial.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Seleccione una carpeta válida.");
            return;
        }
        
        busquedaArchivos.buscarArchivosImagen(directorioInicial);
        
        // Obtener archivos encontrados y mostrar en la tabla
        List<File> archivosImagenes = busquedaArchivos.getListaArchivosImagen();
        mostrarArchivosEnTabla(archivosImagenes);
    }
    
    private void mostrarArchivosEnTabla(List<File> archivosImagenes) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        modeloTabla.setRowCount(0); // Limpiar tabla

        for (File archivo : archivosImagenes) {
            busquedaArchivos.agregarMetadatosTabla(archivo, modeloTabla);
        }
    }
    
    private String obtenerExtension(File archivo) {
        String nombre = archivo.getName();
        int i = nombre.lastIndexOf('.');
        return (i > 0) ? nombre.substring(i + 1) : "";
    }
    
    private void guardarRutaDefault(String ruta) {
        try (FileWriter writer = new FileWriter(archivoRutaDefault)) {
            writer.write(ruta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cargarRutaDefault() {
        try {
            if (Files.exists(Paths.get(archivoRutaDefault))) {
                String ruta = new String(Files.readAllBytes(Paths.get(archivoRutaDefault)));
                ctRuta.setText(ruta);
                iniciarBusquedaImagenes(ruta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void verImagenSeleccionada() {
        int filaSeleccionada = tablaArchivos.getSelectedRow();
        if (filaSeleccionada != -1) {
            String rutaImagen = tablaArchivos.getValueAt(filaSeleccionada, 2).toString(); // Columna de la ruta
            File archivoImagen = new File(rutaImagen);

            if (archivoImagen.exists() && esArchivoImagen(archivoImagen)) {
                VentanaImagen ventanaImagen = new VentanaImagen(rutaImagen);
                ventanaImagen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen válida.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una imagen de la tabla para visualizar.");
        }
    }

    private boolean esArchivoImagen(File archivo) {
        String extension = obtenerExtension(archivo).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }
    
    private void realizarBusqueda() {
        String opcionSeleccionada = (String) comboBoxBuscar.getSelectedItem();
        String terminoBusqueda = null;

        // Obtener el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();

        switch (opcionSeleccionada) {
            case "Nombre":
                terminoBusqueda = JOptionPane.showInputDialog("Ingrese la canción a buscar");
                busquedaArchivos.buscarEnTabla(terminoBusqueda, 0, modelo);  // Buscar en la columna de nombre
                break;
            case "Fecha":
                terminoBusqueda = JOptionPane.showInputDialog("Ingrese la fecha de creación a buscar");
                busquedaArchivos.buscarEnTabla(terminoBusqueda, 3, modelo);  // Buscar en la columna de fecha de creación
                break;
            default:
                break;
        }
    }
    
    private void actualizarEspacioTotal() {
        double espacioTotalMB = 0.0;
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String tamañoStr = (String) modeloTabla.getValueAt(i, 5);
            String[] partes = tamañoStr.split(" "); // Divide el String
            if (partes.length > 0) {
                espacioTotalMB += Double.parseDouble(partes[0]); // Suma el valor numérico en MB
            }
        }
        lblEspacioArchivos.setText(String.format("Espacio total ocupado: %.2f MB", espacioTotalMB));
    }

    private void mostrarDuplicados() {
        // Obtener la lista de archivos duplicados
        Map<String, List<File>> archivosDuplicadosMap = busquedaArchivos.buscarDuplicados();
        if (archivosDuplicadosMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron archivos duplicados");
        } else {
            vaciarTabla(); // Limpiar la tabla antes de mostrar los duplicados
            long espacioTotalDuplicados = 0;
            DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();

            for (List<File> archivos : archivosDuplicadosMap.values()) {
                for (File archivo : archivos) {
                    espacioTotalDuplicados += archivo.length();
                    busquedaArchivos.agregarMetadatosTabla(archivo, modelo);
                }
            }

            // Actualizar la tabla para mostrar el espacio ocupado por duplicados
            lblEspacioArchivos.setText("Duplicados: " + archivosDuplicadosMap.values().stream().mapToInt(List::size).sum() + 
                                        " | Espacio ocupado: " + espacioTotalDuplicados / (1024 * 1024) + " MB");
        }
    }
    
    public void mostrarArchivosMasGrandes() {
        List<File> archivosMasGrandes = busquedaArchivos.obtenerArchivosMasGrandes(15);
        DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();
        modelo.setRowCount(0); // Limpia la tabla antes de agregar nuevos datos

        for (File archivo : archivosMasGrandes) {
            busquedaArchivos.agregarMetadatosTabla(archivo, modelo);
        }
    }
    
    private int obtenerFilaSeleccionada() {
        return tablaArchivos.getSelectedRow();
    }

    private void eliminarArchivoSeleccionado() {
        int filaSeleccionada = obtenerFilaSeleccionada();
        if (filaSeleccionada != -1) {
            String rutaArchivo = tablaArchivos.getValueAt(filaSeleccionada, 2).toString(); // Obtener la ruta del archivo de la columna correspondiente
            File archivoAEliminar = new File(rutaArchivo);

            if (archivoAEliminar.exists()) {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este archivo?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (archivoAEliminar.delete()) {
                        JOptionPane.showMessageDialog(this, "Archivo eliminado con éxito");
                        // Actualizar la tabla
                        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
                        modeloTabla.removeRow(filaSeleccionada); // Eliminar la fila de la tabla
                        actualizarEspacioTotal(); // Actualizar el espacio total
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el archivo");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "El archivo seleccionado no existe");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un archivo de la tabla para eliminar");
        }
    }
    
    private void moverArchivoSeleccionado() {
        int filaSeleccionada = tablaArchivos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un archivo de la tabla para mover");
            return;
        }

        String rutaArchivo = tablaArchivos.getValueAt(filaSeleccionada, 2).toString();
        File archivoSeleccionado = new File(rutaArchivo);

        if (!archivoSeleccionado.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo seleccionado no existe");
            return;
        }

        // Crear y configurar JFileChooser para seleccionar la carpeta destino
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Seleccione la carpeta de destino");

        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File carpetaDestino = fileChooser.getSelectedFile();
            File nuevoArchivo = new File(carpetaDestino, archivoSeleccionado.getName());

            try {
                Files.move(archivoSeleccionado.toPath(), nuevoArchivo.toPath());
                JOptionPane.showMessageDialog(this, "Archivo movido con éxito a: " + carpetaDestino.getAbsolutePath());
                actualizarTablaDespuesDeMovimiento();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al mover el archivo: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void actualizarTablaDespuesDeMovimiento() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        int filaSeleccionada = tablaArchivos.getSelectedRow();
        if (filaSeleccionada != -1) {
            modeloTabla.removeRow(filaSeleccionada); // Eliminar la fila del archivo movido
        }
    }
    
    private void agruparPorFecha() {
        String opcionSeleccionada = (String) comboBoxAgrupar.getSelectedItem();

        // Verificar la opción seleccionada y solicitar el dato correspondiente
        String criterio = null;
        switch (opcionSeleccionada) {
            case "Año":
                criterio = JOptionPane.showInputDialog(this, "Ingrese el año para agrupar:");
                if (criterio != null && !criterio.isEmpty()) {
                    agruparPor("Año", criterio);
                } break;
            case "Mes":
                criterio = JOptionPane.showInputDialog(this, "Ingrese el mes (formato MM) para agrupar:");
                if (criterio != null && !criterio.isEmpty()) {
                    agruparPor("Mes", criterio);
                } break;
            case "Día":
                criterio = JOptionPane.showInputDialog(this, "Ingrese el día (formato DD) para agrupar:");
                if (criterio != null && !criterio.isEmpty()) {
                    agruparPor("Día", criterio);
                } break;

            default: break;
        }
    }

    private void agruparPor(String tipo, String valor) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de mostrar resultados agrupados

        List<File> archivosImagenes = busquedaArchivos.getListaArchivosImagen();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String valorFormateado = String.format("%02d", Integer.parseInt(valor));

        for (File archivo : archivosImagenes) {
            String fechaCreacion = obtenerFechaCreacion(archivo); // Formato de fecha: "yyyy-MM-dd"

            // Comparación basada en el tipo seleccionado
            if (tipo.equals("Año") && fechaCreacion.startsWith(valor)) {
                busquedaArchivos.agregarMetadatosTabla(archivo, modeloTabla);
            } else if (tipo.equals("Mes") && fechaCreacion.substring(5, 7).equals(valorFormateado)) {
                busquedaArchivos.agregarMetadatosTabla(archivo, modeloTabla);
            } else if (tipo.equals("Día") && fechaCreacion.substring(8, 10).equals(valorFormateado)) {
                busquedaArchivos.agregarMetadatosTabla(archivo, modeloTabla);
            }
        }
    }

    private String obtenerFechaCreacion(File archivo) {
        try {
            BasicFileAttributes attr = Files.readAttributes(archivo.toPath(), BasicFileAttributes.class);
            return attr.creationTime().toString().substring(0, 10);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void vaciarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();
        modelo.setRowCount(0); // Limpiar la tabla
        lblEspacioArchivos.setText("Espacio total ocupado: 0 MB"); // Resetear el label de espacio total
    }
    
    private void abrirOtraInterfaz() {
        // Crear una instancia de la otra interfaz y abrirla
        Interfaz i = new Interfaz();
        i.setVisible(true);
        this.dispose();
    }
}
