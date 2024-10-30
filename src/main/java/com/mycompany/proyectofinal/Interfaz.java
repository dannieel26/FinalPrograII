
package com.mycompany.proyectofinal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private JComboBox<String> opcionesComboBox;
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
        btnPausa.setBounds(165, 390, 110, 40);
        panel.add(btnPausa);
        
        btnDetener = new JButton("Detener");
        btnDetener.setBounds(295, 390, 110, 40);
        panel.add(btnDetener);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(425, 390, 110, 40);
        panel.add(btnBuscar);
        
        btnPlaylists = new JButton("Playlists");
        btnPlaylists.setBounds(555, 390, 110, 40);
        panel.add(btnPlaylists);
        
        btnVerLetra = new JButton("Ver Letra");
        btnVerLetra.setBounds(685, 390, 110, 40);
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
        opcionesComboBox.setBounds(810, 395, 140, 30);
        panel.add(opcionesComboBox);
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
        
        //Evento para abrir la ventana de playlist conn el boton
        btnPlaylists.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaPlaylist ventanaPlaylist = new VentanaPlaylist();
                ventanaPlaylist.setVisible(true);
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
                    default : JOptionPane.showMessageDialog(null, "Opción no reconocida");
                }
            }
            
        });
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
