
package com.mycompany.proyectofinal;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class VentanaGestionarPlaylists extends JFrame{
    private JTable tablaPlaylist;
    private DefaultTableModel modeloTabla;
    private JButton btnCrearPlaylist, btnBorrarPlaylist, btnVerPlaylist;
    
    public VentanaGestionarPlaylists(){
        setTitle("Getion de Playlists");
        setSize(400,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        modeloTabla = new DefaultTableModel(new String[]{"Lista de playlists"}, 0);
        tablaPlaylist = new JTable(modeloTabla);
        
        //Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPlaylist);
        add(scrollPane, BorderLayout.CENTER);
        
        //Agregar los botones
        JPanel buttonPanel = new JPanel();
        btnCrearPlaylist = new JButton("Crear Playlist");
        btnBorrarPlaylist = new JButton("Borrar Playlist");
        btnVerPlaylist = new JButton("Ver Playlist");

        buttonPanel.add(btnCrearPlaylist);
        buttonPanel.add(btnBorrarPlaylist);
        buttonPanel.add(btnVerPlaylist);
        add(buttonPanel, BorderLayout.SOUTH);

        //Action listeners para hacer que el boton ejecute las acciones
        btnCrearPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPlaylist();
            }
        });

        btnBorrarPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPlaylist();
            }
        });
        
        btnVerPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verPlaylist();
            }
        });
    }
    
    //Métodos para los botones
    private void crearPlaylist(){
        String nombrePlaylist = JOptionPane.showInputDialog("Ingrese el nombre de la playlist");
    }
    
    private void borrarPlaylist(){
        
    }
    
    private void verPlaylist(){
        
    }
}
