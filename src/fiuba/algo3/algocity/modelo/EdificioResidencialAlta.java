package fiuba.algo3.algocity.modelo;

public class EdificioResidencialAlta extends EdificioResidencial {

	public EdificioResidencialAlta(Posicion unaPosicion, Mapa mapa) {
		cantidadPersonas = 0;
		maxCantidadPersonas = 150;
		costo = 400;
		this.posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		porcentajeReparacion = 10;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/casa3.png";
	}
}
