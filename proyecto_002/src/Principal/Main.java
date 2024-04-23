package Principal;

/**
 * 
 * @author Jhon Quinatoa Guerrero Steven
 * tema: Inversion de Dependencia
 * 
 */

 
public class Main {
	

	public static void main(String[] args) {		
		
		Papel triangulo = new Papel(new Triangulo());
		Papel cuadrado = new Papel(new Cuadrado());
		Papel circulo = new Papel(new Circulo());

	}

}
