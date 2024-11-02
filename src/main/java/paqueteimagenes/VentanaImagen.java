
package paqueteimagenes;

import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Pablo
 */
public class VentanaImagen extends JFrame {

    private JLabel labelImagen;
    private ImageIcon imagenOriginal;
    private Timer timer; // Temporizador para retrasar el escalado

    public VentanaImagen(String rutaImagen) {
        setTitle("Visualizar Imagen");
        setSize(800, 800);
        setLocationRelativeTo(null);

        labelImagen = new JLabel();
        labelImagen.setHorizontalAlignment(JLabel.CENTER);
        add(labelImagen);

        imagenOriginal = new ImageIcon(rutaImagen);

        // Mostrar la imagen por primera vez
        ajustarImagen();

        //temporizador con un retraso de 100 ms para redimensionar la imagen
        timer = new Timer(100, e -> ajustarImagen());
        timer.setRepeats(false); // Solo ejecuta una vez tras el último evento

        // listener para iniciar el temporizador en cada redimensionamiento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (timer.isRunning()) {
                    timer.restart(); // Reinicia el temporizador si ya está en marcha
                } else {
                    timer.start(); // Inicia el temporizador
                }
            }
        });
    }

    private void ajustarImagen() {
        int ventanaAncho = getWidth();
        int ventanaAlto = getHeight();

        // Obtener las dimensiones de la imagen original
        int imgAncho = imagenOriginal.getIconWidth();
        int imgAlto = imagenOriginal.getIconHeight();

        // Calcular la escala manteniendo la proporción
        float escalaAncho = (float) ventanaAncho / imgAncho;
        float escalaAlto = (float) ventanaAlto / imgAlto;
        float escala = Math.min(escalaAncho, escalaAlto);

        int nuevoAncho = (int) (imgAncho * escala);
        int nuevoAlto = (int) (imgAlto * escala);

        // Escalar la imagen y actualizar el JLabel
        Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        labelImagen.setIcon(new ImageIcon(imagenEscalada));
    }
}
