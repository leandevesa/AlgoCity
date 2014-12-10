package fiuba.algo3.algocity.modelo;

public class EdificioResidencialMedia extends EdificioResidencial {

	public EdificioResidencialMedia(Posicion unaPosicion, Mapa mapa) {
		cantidadPersonas = 0;
		maxCantidadPersonas = 100;
		costo = 220;
		this.posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		porcentajeReparacion = 10;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/casa2.png";
	}
}
