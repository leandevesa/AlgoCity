package fiuba.algo3.algocity.excepciones;

public class NoSePudoEstablecerUnionEnLaRed extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSePudoEstablecerUnionEnLaRed(String mensaje) {
		super(mensaje);
	}
	public NoSePudoEstablecerUnionEnLaRed() {
		super("No se pudo establecer union en la red");
	}
}
