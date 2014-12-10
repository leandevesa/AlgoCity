package fiuba.algo3.algocity.modelo;

public class Posicion {
	int x = 0;
	int y = 0;
	
	public Posicion(int posX, int posY) {
		x = posX;
		y = posY;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public boolean esIgualA(Posicion b) {
		if ((x == b.x) && (y == b.y)) {
			return true;
		}
		return false;
	}
	
	public boolean esAdyacente(Posicion otraPosicion) {
		int distX = x - otraPosicion.x();
		int distY = y - otraPosicion.y();
		
		if ( Math.abs(distX) > 1 || Math.abs(distY) > 1 ) {
			return false;
		}
		return true;
	}
	
}
