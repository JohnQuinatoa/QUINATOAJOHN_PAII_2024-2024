package personajes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import javax.swing.JPanel;

import Escenario.Bala;
import Escenario.Linea;
import Escenario.Vida;
import Interfaces.Deadable;
import Interfaces.Drawable;
import Interfaces.Movable;
import Ventana.Window;

public class Marciano extends JPanel implements Drawable, Movable, Deadable{
	
	// para que salgo un marciano aletrioamente en la pantalla cada vez que se llama 
	Random ran = new Random();
	int numer = ran.nextInt(711)+10;
	
	private boolean vivo = true; // si vivo cambia a false no se dibuja y el amrciano desaprece de la pantalla 
		
	// cordenadas para dibujar al marciano dentro del panel
    int[] puntosX = {0, 80, 80, 40, 0}; 
    int[] puntosY = {0, 0, 80, 35, 80}; 
    
    // creamos una lista que no tenga problemas con añadir o eliminar elementos
    private static List<Marciano> marcianosActivos = new CopyOnWriteArrayList<>();
    
    private boolean atacar = false;
    private Timer temporizador;
    
    
	public Marciano( ) {

        this.setBackground(new Color(0, 0, 0, 0)); // Fondo transparente
        this.setBounds(numer, 20, 80, 80); // ajusta el panel        
        marcianosActivos.add(this); // agrega un objeto marciano a la lista cuando se incia
        
	}
	
	// metodo apra dibujar el marciano si es que esta vivo caso contrario no lo hara
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (vivo) {
            g.setColor(Color.GREEN);
            g.fillPolygon(puntosX, puntosY, puntosX.length);
        }

    }   
    	
    @Override // pone el marciano en el panel
    public void draw() {    
    	
    	// con el metodo se asegura que este en el hilo apra evitar problemas de concurrencia al crear otros marcianos
        SwingUtilities.invokeLater(() -> {
            Window.getInstance().getLienzo().add(this);
            Window.getInstance().getLienzo().repaint();
        });
        
    }	
    
    @Override
    public void move() { 
    	
        temporizador = new Timer(20, new ActionListener() {
        	
            @Override // metodo que se llama cada vez que el temporizador dispara un evento
            public void actionPerformed(ActionEvent e) {                
                setBounds(getX(), getY() + 5, getWidth(), getHeight());
                Window.getInstance().getLienzo().repaint(); 

                // if para evaluar el movimiento del marciando y pues si cumple als condiciones desaprece de la ventana
                if (verificarLinea(Linea.getInstancia()) && !atacar && Vida.getInstance().getSeguir()) {
                    Vida.getInstance().disminuirVida(20); // dmiminuye la vida en 20 porciento
                    Vida.getInstance().lose(); // evalua la vida si llega a cero pierde
                    atacar = true;
                    temporizador.stop();                    
                }
            }
        });
        
        temporizador.start(); // Inicia el Timer
    }  
    
    @Override // hace que desaparezca el marciano cuando se lo llama     
    public void dead() {
    	
    	// con el metodo se asegura que este en el hilo apra evitar problemas de concurrencia al crear otros marcianos
    	SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            Window.getInstance().getLienzo().remove(this);
            if (temporizador != null) {
                temporizador.stop(); // Detiene el Timer cuando el marciano muere
            }
        });
        marcianosActivos.remove(this); // Elimina el marciano de la lista de activos.        
    }

    // metodo que verifica la colision entre la bala y el marciano
    public boolean verificarColision(Bala bala) {
        Rectangle areaMarciano = this.getBounds();
        Rectangle areaBala = bala.getBounds();
        return areaMarciano.intersects(areaBala);
    }
    
    // verifica la colision entre la linea y el marciano
    public boolean verificarLinea(Linea linea) {
        Rectangle areaMarciano = this.getBounds();
        Rectangle areaLinea = linea.getBounds();
        // Ajusta el área de la línea para que sea 5 píxeles más grande en la dirección en la que se mueve el Marciano.
        Rectangle areaLineaAjustada = new Rectangle(areaLinea.x, areaLinea.y - 65, areaLinea.width, areaLinea.height);
        
        if (areaMarciano.y > areaLineaAjustada.y + areaLineaAjustada.height) {
            return true;
        }
        return false;
    }
    
    public static List<Marciano> getMarcianosActivos() {
        return marcianosActivos;
    } 
    
}
