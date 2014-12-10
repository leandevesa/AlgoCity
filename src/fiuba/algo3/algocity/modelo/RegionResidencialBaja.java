package fiuba.algo3.algocity.modelo;


public class RegionResidencialBaja extends RegionResidencial {
		
	public RegionResidencialBaja(Posicion unaPosicion, Dimension unaDimension, Mapa mapa) {
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/regionResidencialBaja.png";
		posicion = unaPosicion;
		dimension = unaDimension;
		maxPersonasPorHectarea = getEdificioAConstruir(new Posicion(0,0)).getMaxCantidadPersonas();
		cantHectareas = (dimension.x) * (dimension.y);
		costo = (getEdificioAConstruir(new Posicion(0,0)).getCosto() * cantHectareas);
		pathImagenEdificio = "src/fiuba/algo3/algocity/modelo/imagenes/casa_regionbaja.png";
		this.mapa = mapa;
	}

	@Override
	protected EdificioResidencial getEdificioAConstruir(Posicion unaPosicion) {
		return new EdificioResidencialBaja(unaPosicion, mapa);
	}
		
}
