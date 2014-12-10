package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;

import fiuba.algo3.algocity.excepciones.NoSePudoEstablecerUnionEnLaRed;


public class RedDeRutas extends Red {
	
	public RedDeRutas(Mapa mapa) {
		this.conexiones = new LinkedList<Construccion>();
		red = new LinkedList<Conector>();
		this.mapa = mapa;
	}
	
	public void conectar(Region unaRegion) throws Exception {
		if (red.size() == 0) {
			throw new NoSePudoEstablecerUnionEnLaRed("No hay ningún conector creado");
		}
		
		if (!regionFuente.esAdyacente((red.getFirst()).getPosicion())) {
			throw new NoSePudoEstablecerUnionEnLaRed("No estan unidas las rutas");
		}
		if (!unaRegion.esAdyacente(((Conector) red.getLast()).getPosicion())) {
			throw new NoSePudoEstablecerUnionEnLaRed("No estan unidas las rutas");
		}

		conexiones.add(unaRegion);
		unaRegion.conectarCon(regionFuente);
	}

}
