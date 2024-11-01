
package com.mycompany.proyectofinal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

/**
 *
 * @author Pablo
 */
public class Interfaz extends JFrame{
    
    //Atributos de la clase
    private JPanel panel;
    private JTextField ctRuta;
    private JButton btnBuscarCarpeta,btnReproducir,btnPausa,btnDetener,btnBuscar,btnPlaylists,btnVerLetra;
    private JTable tablaArchivos;
    private JComboBox<String> opcionesComboBox, comboBoxPlaylists, comboBoxBuscar;
    private JScrollPane scrollTabla;
    private JFileChooser fileChooser;
    private Rutas rutas;
    private Busqueda busqueda;
    private Reproductor reproductor;
    private JLabel lblEspacioArchivos;
    
    //Constructor
    public Interfaz(){
        this.setSize(980, 500);
        setTitle("Administrador multimedia");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        rutas = new Rutas();
        reproductor = new Reproductor();
        reproductor.getMediaPlayerComponent().setBounds(950, 100, 400, 400);
        iniciarComponentes();
    }
    
    // Método para iniciar los componentes
    private void iniciarComponentes(){
        colocarPanel();
        colocarEtiquetas();
        colocarCajaTexto();
        colocarFileChooser();
        colocarBotones();
        colocarTabla();
        colocarEtiquetaEspacioTotal();
        colocarComboBox();
        
        busqueda = new Busqueda(tablaArchivos);
        
        panel.add(reproductor.getMediaPlayerComponent());
        agregarEventos();
        agregarEventosComboBox();
        buscarArchivosRutaPredefinida();
    }
    
