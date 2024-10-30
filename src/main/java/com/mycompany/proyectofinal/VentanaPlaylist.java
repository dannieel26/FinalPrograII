
package com.mycompany.proyectofinal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class VentanaPlaylist extends JFrame {
    private JTable tablaPlaylist;
    private DefaultTableModel modeloTabla;
    private JButton btnCrearPlaylist, btnBorrarPlaylist, btnAbrirPlaylist;
    
    public VentanaPlaylist(){
        setTitle("Getion de Playlists");
        setSize(400,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        modeloTabla = new DefaultTableModel(new String[]{"Nombre Playlist"}, 0);
        tablaPlaylist = new JTable(modeloTabla);
        
        //Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPlaylist);
        add(scrollPane, BorderLayout.CENTER);
        
        //Agregar los botones
        JPanel buttonPanel = new JPanel();
        btnCrearPlaylist = new JButton("Crear Playlist");
        btnBorrarPlaylist = new JButton("Borrar Playlist");
        btnAbrirPlaylist = new JButton("Abrir Playlist");

        buttonPanel.add(btnCrearPlaylist);
        buttonPanel.add(btnBorrarPlaylist);
        buttonPanel.add(btnAbrirPlaylist);
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

        btnAbrirPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPlaylist();
            }
        });
    }
    
    //Métodos para manejar las acciones
    private void crearPlaylist() {
        
    }

    private void borrarPlaylist() {
        
    }

    private void abrirPlaylist() {
        
    }
}
