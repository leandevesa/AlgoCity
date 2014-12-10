package fiuba.algo3.algocity.modelo;
public class EdificioResidencialBaja extends EdificioResidencial {
	
	public EdificioResidencialBaja(Posicion unaPosicion, Mapa mapa) {
		posicion = unaPosicion;
		cantidadPersonas = 0;
		maxCantidadPersonas = 50;
		costo = 70;
		this.posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		porcentajeReparacion = 10;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/casa1.png";
	}

}
