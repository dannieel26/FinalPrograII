
package com.mycompany.proyectofinal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VentanaReproductor extends JFrame {
    private Reproductor reproductor;

    public VentanaReproductor(String rutaArchivo) {
        setTitle("Reproductor de Video");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        reproductor = new Reproductor();

        // Crear panel de controles
        JPanel panelControles = new JPanel();
        JButton btnReproducir = new JButton("Reproducir");
        JButton btnPausar = new JButton("Pausar");
        JButton btnDetener = new JButton("Detener");

        // Agregar acción a los botones
        btnReproducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.reproducir(rutaArchivo);
                
            }
        });

        btnPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.pausar();
            }
        });

        btnDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.detener();
            }
        });

        panelControles.add(btnReproducir);
        panelControles.add(btnPausar);
        panelControles.add(btnDetener);

        // Agregar el componente de video y el panel de controles a la ventana
        this.add(reproductor.getMediaPlayerComponent(), BorderLayout.CENTER);
        this.add(panelControles, BorderLayout.SOUTH);

        setVisible(true);

        // Reproducir el archivo después de que la ventana esté visible
        reproducirVideo(rutaArchivo);
    }

    private void reproducirVideo(String rutaArchivo) {
        // Esperar un poco antes de intentar reproducir, para asegurarse de que el componente esté listo
        SwingUtilities.invokeLater(() -> {
            reproductor.reproducir(rutaArchivo);
        });
    }
    
    @Override
    public void dispose() {
        // Detener la reproducción al cerrar la ventana
        reproductor.detener();
        super.dispose();
    }
}
