package fiuba.algo3.algocity.excepciones;

public class NoSePuedeConstruirSobreAgua extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSePuedeConstruirSobreAgua() {
	    super("No se puede construir sobre agua");
	}
}
