package Escenario;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;

import Interfaces.Drawable;
import Ventana.Window;

public class Escore extends JPanel implements Drawable{
	
	private int Puntaje;
	
	private static Escore instancia;
	
    public Escore() {
    	
    	this.Puntaje = 0;
        // Establecer el tamaño y la posición del panel
        this.setBounds(680, 0, 110, 60); // Altura aumentada para bordes más grandes
        this.setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Aplicar suavizado al texto para un aspecto más limpio
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Establecer la fuente
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

        // Dibujar "Escore"
        FontMetrics metrics = g2d.getFontMetrics();
        String textoEscore = "Escore";
        int xEscore = (getWidth() - metrics.stringWidth(textoEscore)) / 2;
        int yEscore = getHeight()/2 ; // Un tercio de la altura para "Escore"
        g2d.setColor(Color.WHITE);
        g2d.drawString(textoEscore, xEscore, yEscore);

        // Dibujar el puntaje
        String textoPuntaje = String.valueOf(Puntaje);
        int xPuntaje = (getWidth() - metrics.stringWidth(textoPuntaje)) / 2;
        int yPuntaje = getHeight()-2 ; // Dos tercios de la altura para el puntaje
        g2d.drawString(textoPuntaje, xPuntaje, yPuntaje);
    }

    
    public void draw() {
        Window.getInstance().getLienzo().add(this); // Agregamos este JPanel al lienzo de la ventana
        Window.getInstance().getLienzo().repaint();
    }
    
    
    public void aumetnarPuntaje(int cantidad) {    	
        Puntaje += cantidad;
        repaint(); 
    }
    
    public static Escore GetInstance() {
        if (instancia == null) {
            instancia = new Escore();
        }
        return instancia;
    }
    
    public int getPuntaje() {
    	return Puntaje;
    }
}
