package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class DespertarDerechoHorizontal extends EstrategiaGodzilla {
	
	public DespertarDerechoHorizontal() {
		posicionesDaniadas = new LinkedList();
	}
	
	@Override
	public void caminar(Posicion posicionPartida, Mapa unMapa) {	
		if ( posicionPartida.x() < (unMapa.getDimension().x()-5) ) {
			for(int i = posicionPartida.x(); i < unMapa.getDimension().x(); i++) {
				Posicion posicionImpactada = new Posicion(i, posicionPartida.y());
				posicionesDaniadas.add(posicionImpactada);
				unMapa.daniar(posicionImpactada);
				
			}
		}
		else if ( posicionPartida.x() >= (unMapa.getDimension().x()-5) ) {
			for(int i = posicionPartida.x(); i > 0; i--) {
				Posicion posicionImpactada = new Posicion(i, posicionPartida.y());
				posicionesDaniadas.add(posicionImpactada);
				unMapa.daniar(posicionImpactada);				
			}
		}
	}

}
