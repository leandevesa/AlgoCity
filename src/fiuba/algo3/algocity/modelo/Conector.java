package fiuba.algo3.algocity.modelo;

public abstract class Conector extends Construccion {
	protected Red red;
    
	public Posicion getPosicion() {
		return posicion;
	}
	
	public void setRed(Red unaRed) {
		red = unaRed;
	}
}
