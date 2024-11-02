
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
    }
    
    private void abrirDialogoYBuscarImagenes() {
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            ctRuta.setText(ruta);
            guardarRutaDefault(ruta); // Guardar como última ruta
            iniciarBusquedaImagenes(ruta);
        }
    }
    
    private void iniciarBusquedaImagenes(String ruta) {
        File directorioInicial = new File(ruta);
        if (!directorioInicial.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Seleccione una carpeta válida.");
            return;
        }
        
        // Búsqueda de archivos de imagen en la ruta seleccionada
        busquedaArchivos.buscarArchivosImagen(directorioInicial);
        
        // Obtener archivos encontrados y mostrar en la tabla
        List<File> archivosImagenes = busquedaArchivos.getListaArchivosImagen();
        mostrarArchivosEnTabla(archivosImagenes);
    }
    
    private void mostrarArchivosEnTabla(List<File> archivosImagenes) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        modeloTabla.setRowCount(0); // Limpiar tabla

        for (File archivo : archivosImagenes) {
            // Convierte el tamaño a MB
            double tamañoMB = archivo.length() / (1024.0 * 1024.0);
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
    
    private void actualizarEspacioTotal() {
        double espacioTotalMB = 0.0;
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String tamañoStr = (String) modeloTabla.getValueAt(i, 5); // Suponiendo que la columna 5 tiene el tamaño
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
                    // Agregar metadatos a la tabla (asumiendo que tienes un método para esto)
                    busquedaArchivos.agregarMetadatosTabla(archivo, modelo);
                }
            }

            // Actualizar la tabla para mostrar el espacio ocupado por duplicados
            lblEspacioArchivos.setText("Duplicados: " + archivosDuplicadosMap.values().stream().mapToInt(List::size).sum() + 
                                        " | Espacio ocupado: " + espacioTotalDuplicados / (1024 * 1024) + " MB");
        }
    }

    // Método para vaciar la tabla
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
