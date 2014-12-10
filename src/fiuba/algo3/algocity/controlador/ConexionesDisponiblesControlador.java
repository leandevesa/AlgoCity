package fiuba.algo3.algocity.controlador;

import java.util.List;

import fiuba.algo3.algocity.modelo.Construccion;
import fiuba.algo3.algocity.modelo.Mapa;
import fiuba.algo3.algocity.modelo.Tuberia;

public class ConexionesDisponiblesControlador {
	
	public void conexionesDisponibles(Mapa mapa, Construccion fuente, Construccion otraConstruccion) {
		List<Tuberia> tuberias = mapa.getTuberias();
		
		for (Tuberia tuberia : tuberias) {
			if (tuberia.getPosicion().esIgualA(fuente.getPosicion())) {
				//Pensar
			}
		}
		
	}
	
}