    //Método para colocar el panel a la ventana
    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.gray);
        this.getContentPane().add(panel);
    }
    
    //Métodos gráficos
    private void colocarEtiquetas(){
        JLabel etiqueta1 = new JLabel("Administrador multimedia");
        etiqueta1.setBounds(325,20,230,20);
        etiqueta1.setFont(new Font("arial",1,18));
        panel.add(etiqueta1);
    }
    
    private void colocarCajaTexto(){
        ctRuta = new JTextField();
        ctRuta.setBounds(200, 50, 600, 30);
        ctRuta.setEditable(false);
        panel.add(ctRuta);
    }
    
    private void colocarBotones(){
        btnBuscarCarpeta = new JButton("Buscar Carpeta");
        btnBuscarCarpeta.setBounds(35, 45, 140, 40);
        panel.add(btnBuscarCarpeta);
        
        btnReproducir = new JButton("Reproducir");
        btnReproducir.setBounds(35, 390, 110, 40);
        panel.add(btnReproducir);
        
        btnPausa = new JButton("Pausa");
        btnPausa.setBounds(160, 390, 110, 40);
        panel.add(btnPausa);
        
        btnDetener = new JButton("Detener");
        btnDetener.setBounds(285, 390, 110, 40);
        panel.add(btnDetener);
        
        btnVerLetra = new JButton("Ver letra");
        btnVerLetra.setBounds(410, 390, 110, 40);
        panel.add(btnVerLetra);
    }
    
    private void colocarTabla(){
        String[] columnas = {"Nombre","Artista","Album","Genero","Año","Duracion","Tamaño","Extension","Ruta"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaArchivos = new JTable(modeloTabla);
        
        scrollTabla = new JScrollPane (tablaArchivos);
        scrollTabla.setBounds(35, 120, 895, 240);
        panel.add(scrollTabla);
    }
    
    private void colocarFileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        String rutaPredefinida = rutas.leerRutaObtenida(); // para leer las rutas guardadas
        if (rutaPredefinida != null){
            fileChooser.setCurrentDirectory(new File(rutaPredefinida)); // Se establece la carpeta por defecto
            ctRuta.setText(rutaPredefinida); // MOstrar la ruta en la caja de texto del panel
        }
    }
    
    private void colocarEtiquetaEspacioTotal(){
        lblEspacioArchivos = new JLabel("Espacio total ocupado: 0 MB");
        lblEspacioArchivos.setBounds(35, 360, 300, 20);
        panel.add(lblEspacioArchivos);
    }
    
    private void colocarComboBox(){
        opcionesComboBox = new JComboBox<>();
        opcionesComboBox.addItem("Seleccionar opción");
        opcionesComboBox.addItem("Mostrar duplicados");
        opcionesComboBox.addItem("Ver más grandes");
        opcionesComboBox.addItem("Eliminar archivo(s)");
        opcionesComboBox.addItem("Mover archivo(s)");
        opcionesComboBox.setBounds(800, 390, 140, 40);
        panel.add(opcionesComboBox);
        
        comboBoxPlaylists = new JComboBox<>();
        comboBoxPlaylists.addItem("Opciones Playlist");
        comboBoxPlaylists.addItem("Agregar a playlist");
        comboBoxPlaylists.addItem("Gestionar Playlists");
        comboBoxPlaylists.setBounds(660,390,125,40);
        panel.add(comboBoxPlaylists);
        
        comboBoxBuscar = new JComboBox<>();
        comboBoxBuscar.addItem("Buscar por...");
        comboBoxBuscar.addItem("Canción");
        comboBoxBuscar.addItem("Artista");
        comboBoxBuscar.addItem("Álbum");
        comboBoxBuscar.setBounds(535, 390, 110, 40);
        panel.add(comboBoxBuscar);
    }
    
    private void agregarEventos(){
        Eventos eventos = new Eventos(fileChooser, ctRuta, busqueda, rutas, lblEspacioArchivos);
        //Agregar evento del botón
        btnBuscarCarpeta.addActionListener(eventos);
        
        //Evento para el boton reproducir
        btnReproducir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaArchivos.getSelectedRow(); // obtener la fila seleccionada de la tabla
                if (filaSeleccionada != -1){ //verificar si hay una fila seleccionada
                    String rutaArchivo = (String) tablaArchivos.getValueAt(filaSeleccionada, 8);
                    reproductor.reproducir(rutaArchivo);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un archivo para reproducir");
                }
            }
        });
        
        //evento para el boton de pausa
        btnPausa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.pausar();
            }
        });
        
        // Evento para el boton de detener
        btnDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.detener();
            }
        });
        
        
        
    }
    
    private void agregarEventosComboBox(){
        opcionesComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcionSeleccionada = (String) opcionesComboBox.getSelectedItem();
                
                switch (opcionSeleccionada){
                    case "Mostrar duplicados" : mostrarDuplicados(); break;
                    case "Ver más grandes" : busqueda.mostrarArchivosMasGrandes(); break;
                    case "Eliminar archivo(s)" : eliminarArchivoSeleccionado(); break;
                    default : break;
                }
            }
            
        });
        
        comboBoxPlaylists.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcionSeleccionada2 = (String) comboBoxPlaylists.getSelectedItem();
                switch (opcionSeleccionada2){
                    case "Agregar a playlist" : String nombreArchivo = obtenerNombreArchivoSeleccionado();
                                                String rutaArchivo = obtenerRutaArchivoSeleccionado();
                                                if (nombreArchivo != null) {
                                                VentanaPlaylist ventanaPlaylist = new VentanaPlaylist(nombreArchivo, rutaArchivo);
                                                ventanaPlaylist.setVisible(true);
                                                } break;
                    case "Gestionar Playlists" : VentanaGestionarPlaylists v2 = new VentanaGestionarPlaylists(Interfaz.this); v2.setVisible(true); break;
                    default : break;
                }
            }
        });
        
        comboBoxBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcionSeleccionada3 = (String) comboBoxBuscar.getSelectedItem();
                String terminoBusqueda = null;
                switch (opcionSeleccionada3) {
                case "Canción": terminoBusqueda = JOptionPane.showInputDialog("Ingrese la canción a buscar");
                buscarEnTabla(terminoBusqueda, 0);  // Buscar en la columna de nombre
                break;
            case "Artista": terminoBusqueda = JOptionPane.showInputDialog("Ingrese el artista a buscar");
                buscarEnTabla(terminoBusqueda, 1);  // Buscar en la columna de artista
                break;
            case "Álbum": terminoBusqueda = JOptionPane.showInputDialog("Ingrese el álbum a buscar");
                buscarEnTabla(terminoBusqueda, 2);  // Buscar en la columna de álbum
                break;
            default:
                break;
        }
            }
        });
    }
    
    private void buscarEnTabla(String termino, int columna) {
        if (termino == null || termino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un término de búsqueda válido.");
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
            JOptionPane.showMessageDialog(this, "No se encontraron resultados para la búsqueda");
        } else {
            tablaArchivos.setModel(modeloFiltrado);
        }
    }
    
    // Método para mostrar las canciones de una playlist en la tabla
    public void mostrarPlaylistEnTabla(List<File> cancionesPlaylist) {
        vaciarTabla();  // Limpiar la tabla antes de mostrar la playlist
        lblEspacioArchivos.setText("");
        DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();
        for (File cancion : cancionesPlaylist) {
            busqueda.agregarMetadatosTabla(cancion, modelo);
        }
    }

    // Método para obtener el nombre del archivo seleccionado en la tabla
    public String obtenerNombreArchivoSeleccionado() {
        int filaSeleccionada = tablaArchivos.getSelectedRow();
        if (filaSeleccionada != -1) {
            return (String) tablaArchivos.getValueAt(filaSeleccionada, 0); // Asume que el nombre está en la columna 0
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo");
            return null;
        }
    }
    
    private String obtenerRutaArchivoSeleccionado(){
        int filaSeleccionada = tablaArchivos.getSelectedRow();
        if (filaSeleccionada != -1) {
            return (String) tablaArchivos.getValueAt(filaSeleccionada, 8); // para recibir la ruta
        } else {
            return null;
        }
    }
    
    public void mostrarCancionesEnTabla(List<String> canciones) {
    DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();
    modeloTabla.setRowCount(0); // Limpiar la tabla antes de mostrar nuevas canciones
    for (String cancion : canciones) {
        modeloTabla.addRow(new Object[]{cancion});
        }
    }
    
    private void mostrarDuplicados(){
        java.util.List<File> archivosDuplicados = busqueda.buscarDuplicados();
        
        if (archivosDuplicados.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se encontraron archivos duplicados");
        } else {
            vaciarTabla(); //limpiar la tabla antes de mostrar los duplicados
            long espacioTotalDuplicados = 0;
            DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();
            for (File archivo : archivosDuplicados){
                espacioTotalDuplicados += archivo.length();
                busqueda.agregarMetadatosTabla(archivo, modelo);
            }
            
            //Actualizar el label para mostrar el espacio ocupado por duplicados
            lblEspacioArchivos.setText("Duplicados: " + archivosDuplicados.size() + " | Espacio ocupado: " + espacioTotalDuplicados / (1024 * 1024) + " MB");
        }
    }
    
    private void eliminarArchivoSeleccionado(){
        int filaSeleccionada = tablaArchivos.getSelectedRow();
        if (filaSeleccionada != -1){
            String rutaArchivo = (String) (tablaArchivos.getValueAt(filaSeleccionada, 8));
            File archivo = new File(rutaArchivo);
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar " + archivo.getName() + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION){
                if (archivo.delete()){
                    ((DefaultTableModel) tablaArchivos.getModel()).removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(null, "Archivo eliminado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el archivo");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un archivo para eliminar");
        }
    }
    
    private void buscarArchivosRutaPredefinida(){
        String rutaPredefinida = rutas.leerRutaObtenida();
        if (rutaPredefinida != null){
            File carpeta = new File(rutaPredefinida);
            if (carpeta.exists() && carpeta.isDirectory()){
                vaciarTabla();
                long espacioTotal = busqueda.buscarArchivosRaiz(carpeta);
                lblEspacioArchivos.setText("Espacio total ocupado: " + (espacioTotal / (1024 * 1024)) + " MB");
            }
        }
    }
    
    private void vaciarTabla(){
        DefaultTableModel modelo = (DefaultTableModel) tablaArchivos.getModel();
        modelo.setRowCount(0);
    }
}
