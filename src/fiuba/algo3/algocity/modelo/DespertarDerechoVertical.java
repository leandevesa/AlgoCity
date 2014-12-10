package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class DespertarDerechoVertical extends EstrategiaGodzilla {
	
	public DespertarDerechoVertical() {
		posicionesDaniadas = new LinkedList();
	}
	
	@Override
	public void caminar(Posicion posicionPartida, Mapa unMapa) {	
		if ( posicionPartida.y() < (unMapa.getDimension().y()-5) ) {
			for(int i = posicionPartida.y(); i < unMapa.getDimension().y(); i++) {
				Posicion posicionImpactada = new Posicion(posicionPartida.x(), i);
				posicionesDaniadas.add(posicionImpactada);
				unMapa.daniar(posicionImpactada);
				
			}
		}
		else if ( posicionPartida.y() >= (unMapa.getDimension().y()-5) ) {
			for(int i = posicionPartida.y(); i > 0; i--) {
				Posicion posicionImpactada = new Posicion(posicionPartida.x(), i);
				posicionesDaniadas.add(posicionImpactada);
				unMapa.daniar(posicionImpactada);				
			}
		}
	}
	
}
