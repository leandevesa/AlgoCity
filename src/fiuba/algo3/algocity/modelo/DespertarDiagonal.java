package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class DespertarDiagonal extends EstrategiaGodzilla {
	
	public DespertarDiagonal() {
		posicionesDaniadas = new LinkedList();
	}
	
	@Override
	public void caminar(Posicion posicionPartida, Mapa unMapa) {	
		int x = posicionPartida.x();
		int y = posicionPartida.y();
		while(x < unMapa.getDimension().x() && y < unMapa.getDimension().y()) {
			Posicion posicionImpactada = new Posicion(x, y);
			posicionesDaniadas.add(posicionImpactada);
			unMapa.daniar(posicionImpactada);
			x++;
			y++;
		}
	}
}
