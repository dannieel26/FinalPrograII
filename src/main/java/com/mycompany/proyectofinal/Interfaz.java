
package com.mycompany.proyectofinal;

import java.awt.Color;
import java.awt.Font;
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
    private JButton btnBuscarCarpeta,btnReproducir,btnPausa,btnDetener,btnVerLetra;
    private JTable tablaArchivos;
    private JComboBox<String> opcionesComboBox, comboBoxPlaylists, comboBoxBuscar,comboBoxOrdenar;
    private JScrollPane scrollTabla;
    private JFileChooser fileChooser;
    private Rutas rutas;
    private Busqueda busqueda;
    private Reproductor reproductor;
    private JLabel lblEspacioArchivos;
    private GestorArchivos gestorArchivos;
    
    //Constructor
    public Interfaz(){
        this.setSize(1100, 550);
        setTitle("Administrador multimedia");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        rutas = new Rutas();
        reproductor = new Reproductor();
        gestorArchivos = new GestorArchivos();
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
        panel.setBackground(new Color(217,250,182));
        this.getContentPane().add(panel);
    }
    
    //Métodos gráficos
    private void colocarEtiquetas(){
        JLabel etiqueta1 = new JLabel("Administrador multimedia");
        etiqueta1.setBounds(425,15,230,20);
        etiqueta1.setFont(new Font("arial",1,18));
        etiqueta1.setForeground(new Color(0,127,0)); // Verde oscuro
        panel.add(etiqueta1);
    }
    
    private void colocarCajaTexto(){
        ctRuta = new JTextField();
        ctRuta.setBounds(200, 50, 700, 30);
        ctRuta.setBackground(Color.WHITE);
        ctRuta.setEditable(false);
        panel.add(ctRuta);
    }
    
    private void colocarBotones(){
        btnBuscarCarpeta = new JButton("Buscar Carpeta");
        btnBuscarCarpeta.setBounds(35, 45, 140, 40);
        btnBuscarCarpeta.setBackground(new Color(0,127,0)); // Botón verde oscuro
        btnBuscarCarpeta.setForeground(Color.WHITE);
        panel.add(btnBuscarCarpeta);
        
        btnReproducir = new JButton("Reproducir");
        btnReproducir.setBounds(35, 390, 110, 40);
        btnReproducir.setBackground(new Color(2,63,64));
        btnReproducir.setForeground(Color.WHITE);
        panel.add(btnReproducir);
        
        btnPausa = new JButton("Pausa");
        btnPausa.setBounds(160, 390, 110, 40);
        btnPausa.setBackground(new Color(2,63,64));
        btnPausa.setForeground(Color.WHITE);
        panel.add(btnPausa);
        
        btnDetener = new JButton("Detener");
        btnDetener.setBounds(285, 390, 110, 40);
        btnDetener.setBackground(new Color(2,63,64));
        btnDetener.setForeground(Color.WHITE);

        panel.add(btnDetener);
        
        btnVerLetra = new JButton("Ver letra");
        btnVerLetra.setBounds(410, 390, 110, 40);
        btnVerLetra.setBackground(new Color(0,130,132));
        btnVerLetra.setForeground(Color.WHITE);
        panel.add(btnVerLetra);
    }
    
    private void colocarTabla(){
        String[] columnas = {"Nombre","Artista","Album","Genero","Año","Duracion","Tamaño","Extension","Ruta"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaArchivos = new JTable(modeloTabla);
        
        scrollTabla = new JScrollPane (tablaArchivos);
        scrollTabla.setBounds(35, 120, 1010, 240);
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
        comboBoxPlaylists = new JComboBox<>();
        comboBoxPlaylists.addItem("Opciones playlist");
        comboBoxPlaylists.addItem("Agregar a playlist");
        comboBoxPlaylists.addItem("Gestionar Playlists");
        comboBoxPlaylists.setBounds(535,390,120,40);
        comboBoxPlaylists.setForeground(Color.WHITE);
        comboBoxPlaylists.setBackground(new Color(0,130,132));
        panel.add(comboBoxPlaylists);
        
        comboBoxBuscar = new JComboBox<>();
        comboBoxBuscar.addItem("Buscar por...");
        comboBoxBuscar.addItem("Canción");
        comboBoxBuscar.addItem("Artista");
        comboBoxBuscar.addItem("Álbum");
        comboBoxBuscar.setBackground(new Color(2,63,64));
        comboBoxBuscar.setForeground(Color.WHITE);
        comboBoxBuscar.setBounds(670, 390, 110, 40);
        panel.add(comboBoxBuscar);
        
        comboBoxOrdenar = new JComboBox<>();
        comboBoxOrdenar.addItem("Ordenar por...");
        comboBoxOrdenar.addItem("Artista");
        comboBoxOrdenar.addItem("Album");
        comboBoxOrdenar.addItem("Genero");
        comboBoxOrdenar.addItem("Año");
        comboBoxOrdenar.addItem("Duración");
        comboBoxOrdenar.setBounds(795, 390, 105, 40);
        comboBoxOrdenar.setBackground(new Color(2,63,64));
        comboBoxOrdenar.setForeground(Color.WHITE);
        panel.add(comboBoxOrdenar);
        
        opcionesComboBox = new JComboBox<>();
        opcionesComboBox.addItem("Seleccionar opción");
        opcionesComboBox.addItem("Mostrar duplicados");
        opcionesComboBox.addItem("Ver más grandes");
        opcionesComboBox.addItem("Eliminar archivo(s)");
        opcionesComboBox.addItem("Mover archivo(s)");
        opcionesComboBox.setBounds(915, 390, 135, 40);
        opcionesComboBox.setBackground(new Color(2,63,64));
        opcionesComboBox.setForeground(Color.WHITE);
        panel.add(opcionesComboBox);
    }
    
    private void agregarEventos(){
        Eventos eventos = new Eventos(fileChooser, ctRuta, busqueda, rutas, lblEspacioArchivos);
        //Agregar evento del botón
        btnBuscarCarpeta.addActionListener(eventos);
        btnReproducir.addActionListener(e -> reproducirArchivo());
        btnPausa.addActionListener(e -> reproductor.pausar());
        btnDetener.addActionListener(e -> reproductor.detener());
    }
    
    private int obtenerFilaSeleccionada() {
        return tablaArchivos.getSelectedRow();
    }
    
    private void reproducirArchivo() {
        int filaSeleccionada = obtenerFilaSeleccionada();
        if (filaSeleccionada != -1) { // Verificar si hay una fila seleccionada
            String rutaArchivo = obtenerRutaArchivoSeleccionado();

            // Verificar la extensión del archivo
            if (rutaArchivo.endsWith(".mp4") || rutaArchivo.endsWith(".flv")) {
                // Abrir el reproductor de video
                VentanaReproductor ventanaReproductor = new VentanaReproductor(rutaArchivo);
                ventanaReproductor.setVisible(true);
            } else {
                // Reproducir el archivo de audio normalmente
                reproductor.reproducir(rutaArchivo);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un archivo para reproducir");
        }
    }
    
    public void abrirReproductorDeVideo(String archivo) {
        // Crear y mostrar la ventana del reproductor de video
        VentanaReproductor ventanaReproductor = new VentanaReproductor(archivo);
        ventanaReproductor.setVisible(true);
    }
    
    private void agregarEventosComboBox(){
        opcionesComboBox.addActionListener(e -> manejarOpcionesComboBox());        
        comboBoxPlaylists.addActionListener(e -> manejarComboBoxPlaylists());
        comboBoxBuscar.addActionListener(e -> manejarComboBoxBuscar());
        comboBoxOrdenar.addActionListener(e -> manejarComboBoxOrdenar());
    }
    
    private void manejarOpcionesComboBox(){
        String opcionSeleccionada = (String) opcionesComboBox.getSelectedItem();
        switch (opcionSeleccionada){
            case "Mostrar duplicados" : mostrarDuplicados(); break;
            case "Ver más grandes" : busqueda.mostrarArchivosMasGrandes(); break;
            case "Eliminar archivo(s)" : eliminarArchivoSeleccionado(); break;
            case "Mover archivo(s)" : moverArchivoSeleccionado(); break;
            default : break;
        }
    }
    
    private void manejarComboBoxPlaylists(){
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
    
    private void manejarComboBoxBuscar(){
        String opcionSeleccionada3 = (String) comboBoxBuscar.getSelectedItem();
        String terminoBusqueda = null;
        switch (opcionSeleccionada3) {
            case "Canción": terminoBusqueda = JOptionPane.showInputDialog("Ingrese la canción a buscar");
                busqueda.buscarEnTabla(terminoBusqueda, 0);  // Buscar en la columna de nombre
                break;
            case "Artista": terminoBusqueda = JOptionPane.showInputDialog("Ingrese el artista a buscar");
                busqueda.buscarEnTabla(terminoBusqueda, 1);  // Buscar en la columna de artista
                break;
            case "Álbum": terminoBusqueda = JOptionPane.showInputDialog("Ingrese el álbum a buscar");
                busqueda.buscarEnTabla(terminoBusqueda, 2);  // Buscar en la columna de álbum
                break;
            default: break;
        }
    }
    
     private void manejarComboBoxOrdenar() {
        String opcionSeleccionada = (String) comboBoxOrdenar.getSelectedItem();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaArchivos.getModel();

        // Verifica si hay filas en la tabla
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay archivos para ordenar.");
            return;
        }

        switch (opcionSeleccionada) {
            case "Artista": gestorArchivos.ordenarColumna(modeloTabla, 1); break;
            case "Album": gestorArchivos.ordenarColumna(modeloTabla, 2); break;
            case "Genero": gestorArchivos.ordenarColumna(modeloTabla, 3); break;
            case "Año": gestorArchivos.ordenarColumna(modeloTabla, 4); break;
            case "Duración": gestorArchivos.ordenarColumna(modeloTabla, 5); break;
            default: break;
        }
    }
    
    private void moverArchivoSeleccionado(){
        File archivoSeleccionado = obtenerArchivoSeleccionado();
        if (archivoSeleccionado != null) {
            gestorArchivos.moverArchivo(archivoSeleccionado); // Enviar archivo a GestorArchivos
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un archivo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        int filaSeleccionada = obtenerFilaSeleccionada();
        if (filaSeleccionada != -1) {
            return (String) tablaArchivos.getValueAt(filaSeleccionada, 0); // Asume que el nombre está en la columna 0
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún archivo");
            return null;
        }
    }
    
    // Método para obtener la ruta del archivo seleccionado como String
    private String obtenerRutaArchivoSeleccionado() {
        File archivo = obtenerArchivoSeleccionado();
        return archivo != null ? archivo.getPath() : null;
}

    // Método para obtener el archivo seleccionado como File
    private File obtenerArchivoSeleccionado() {
        int filaSeleccionada = obtenerFilaSeleccionada();
        if (filaSeleccionada != -1) { // Verifica si hay una fila seleccionada
            String rutaArchivo = (String) tablaArchivos.getValueAt(filaSeleccionada, 8);
            return new File(rutaArchivo); // Devuelve el archivo
        }
        return null; // No hay selección
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
            String rutaArchivo = obtenerRutaArchivoSeleccionado();
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
