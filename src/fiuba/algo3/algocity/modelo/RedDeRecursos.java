package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;

import fiuba.algo3.algocity.excepciones.NoSePudoEstablecerUnionEnLaRed;


public class RedDeRecursos extends Red {
	
	public RedDeRecursos(Mapa mapa) {
		this.conexiones = new LinkedList<Construccion>();
		red = new LinkedList<Conector>();
		this.mapa = mapa;
	}
	
	public void conectar(Construccion unDestino) throws NoSePudoEstablecerUnionEnLaRed {
		conexiones.add(unDestino);
		if (fuente != null) {
			unDestino.setFuenteDeRecursos(fuente);
		}
	}
	
}
