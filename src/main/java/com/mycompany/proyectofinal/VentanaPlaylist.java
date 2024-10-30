

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
    private JButton btnConfirmar, btnCancelar;
    
    public VentanaPlaylist(String nombreArchivo){
        setTitle("Getion de Playlists");
        setSize(400,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        modeloTabla = new DefaultTableModel(new String[]{"Agregar " + nombreArchivo + " a..."}, 0);
        tablaPlaylist = new JTable(modeloTabla);
        
        //Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPlaylist);
        add(scrollPane, BorderLayout.CENTER);
        
        //Agregar los botones
        JPanel buttonPanel = new JPanel();
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnConfirmar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);

        //Action listeners para hacer que el boton ejecute las acciones
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAPlaylist();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAPrincipal();
            }
        });
    }
    
    //Métodos para los botones
    private void agregarAPlaylist(){
        
    }
    
    private void regresarAPrincipal(){
        
    }
    
}