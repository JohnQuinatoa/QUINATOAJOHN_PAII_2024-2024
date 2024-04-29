package Dependencia;

import Interfaces.Deadable;
import Interfaces.Drawable;
import Interfaces.Loseable;
import Interfaces.Movable;
import Interfaces.Shootable;

public class Container {	
	
	// clase intermediaria para inversion de dependencia
		
	public void Dibujar(Drawable d) {
		d.draw();
	}
	
	public void Mover(Movable m) {
		m.move();		
	}
	
	public void Disparar(Shootable s) {
		s.shoot();
	}
	
	public void Morir(Deadable d) {
		d.dead();		
	}
	
	public void Perder(Loseable l) {
		l.lose();
	}

}

