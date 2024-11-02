
package com.mycompany.proyectofinal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VentanaPlaylist extends JFrame {
    private JTable tablaPlaylist;
    private DefaultTableModel modeloTabla;
    private JButton btnConfirmar, btnCancelar;
    private PlaylistManager playlistManager; // Para gestionar las playlists
    private String nombreArchivo;
    private String rutaArchivo;

    public VentanaPlaylist(String nombreArchivo, String rutaArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        setTitle("Gestión de Playlists");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        playlistManager = new PlaylistManager();

        modeloTabla = new DefaultTableModel(new String[]{"Agregar " + nombreArchivo + " a..."}, 0);
        tablaPlaylist = new JTable(modeloTabla);
        
        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPlaylist);
        add(scrollPane, BorderLayout.CENTER);
        
        // Agregar los botones
        JPanel buttonPanel = new JPanel();
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnConfirmar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners para los botones
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

        cargarPlaylists(); // Cargar las playlists disponibles al iniciar
    }
    
    private void cargarPlaylists() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<String> playlists = playlistManager.cargarPlaylists();
        for (String playlist : playlists) {
            modeloTabla.addRow(new Object[]{playlist});
        }
    }

    // Método para agregar la canción seleccionada a la playlist elegida
    private void agregarAPlaylist() {
        int selectedRow = tablaPlaylist.getSelectedRow();
        if (selectedRow != -1) {
            String nombrePlaylist = (String) modeloTabla.getValueAt(selectedRow, 0);
            boolean agregado = playlistManager.agregarCancionAPlaylist(nombrePlaylist, rutaArchivo);
            if (agregado) {
                JOptionPane.showMessageDialog(this, "Canción agregada a la playlist " + nombrePlaylist + " exitosamente.");
                dispose(); // Cerrar ventana después de agregar
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la canción a la playlist.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una playlist para agregar la canción.");
        }
    }

    // Método para regresar a la ventana principal
    private void regresarAPrincipal() {
        dispose(); // Cierra la ventana actual
    }
}
