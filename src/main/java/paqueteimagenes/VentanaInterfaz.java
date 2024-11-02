
package paqueteimagenes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class VentanaInterfaz extends JFrame {
    
    private JPanel panel;
    private JTextField ctRuta;
    private JButton btnBuscarCarpeta, btnVerImagen, btnMostrarEspacio, btnVerDuplicados, btnEliminarArchivo, btnVerMasGrandes,btnMoverArchivo;
    private JTable tablaArchivos;
    private JScrollPane scrollTabla;
    private JFileChooser fileChooser;
    private JLabel lblEspacioArchivos;
    private JComboBox comboBoxBuscar, comboBoxAgrupar;
    
    public VentanaInterfaz(){
        this.setSize(1100, 550);
        setTitle("Administrador multimedia");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        iniciarComponentes();
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
        ctRuta.setBounds(200, 50, 700, 30);
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
    }
    
    private void colocarTabla(){
        String[] columnas = {"Nombre","Artista","Album","Genero","Año","Duracion","Tamaño","Extension","Ruta"};
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
}
