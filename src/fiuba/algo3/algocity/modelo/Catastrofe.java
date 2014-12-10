package fiuba.algo3.algocity.modelo;

import java.util.List;

public abstract class Catastrofe {
	protected Mapa mapa;
	protected Posicion posicionInicio;
	protected static List posicionesDaniadas;

	abstract public void originar();
	abstract public List getPosicionesDaniadas();
	abstract public String getImagen();
	abstract public String getSonido();
	
}
