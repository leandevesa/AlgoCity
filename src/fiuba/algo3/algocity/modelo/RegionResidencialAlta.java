package fiuba.algo3.algocity.modelo;

public class RegionResidencialAlta extends RegionResidencial {
	
	public RegionResidencialAlta(Posicion unaPosicion, Dimension unaDimension, Mapa mapa) {
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/regionResidencialAlta.png";
		posicion = unaPosicion;
		dimension = unaDimension;
		maxPersonasPorHectarea = getEdificioAConstruir(new Posicion(0,0)).getMaxCantidadPersonas();
		cantHectareas = (dimension.x) * (dimension.y);
		costo = (getEdificioAConstruir(new Posicion(0,0)).getCosto() * cantHectareas);
		pathImagenEdificio = "src/fiuba/algo3/algocity/modelo/imagenes/casa_regionalta.png";
		this.mapa = mapa;
	}

	@Override
	protected EdificioResidencial getEdificioAConstruir(Posicion unaPosicion) {
		return new EdificioResidencialAlta(unaPosicion, mapa);
	}
		
}
