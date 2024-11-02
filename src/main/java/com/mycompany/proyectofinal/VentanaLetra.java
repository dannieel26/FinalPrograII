
package com.mycompany.proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class VentanaLetra extends JFrame {
    public VentanaLetra(String letra) {
        setTitle("Letra de la Canción");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(1, 50, 32));

        // JTextPane para mostrar la letra y permitir centrado
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(new Color(1, 50, 32));
        textPane.setForeground(Color.WHITE);
        textPane.setFont(new Font("Arial", Font.BOLD, 24));
        textPane.setText(letra);

        // Alinear el texto horizontalmente
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar la barra de desplazamiento vertical
        
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }
}
