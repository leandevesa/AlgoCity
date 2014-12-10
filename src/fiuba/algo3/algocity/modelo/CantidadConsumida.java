package fiuba.algo3.algocity.modelo;

public class CantidadConsumida {
	private FuenteDeRecursos fuente;
	private int cantidad;
	
	public CantidadConsumida (FuenteDeRecursos unaFuente, int unaCantidad) {
		fuente = unaFuente;
		cantidad = unaCantidad;
	}
	
	public FuenteDeRecursos getFuente() {
		return fuente;
	}
	
	public int getCantidad() {
		return cantidad;
	}
}
