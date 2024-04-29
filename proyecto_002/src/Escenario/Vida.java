package Escenario;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Interfaces.Drawable;
import Interfaces.Loseable;
import Ventana.Window;

public class Vida extends JPanel implements Drawable, Loseable {
	
    private int porcentajeVida;
    private static Vida instancia;
    private final int anchoBarra = 304; // Ancho total de la barra de vida
    private static boolean seguir = true;

    public Vida() {
    	
        // Inicializar la vida al 100%
        this.porcentajeVida = 100;
        // Establecer el tamaño y la posición del panel
        this.setBounds(10, 10, anchoBarra, 30);
        this.setOpaque(false);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar el borde de la barra de vida
        g.setColor(Color.GRAY); // Color del borde aplatinado
        g.fillRect(0, 0, anchoBarra+2, 30); // Fondo del borde

        // Hacer el interior transparente
        g.setColor(new Color(10,10,10)); // Establecer el color de fondo del componente
        g.fillRect(2, 2, anchoBarra - 4, 26);

        // Dibujar el relleno de la barra de vida
        g.setColor(new Color(0, 71, 171)); // Color de la vida
        g.fillRect(2, 2, porcentajeVida * 3, 26); // Cada porcentaje es 4 píxeles

        // Dibujar el texto de la vida
        g.setColor(Color.WHITE); // Color del texto
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        FontMetrics metrics = g.getFontMetrics();
        String textoVida = "Vida " + porcentajeVida + "%";
        // Centrar texto
        int x = (anchoBarra - metrics.stringWidth(textoVida)) / 2;
        int y = ((30 - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(textoVida, x, y);
    }
    
    @Override
    public void draw() {
        Window.getInstance().getLienzo().add(this); // Agregamos este JPanel al lienzo de la ventana
        Window.getInstance().getLienzo().repaint();
    }

    public void disminuirVida(int cantidad) {
        // Asegurarse de que la vida no sea menor que 0
        porcentajeVida -= cantidad;
        repaint(); // Repinta directamente el componente
    }

    public static Vida getInstance() {
        if (instancia == null) {
            instancia = new Vida();
        }
        return instancia;
    }
    
    @Override
    public void lose() {
    	
        if (porcentajeVida <= 0) {
            seguir = false;
            int puntajeMaximo = Escore.GetInstance().getPuntaje();
            String mensaje = "Game Over\nTu puntaje máximo fue: " + puntajeMaximo;

            // Crear un botón para cerrar el juego
            JButton botonAceptar = new JButton("Aceptar");
            botonAceptar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); // Cerrar el juego
                }
            });

            // Crear el JOptionPane con el mensaje y el botón
            final JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{botonAceptar}, botonAceptar);

            // Crear el JDialog y configurarlo
            final JDialog dialog = new JDialog();
            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Evitar que se cierre con el botón de cierre de la ventana
            dialog.setTitle("PERDSITEEEE!!!!!!!");
            dialog.setModal(true); // Bloquear el resto de la interfaz
            dialog.pack(); // Ajustar el tamaño
            dialog.setLocationRelativeTo(null); // Centrar en la pantalla
            dialog.setVisible(true); // Mostrar el diálogo
        }
    }
    
    public boolean getSeguir() {
    	return seguir;
    }    
    
}