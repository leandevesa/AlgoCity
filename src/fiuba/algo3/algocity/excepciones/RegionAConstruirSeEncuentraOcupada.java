package fiuba.algo3.algocity.excepciones;

public class RegionAConstruirSeEncuentraOcupada extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RegionAConstruirSeEncuentraOcupada() {
	    super("La región a construir se encuentra ocupada");
	}
	public RegionAConstruirSeEncuentraOcupada(String message) {
	    super(message);
	}
}
