package Ventana;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import personajes.Nave;

public class Window extends JFrame{
	
	private static Window instance = null;// para devolver una instancia de la ventana y trabajar sobre la misma ventan
	private JPanel lienzo; // para agragar jpnaels como figuras a la ventana 
	
	public Window() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);//cuando se cierre termine la ejecucion	
		setTitle("Galata");//poner el titulo
		
		lienzo = new JPanel();// inicializa el lienzo como un nuevo Panel
		
        lienzo.setPreferredSize(new Dimension(800, 600)); // Establece el tamaño preferido del lienzo
        lienzo.setBackground(new Color(10,10,10));// establece el color del fondo
        lienzo.setLayout(null); // Usamos un layout nulo para posicionar los elementos manualmente
        add(lienzo); // Agregamos el lienzo a la ventana
        
        pack(); // Ajusta el tamaño de la ventana para que coincida con el tamaño preferido de sus subcomponentes
        setLocationRelativeTo(null); // Centra la ventana
        setVisible(true);
		
	}
	
	// devulve la instancia de la ventana
    public static Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }
    
    // devuelve la instancia de lienzo apra agregar paneles a la ventan	
    public JPanel getLienzo() {
        return lienzo;
    }

}
