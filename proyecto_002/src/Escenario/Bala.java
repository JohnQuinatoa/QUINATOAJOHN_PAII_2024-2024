package Escenario;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Interfaces.Deadable;
import Interfaces.Drawable;
import Interfaces.Movable;
import Ventana.Window;
import personajes.Marciano;

public class Bala extends JPanel implements Drawable, Movable, Runnable, Deadable {
	
    private int x; // Posición inicial en X
    private int y; // Posición inicial en Y
    private Thread hiloMovimientoBala; // Hilo para la animación
    public static final int ANCHO = 15; // Ancho de la bala
    public static final int ALTO = 15;
    
    //  creamos un arreglo que cope sin problema de añadir y elimiar objetos añadidos
    private static List<Bala> balasActivas = new CopyOnWriteArrayList<>();   

    public Bala(int x, int y) {
        this.x = x;
        this.y = y;
        this.setBackground(new Color(0, 0, 0, 0));
        this.setBounds(x, y - ALTO, ANCHO, ALTO); 
        balasActivas.add(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, ANCHO, ALTO); // Dibuja la bala como un óvalo blanco
    }
    
    @Override
    public void draw() {   
    	
    	// metodo que asegura que este trabajando en el hilo apra no tener problemas ocn concurrencia
        SwingUtilities.invokeLater(() -> {
            Window.getInstance().getLienzo().add(this); // Agrega la bala al lienzo
            Window.getInstance().getLienzo().repaint(); // Repinta el lienzo para mostrar la bala
        });
    }
    
    
    @Override
    public void move() {
    	
    	// incia los hilos apr que la bala se pueda mover 
        if (hiloMovimientoBala == null || !hiloMovimientoBala.isAlive()) {
            hiloMovimientoBala = new Thread(this);
            hiloMovimientoBala.start();
        }
        
    }
    
    @Override
    public void run() {
    	
    	// el metodo apra poder iniciar el movimeitno y que desaparezca cunado salga de la pantalla
        while (y > 0) {
        	
            y -= 10;
            
            // programa una tarea para ser ejecutada en el EDT que es el hilo que se maneja las actulizaciones 
            SwingUtilities.invokeLater(() -> {
                this.setLocation(x, y);
                repaint();
            });
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            Window.getInstance().getLienzo().remove(this);
        });
        
    }
    
    @Override
    public void dead() {    
    	
    	// remueve y quita la bala tanto del arreglo como del panel
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            Window.getInstance().getLienzo().remove(this);
        });
        balasActivas.remove(this); // Elimina la bala de la lista de activas.
  	
    }
    
    public static List<Bala> getBalasActivas() {
        return balasActivas;
    }    
    
}