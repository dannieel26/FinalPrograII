
package com.mycompany.proyectofinal;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
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
    private JButton btnBuscarCarpeta,btnReproducir,btnPausa,btnDetener,btnBuscar,btnCrearPlaylist,btnVerLetra;
    private JTable tablaArchivos;
    private JScrollPane scrollTabla;
    private JFileChooser fileChooser;
    private Rutas rutas;
    private Busqueda busqueda;
    private Reproductor reproductor;
    
    //Constructor
    public Interfaz(){
        this.setSize(900, 500);
        setTitle("Administrador multimedia");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        rutas = new Rutas();
        reproductor = new Reproductor();
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
        busqueda = new Busqueda(tablaArchivos);
        agregarEventos();
        buscarArchivosRutaPredefinida();
    }
    
    //Método para colocar el panel a la ventana
    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
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
        
        btnCrearPlaylist = new JButton("Crear Playlist");
        btnCrearPlaylist.setBounds(555, 390, 110, 40);
        panel.add(btnCrearPlaylist);
        
        btnVerLetra = new JButton("Ver Letra");
        btnVerLetra.setBounds(685, 390, 110, 40);
        panel.add(btnVerLetra);
    }
    
    private void colocarTabla(){
        String[] columnas = {"Nombre","Artista","Album","Genero","Año","Duracion","Tamaño","Extension","Ruta"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaArchivos = new JTable(modeloTabla);
        
        scrollTabla = new JScrollPane (tablaArchivos);
        scrollTabla.setBounds(35, 120, 815, 240);
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
    
    private void agregarEventos(){
        Eventos eventos = new Eventos(fileChooser, ctRuta, busqueda, rutas);
        //Agregar evento del botón
        btnBuscarCarpeta.addActionListener(eventos);
        
        //Evento para el boton reproducir
        btnReproducir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaArchivos.getSelectedRow(); // obtener la fila seleccionada de la tabla
                if (filaSeleccionada != -1){ //verificar si hay una fila seleccionada
                    String rutaArchivo = (String) tablaArchivos.getValueAt(filaSeleccionada, 8);
                    reproductor.reproducirArchivo(rutaArchivo);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un archivo para reproducir");
                }
            }
        });
    }
    
    private void buscarArchivosRutaPredefinida(){
        String rutaPredefinida = rutas.leerRutaObtenida();
        if (rutaPredefinida != null){
            File carpeta = new File(rutaPredefinida);
            if (carpeta.exists() && carpeta.isDirectory()){
                busqueda.buscarArchivosRaiz(carpeta);
            }
        }
    }
}
