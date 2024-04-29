package Escenario;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import Interfaces.Drawable;
import Ventana.Window;

public class Linea extends JPanel implements Drawable{
	
	private static Linea  instancia;
    
    public Linea() {
        // Establece el color de fondo y el tamaño del JPanel para que sea lo suficientemente grande
        this.setBackground(new Color(0, 0, 0, 0)); 
        // Ajusta la posición y el tamaño del JPanel para que coincida con el área del triángulo
        this.setBounds(0, 398, Window.getInstance().getWidth(), 4);
        
        instancia = this;
    } 
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        // Dibuja un rectángulo que funcionará como una línea de 4 píxeles de grosor
        g.fillRect(0, 0, this.getWidth(), 4);
    }   
    
    @Override
    public void draw() {    	
    	Window.getInstance().getLienzo().add(this);// Agregamos este JPanel al lienzo de la ventana
        Window.getInstance().getLienzo().repaint();
    }
    
    public static Linea getInstancia() {
        return instancia; // Retorna la instancia actual de Linea.
    }

}
