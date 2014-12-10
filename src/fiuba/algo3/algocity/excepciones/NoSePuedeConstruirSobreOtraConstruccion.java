package fiuba.algo3.algocity.excepciones;

public class NoSePuedeConstruirSobreOtraConstruccion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoSePuedeConstruirSobreOtraConstruccion() {
	    super("No se puede construir sobre otra construcción");
	}
}