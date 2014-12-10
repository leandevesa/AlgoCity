package fiuba.algo3.algocity.modelo;

import java.util.List;

public abstract class EstrategiaGodzilla {
	
	protected static List posicionesDaniadas;
	
	public abstract void caminar(Posicion posicionPartida, Mapa unMapa);
	
	public List getPosicionesDaniadas() {
		return posicionesDaniadas;
	}
	
}
