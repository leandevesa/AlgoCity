package fiuba.algo3.algocity.excepciones;

public class SeDebeConstruirSobreAgua extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeDebeConstruirSobreAgua() {
		super("Sólo se puede construir en una región acuatica");
	}
}
