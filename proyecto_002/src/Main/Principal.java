package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import Dependencia.Container;
import Escenario.Bala;
import Escenario.Escore;
import Escenario.Linea;
import Escenario.Vida;
import Ventana.Window;
import personajes.Marciano;
import personajes.Nave;

/**
* 
* @author John Steven Quinatoa Guerrero 
* Tema: Inversion de dependencia
* 
*/
	

public class Principal {
	
	public static void main(String[] args) throws InterruptedException {
		
		Window ventana = new Window();
		Container display = new Container();
        
        // obtenemos el arreglo de las balas para trabajar con ellas en la colision y poner que desaparezcan en patanlla
        List<Bala> balas = Bala.getBalasActivas(); 
        List<Marciano> marcianos = Marciano.getMarcianosActivos();
        
        // dibujamos y activmaos para poder mover la nave
        Nave nave2 = new Nave();        
        display.Dibujar(nave2);
        display.Mover(nave2);
        
        // ponemos la linea en pantalla
        Linea linea = new Linea();
        display.Dibujar(linea);   
               
        // ponemos la vida en pantalla y activamos el metodo perder
        display.Dibujar(Vida.getInstance());      
        
        // pone el Escore en Pantalla
        display.Dibujar(Escore.GetInstance());                
                
        // crea marcinaos en intervalos de 1.5 segundos 
        Timer hiloMarcianos = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Asegúrate de que la nave no sea null y que shoot esté definido correctamente
            	Marciano marciano2 = new Marciano();    
                display.Dibujar(marciano2);
                display.Mover(marciano2);                

            }
        });
       hiloMarcianos.start();    
       
       // evalua la colision llamando al metodo verificar Colisiones
       Timer hiloColisiones = new Timer(100, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
        	   verificarColisiones();
           }
       });
       hiloColisiones.start();      
                    
	}
	
	public static void verificarColisiones() {
	    List<Bala> balasParaEliminar = new ArrayList<>();
	    List<Marciano> marcianosParaEliminar = new ArrayList<>();

	    for (Bala bala : Bala.getBalasActivas()) {
	        for (Marciano marciano : Marciano.getMarcianosActivos()) {
	            if (marciano.verificarColision(bala)) {
	                marcianosParaEliminar.add(marciano);
	                balasParaEliminar.add(bala);
	                marciano.dead();
	                bala.dead();
	                Escore.GetInstance().aumetnarPuntaje(10);	                
	            }
	            if (marciano.verificarLinea(Linea.getInstancia())) {
	                marcianosParaEliminar.add(marciano);
	                marciano.dead();
	            }
	        }
	    }

	    // Elimina todos los marcianos y balas recogidos
	    Bala.getBalasActivas().removeAll(balasParaEliminar);
	    Marciano.getMarcianosActivos().removeAll(marcianosParaEliminar);
	}

	
}