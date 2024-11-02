
package com.mycompany.proyectofinal;

import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaGestionarPlaylists extends JFrame {
    private JTable tablaPlaylists;
    private DefaultTableModel modeloTabla;
    private PlaylistManager playlistManager;
    private Interfaz interfaz;

    public VentanaGestionarPlaylists(Interfaz interfaz) {
        this.interfaz = interfaz;
        setTitle("Gestión de Playlists");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialización de PlaylistManager y tabla
        playlistManager = new PlaylistManager();
        tablaPlaylists = new JTable(new DefaultTableModel(new Object[]{"Nombre de Playlist"}, 0));
        modeloTabla = (DefaultTableModel) tablaPlaylists.getModel();
        
        JButton btnCrearPlaylist = new JButton("Crear Playlist");
        btnCrearPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrePlaylist = JOptionPane.showInputDialog("Ingrese el nombre de la nueva playlist:");
                if (nombrePlaylist != null && !nombrePlaylist.trim().isEmpty()) {
                    playlistManager.agregarPlaylist(nombrePlaylist);
                    cargarPlaylists();
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre de la playlist no puede estar vacío.");
                }
            }
        });

        JButton btnBorrarPlaylist = new JButton("Borrar Playlist");
        btnBorrarPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaPlaylists.getSelectedRow();
                if (selectedRow != -1) {
                    String nombrePlaylist = (String) modeloTabla.getValueAt(selectedRow, 0);
                    playlistManager.borrarPlaylist(nombrePlaylist); // Método que elimina la playlist y el archivo asociado
                    cargarPlaylists(); // Actualizar la tabla
                }
            }
        });
        
        JButton btnAbrirPlaylist = new JButton("Abrir Playlist");
        btnAbrirPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaPlaylists.getSelectedRow();
                if (selectedRow != -1) {
                    String nombrePlaylist = (String) modeloTabla.getValueAt(selectedRow, 0);
                    List<String> cancionesNombres = playlistManager.obtenerCancionesDePlaylist(nombrePlaylist); // Método que devuelve los nombres de las canciones de la playlist

                    List<File> canciones = new ArrayList<>();
                    for (String nombre : cancionesNombres) {
                        canciones.add(new File(nombre)); // Crear un objeto File para cada nombre de canción
                    }

                    interfaz.mostrarPlaylistEnTabla(canciones);
                    dispose(); // Cierra la ventana de gestionar playlists
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una playlist para abrir.");
                }
            }
        });
            
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCrearPlaylist);
        panelBotones.add(btnBorrarPlaylist);
        //panelBotones.add(btnEnviarAInterfaz);
        panelBotones.add(btnAbrirPlaylist);

        setLayout(new BorderLayout());
        add(new JScrollPane(tablaPlaylists), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar las playlists en la tabla al abrir la ventana
        cargarPlaylists();
    }

    // Método para cargar playlists desde el archivo a la tabla
    private void cargarPlaylists() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        List<String> playlists = playlistManager.cargarPlaylists();
        for (String playlist : playlists) {
            modeloTabla.addRow(new Object[]{playlist});
        }
    }
}
