package fiuba.algo3.algocity.excepciones;

public class SeDebeSeleccionarUnaFuenteDeRecursos extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeDebeSeleccionarUnaFuenteDeRecursos() {
		super("Se debe seleccionar una fuente de recursos para poder agregar una conexion");
	}
}
