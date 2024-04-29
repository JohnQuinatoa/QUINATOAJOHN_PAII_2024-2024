package personajes;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import Escenario.Bala;
import Interfaces.Drawable;
import Interfaces.Movable;
import Interfaces.Shootable;
import Ventana.Window;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nave extends JPanel implements Drawable, Movable, Shootable{
   
	// cordenadas para dibujar al marciano dentro del panel
    int[] puntosX = {35, 70, 0}; //cordenadas en x dentro del panel
    int[] puntosY = {0, 80, 80}; //cordenadas en y dentro del panel    
        
    private Set<Integer> conjuntoTeclas = new HashSet<>();// conjunto que almacena las teclas presionadas de mi vetana
    private Timer movementTimer; // un temporizador apra mantener el movimiento constante si se aplastan otras teclas

    private long finalDispario = 0; // Almacena el tiempo del último disparo
    
    public Nave() {
    	
        this.setBackground(new Color(0, 0, 0, 0));// Establece el color de fondo y el tamaño del JPanel para que sea lo suficientemente grande
        this.setBounds(360, 456, 70, 80); // dibuja el panel donde se dibuja el triangulo        
        setFocusable(true); // obtiene el foco cunado estoy presionando teclas
        configurarTemporizador(); // 
        
    } 
    
    @Override // dibuja la nave en mi vetana
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillPolygon(puntosX, puntosY, 3);   
    }   
    
    @Override // llamando al metodo pone la vane en mi ventana
    public void draw() {    	
    	Window.getInstance().getLienzo().add(this);// Agregamos este JPanel al lienzo de la ventana
        Window.getInstance().getLienzo().repaint();// repinta el liezo asi no se borra los objetos 
    }  
    

    @Override
    public void move() {

    	// metodo que recibe cunado una tecla esta sienod presionada
        addKeyListener(new KeyAdapter() {
        	
            @Override // metodo que se llama cundao una tecla es presionada 
            public void keyPressed(KeyEvent e) {
                conjuntoTeclas.add(e.getKeyCode());
                actualizarPosicion();
            }

            @Override // metodo que se llama cuando la telca es liberada
            public void keyReleased(KeyEvent e) {
                conjuntoTeclas.remove(e.getKeyCode());
                if (!teclaEspecifica(e.getKeyCode())) {
                    actualizarPosicion();
                }
            }
        });
        
    }
    
    // metodo para configurar e iniciar el temporizador 
    private void configurarTemporizador() {
        movementTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPosicion();
            }
        });
        movementTimer.start();
    }
    
    // metodo que devulve un verdadero o falso si la tecla aplastada es la correcta
    private boolean teclaEspecifica(int keyCode) {
        return keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A ||
               keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D;
    }
    
    // metodo que hace que la anve se mueva repintando segun cuantos pixeles quiero que se mueva por presionar la tecla
    private void actualizarPosicion() {
    	
        if (conjuntoTeclas.contains(KeyEvent.VK_LEFT) || conjuntoTeclas.contains(KeyEvent.VK_A)) {
            if (getX() > 0) {            	
                setBounds(getX() - 7, getY(), getWidth(), getHeight());
            }
        }
        
        if (conjuntoTeclas.contains(KeyEvent.VK_RIGHT) || conjuntoTeclas.contains(KeyEvent.VK_D)) {
        	
            if (getX() < 730) {
                setBounds(getX() + 7, getY(), getWidth(), getHeight());
            }
        }
        
        if(conjuntoTeclas.contains(KeyEvent.VK_H)) {      	     
        	
        	shoot();    	
        	
        }
        
        // Solicita que el lienzo de la ventana principal se repinte
        Window.getInstance().getLienzo().repaint();
    }   
    
    @Override // metod que hace que se dibuje la bala  
    public void shoot() {
    	
    	// obtiene el tiempo actual en milisegundos
        long inicioDisparo = System.currentTimeMillis();
        
        if (inicioDisparo - finalDispario >= 150) { // Permite tres disparos por segundo no mas de eso
            int balaX = getX() + getWidth() / 2 - Bala.ANCHO / 2; // Centro horizontal de la nave
            int balaY = getY() - Bala.ALTO ; // Justo por encima de la nave

            Bala bala = new Bala(balaX, balaY);
            bala.draw();
            bala.move();

            finalDispario = inicioDisparo;
        }
    }    
    
}